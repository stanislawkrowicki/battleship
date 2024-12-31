package com.put.battleship.server.handlers;

import com.put.battleship.server.IncomingWebSocketFrame;

public class CreateGameHandler extends IncomingFrameHandler {

    public CreateGameHandler(IncomingWebSocketFrame frame) {
        super(frame);
    }

    @Override
    public void handle() {
        System.out.println("Handling create game");
    }
}
