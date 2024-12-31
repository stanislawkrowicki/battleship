package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;

public class FetchRoomsHandler extends IncomingFrameHandler {

    public FetchRoomsHandler(IncomingWebSocketFrame frame) {
        super(frame);
    }

    @Override
    public void handle() {
        System.out.println("Handling fetch rooms");
    }
}
