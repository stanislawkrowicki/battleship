package com.put.battleship.client.handlers;

import com.put.battleship.client.WebSocketClient;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;

public class GameAlreadyExistsHandler extends ServerFrameHandler {
    public GameAlreadyExistsHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        System.out.println("Game already exists");
        Platform.runLater(() -> {
            WebSocketClient.getRoomController().setGameExistsLabel();
        });
    }
}
