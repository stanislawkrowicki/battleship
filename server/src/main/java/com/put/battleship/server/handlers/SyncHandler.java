package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;

public class SyncHandler extends IncomingFrameHandler {

    public SyncHandler(IncomingWebSocketFrame frame) {
        super(frame);
    }

    @Override
    public void handle() {
        System.out.println("Handling sync");
    }
}
