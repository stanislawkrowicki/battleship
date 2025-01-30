package com.put.battleship.client.handlers;

import com.put.battleship.client.BattleShipsApp;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class ShotHitHandler extends ServerFrameHandler {
    public ShotHitHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("You Shot Him");
        BattleShipsApp.model.handleMyResult(true);
    }
}
