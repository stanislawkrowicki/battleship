package com.put.battleship.server.handlers;

import com.put.battleship.shared.frames.ClientFrame;
import io.netty.channel.ChannelHandlerContext;

public class FetchRoomsHandler extends ClientFrameHandler {

    public FetchRoomsHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Handling fetch rooms");
    }
}
