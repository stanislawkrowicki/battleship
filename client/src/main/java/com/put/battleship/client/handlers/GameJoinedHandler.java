package com.put.battleship.client.handlers;

import com.put.battleship.client.SceneSwitcher;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.server.GameCreatedPayload;
import io.netty.channel.ChannelHandlerContext;

public class GameJoinedHandler extends ServerFrameHandler {
    public GameJoinedHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        SceneSwitcher.switchToSetShipScreen();
    }
}
