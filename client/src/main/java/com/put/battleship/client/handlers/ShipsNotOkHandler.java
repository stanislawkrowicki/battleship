package com.put.battleship.client.handlers;

import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class ShipsNotOkHandler extends ServerFrameHandler {
    public ShipsNotOkHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Ships NOT OK");
    }
}
