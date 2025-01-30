package com.put.battleship.client.handlers;

import com.put.battleship.client.SceneSwitcher;
import com.put.battleship.client.WebSocketClient;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;

public class GameShipsSetHandler extends ServerFrameHandler {
    public GameShipsSetHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        SceneSwitcher.switchToBattleScreen();
        Platform.runLater(() -> {
            WebSocketClient.getController().setEnemyTurn();
        });
    }
}
