package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JacksonException;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
        ClientFrame frame = null;
        try {
            frame = objectMapper.readValue(webSocketFrame.text(), ClientFrame.class);
        } catch (JacksonException e) {
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
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("Client connected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public static void throwInvalidFrame(ChannelHandlerContext ctx) throws Exception {
        ServerFrame invalidFrame = new ServerFrame(ServerFrameType.INVALID_FRAME, "");
        String jsonString = objectMapper.writeValueAsString(invalidFrame);
        ctx.writeAndFlush(new TextWebSocketFrame(jsonString));
    }

}