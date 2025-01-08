package com.put.battleship.client.senders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.frames.ClientFrame;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class FrameSenderManager {
    static private ChannelHandlerContext ctx;
    static private final ObjectMapper objectMapper = new ObjectMapper();

    static public void setContext(ChannelHandlerContext context) {
        ctx = context;
    }

    static public void sendFrame(ClientFrame frame) {
        if (ctx == null) {
            // TODO: handle this better
            System.out.println("Tried to send frame without open ctx");
            return;
        }

        try {
            String json = objectMapper.writeValueAsString(frame);
            TextWebSocketFrame wsFrame = new TextWebSocketFrame(json);
            ChannelFuture res = ctx.writeAndFlush(wsFrame);
            res.addListener(future -> {
                if (!future.isSuccess()) {
                    System.out.println("Failed to send frame: " + future.cause().getMessage() + ", retrying...");
                    ctx.writeAndFlush(wsFrame).addListener(retryFuture -> {
                        if (!retryFuture.isSuccess()) {
                            System.out.println("Frame sent successfully after retry");
                        }
                    });
                }
            });
        } catch (JsonProcessingException e) {
            System.out.println("Tried to send invalid frame: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
