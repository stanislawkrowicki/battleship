package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.put.battleship.server.frames.IncomingWebSocketFrame;
import com.put.battleship.server.frames.OutgoingFrameType;
import com.put.battleship.server.frames.OutgoingWebSocketFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
        System.out.println("Received: " + webSocketFrame.text());

        IncomingWebSocketFrame frame = null;
        try {
            frame = objectMapper.readValue(webSocketFrame.text(), IncomingWebSocketFrame.class);
        } catch (JsonProcessingException e) {
            try {
                System.out.println("Got invalid frame: " + webSocketFrame.text() + "\n" + e.getMessage());
                sendInvalidFrame(ctx);
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

        IncomingFrameHandler handler = IncomingFrameHandlerFactory.getHandler(frame);
        handler.handle();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("Client connected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public void sendInvalidFrame(ChannelHandlerContext ctx) throws Exception {
        OutgoingWebSocketFrame invalidFrame = new OutgoingWebSocketFrame(OutgoingFrameType.INVALID_FRAME, "");
        String jsonString = objectMapper.writeValueAsString(invalidFrame);
        ctx.writeAndFlush(new TextWebSocketFrame(jsonString));
    }

}