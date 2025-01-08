package com.put.battleship.client.handlers;

import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.server.GameCreatedPayload;
import io.netty.channel.ChannelHandlerContext;

public class GameCreatedHandler extends ServerFrameHandler {
    public GameCreatedHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        GameCreatedPayload payload = (GameCreatedPayload) frame.payload;
        System.out.printf("Game created with id %s and joinCode %s\n", payload.id(), payload.joinCode());
    }
}
