package com.put.battleship.server.handlers;

import com.put.battleship.shared.frames.ClientFrame;
import io.netty.channel.ChannelHandlerContext;

public class SyncHandler extends ClientFrameHandler {

    public SyncHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Handling sync");
    }
}
