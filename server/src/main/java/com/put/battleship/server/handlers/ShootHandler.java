package com.put.battleship.server.handlers;

import com.put.battleship.server.IncomingWebSocketFrame;

public class ShootHandler extends IncomingFrameHandler {

    public ShootHandler(IncomingWebSocketFrame frame) {
        super(frame);
    }

    @Override
    public void handle() {
        System.out.println("Handling shoot");
    }
}
