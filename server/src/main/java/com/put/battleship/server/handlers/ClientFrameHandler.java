package com.put.battleship.server.handlers;

import com.put.battleship.shared.frames.ClientFrame;
import io.netty.channel.ChannelHandlerContext;

public abstract class ClientFrameHandler {

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

    public abstract void handle();

}
