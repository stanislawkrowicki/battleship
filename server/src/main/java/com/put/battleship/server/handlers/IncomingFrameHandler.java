package com.put.battleship.server.handlers;

import com.put.battleship.server.IncomingWebSocketFrame;

public abstract class IncomingFrameHandler {

    private final IncomingWebSocketFrame frame;

    public IncomingFrameHandler(IncomingWebSocketFrame frame) {
        this.frame = frame;
    }

    public abstract void handle();
}
