package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import static com.put.battleship.server.handlers.WebSocketFrameHandler.sendFrameToCtx;

public abstract class ClientFrameHandler {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    final ClientFrame frame;
    final ChannelHandlerContext ctx;

    public ClientFrameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        this.frame = frame;
        this.ctx = ctx;
    }

    void throwInvalidFrame() {
        try {
            WebSocketFrameHandler.throwInvalidFrame(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendFrame(ServerFrame frame) {
        sendFrameToCtx(ctx, frame);
    }

    public abstract void handle();

}
