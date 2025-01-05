package com.put.battleship.client.handlers;

import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class ConnectedFrameHandler extends ServerFrameHandler {
    public ConnectedFrameHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Connected to the server");
    }
}
