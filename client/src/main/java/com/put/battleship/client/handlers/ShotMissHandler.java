package com.put.battleship.client.handlers;

import com.put.battleship.client.BattleController;
import com.put.battleship.client.BattleShipsApp;
import com.put.battleship.client.WebSocketClient;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;

public class ShotMissHandler extends ServerFrameHandler {

    public ShotMissHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }


    @Override
    public void handle() {
        System.out.println("You Missed Him");
        BattleShipsApp.model.handleMyResult(false);
        Platform.runLater(() -> {
            WebSocketClient.getController().setEnemyTurn();
        });
    }
}