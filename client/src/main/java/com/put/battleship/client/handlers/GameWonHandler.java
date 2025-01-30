package com.put.battleship.client.handlers;

import com.put.battleship.client.BattleShipsApp;
import com.put.battleship.client.SceneSwitcher;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class GameWonHandler extends ServerFrameHandler {
    public GameWonHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        SceneSwitcher.switchToVictoryScreen();
        BattleShipsApp.model.resetGame();
    }
}
