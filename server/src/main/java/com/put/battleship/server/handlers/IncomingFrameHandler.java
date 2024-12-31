package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;
import io.netty.channel.ChannelHandlerContext;

public abstract class IncomingFrameHandler {

    final IncomingWebSocketFrame frame;
    final ChannelHandlerContext ctx;

    public IncomingFrameHandler(IncomingWebSocketFrame frame, ChannelHandlerContext ctx) {
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

    public abstract void handle();

}
