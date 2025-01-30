package com.put.battleship.client.handlers;

import com.put.battleship.client.SceneSwitcher;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class GameShipsSetHandler extends ServerFrameHandler {
    public GameShipsSetHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Game ships set");
        SceneSwitcher.switchToBattleScreen();
    }
}
