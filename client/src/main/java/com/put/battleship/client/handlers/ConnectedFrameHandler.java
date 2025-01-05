package com.put.battleship.client.handlers;

import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.server.ConnectedPayload;
import io.netty.channel.ChannelHandlerContext;

public class ConnectedFrameHandler extends ServerFrameHandler {
    public ConnectedFrameHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Connected to the server");
        var payload = (ConnectedPayload) this.frame.payload;
        System.out.println("Your player id is: " + payload.uuid());
    }
}
