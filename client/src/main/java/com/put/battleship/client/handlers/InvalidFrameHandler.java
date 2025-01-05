package com.put.battleship.client.handlers;

import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class InvalidFrameHandler extends ServerFrameHandler {
    public InvalidFrameHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("You tried to sent an invalid frame: " + frame.payload);
    }
}
