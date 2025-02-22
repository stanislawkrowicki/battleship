package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.put.battleship.server.Player;
import com.put.battleship.server.PlayerManager;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.server.ConnectedPayload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;


public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
        ClientFrame frame = null;
        try {
            frame = objectMapper.readValue(webSocketFrame.text(), ClientFrame.class);
        } catch (Exception e) {
            try {
                System.out.println("Got invalid frame: " + webSocketFrame.text() + "\n" + e.getMessage());
                throwInvalidFrame(ctx);
                return;
            } catch (Exception mappingException) {
                System.err.println("Error while sending invalid frame!");
                mappingException.printStackTrace();
            }
        }

        if (frame == null) {
            System.err.println("Frame is null! Should not happen!");
            return;
        }

        ClientFrameHandler handler = ClientFrameHandlerFactory.getHandler(frame, ctx);
        handler.handle();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            System.out.println("Client connected: " + ctx.channel().remoteAddress());
            Player createdPlayer = PlayerManager.createPlayer();
            ConnectionHandler.mapPlayerToChannel(createdPlayer.getId(), ctx);
            sendFrameToCtx(ctx, new ServerFrame(ServerFrameType.CONNECTED, new ConnectedPayload(createdPlayer.getId().toString())));
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public static void throwInvalidFrame(ChannelHandlerContext ctx) throws Exception {
        ServerFrame invalidFrame = new ServerFrame(ServerFrameType.INVALID_FRAME, "");
        sendFrameToCtx(ctx, invalidFrame);
    }

    public static void sendFrameToCtx(ChannelHandlerContext ctx, ServerFrame frame) {
        try {
            String json = objectMapper.writeValueAsString(frame);
            TextWebSocketFrame wsFrame = new TextWebSocketFrame(json);
            var res = ctx.writeAndFlush(wsFrame);
            res.addListener(future -> {
                if (!future.isSuccess()) {
                    System.out.println("Failed to send frame: " + future.cause().getMessage());
                    ctx.writeAndFlush(wsFrame);
                }
            });
        } catch (JsonProcessingException e) {
            System.out.println("Tried to send invalid frame: " + e.getMessage());
            e.printStackTrace();
        }
    }

}