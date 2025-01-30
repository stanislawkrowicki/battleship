package com.put.battleship.client.handlers;

import com.put.battleship.client.BattleShipsApp;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class YourTurnHandler extends ServerFrameHandler {
    public YourTurnHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Your Turn");
        BattleShipsApp.model.enableAttack();
    }
}