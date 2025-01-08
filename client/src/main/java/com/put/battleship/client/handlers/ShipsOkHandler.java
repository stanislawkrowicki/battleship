package com.put.battleship.client.handlers;

import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class ShipsOkHandler extends ServerFrameHandler {
    public ShipsOkHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Ships OK");
    }
}
