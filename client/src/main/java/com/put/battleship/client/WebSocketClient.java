package com.put.battleship.client;

import com.put.battleship.client.handlers.WebSocketFrameHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class WebSocketClient {
    private final String host;
    private final int port;

    public WebSocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            // Add HTTP codec and WebSocketClient handlers
                            pipeline.addLast(new HttpClientCodec());
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            pipeline.addLast(new WebSocketClientProtocolHandler(
                                    WebSocketClientProtocolConfig.newBuilder()
                                            .webSocketUri(String.format("ws://%s:%s/ws", host, port))
                                            .build()
                            ));

                            // Custom handler to send JSON
                            pipeline.addLast(new WebSocketFrameHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess())
                System.out.printf("WebSocketClient connected to ws://%s:%d/ws\n", host, port);
            else {
                System.out.printf("Failed to connect to the server ws://%s:%d/ws%n", host, port);
                if (channelFuture.cause() != null) {
                    System.out.println(channelFuture.cause().getMessage());
                }
                return;
            }

            channelFuture.channel().closeFuture().addListener(_ -> {
                System.out.println("WebSocketClient disconnected");
                workerGroup.shutdownGracefully();
            });
        } catch (Exception e) {
            System.out.println(String.format("Failed to connect to the server ws://%s:%d/ws", host, port));
            e.printStackTrace();
        }
    }
}
