package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;
import io.netty.channel.ChannelHandlerContext;

public class JoinRoomHandler extends IncomingFrameHandler {

    public JoinRoomHandler(IncomingWebSocketFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Handling join room");
    }
}
