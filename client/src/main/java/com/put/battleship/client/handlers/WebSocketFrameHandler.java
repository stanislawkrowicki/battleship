package com.put.battleship.client.handlers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
        ServerFrame frame = null;
        try {
            frame = objectMapper.readValue(webSocketFrame.text(), ServerFrame.class);
        } catch (JacksonException e) {
            System.out.println("Got invalid frame: " + webSocketFrame.text() + "\n" + e.getMessage());
            return;
        }

        if (frame == null) {
            System.err.println("Received a null frame from server!");
            return;
        }

        ServerFrameHandler handler = ServerFrameHandlerFactory.getHandler(frame, ctx);
        handler.handle();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}