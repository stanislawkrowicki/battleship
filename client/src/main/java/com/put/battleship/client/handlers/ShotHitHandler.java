package com.put.battleship.client.handlers;

import com.put.battleship.client.BattleShipsApp;
import com.put.battleship.client.FrameSender;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.server.ConnectedPayload;
import io.netty.channel.ChannelHandlerContext;

public class ShotHitHandler extends ServerFrameHandler {
    public ShotHitHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("You Shot Him");

        synchronized (BattleShipsApp.model.getLock()) {
            BattleShipsApp.model.setYouShotHim(true);
            BattleShipsApp.model.setFlag(true);// Ensure the thread owns the object's monitor
            BattleShipsApp.model.getLock().notifyAll(); // Now this works correctly
        }
    }
}
