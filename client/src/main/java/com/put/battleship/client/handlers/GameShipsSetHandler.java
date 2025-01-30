package com.put.battleship.client.handlers;

import com.put.battleship.client.BattleController;
import com.put.battleship.client.SceneSwitcher;
import com.put.battleship.client.WebSocketClient;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.server.GameShipsSetPayload;
import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;

public class GameShipsSetHandler extends ServerFrameHandler {
    public GameShipsSetHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        GameShipsSetPayload payload = (GameShipsSetPayload) frame.payload;

        SceneSwitcher.switchToBattleScreen();
        Platform.runLater(() -> {
            BattleController controller = WebSocketClient.getController();
            controller.setEnemyTurn();
            controller.setEnemyName(payload.enemyPlayerName());
        });
    }
}
