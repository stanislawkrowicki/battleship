package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;

public class CreateRoomHandler extends IncomingFrameHandler {

    public CreateRoomHandler(IncomingWebSocketFrame frame) {
        super(frame);
    }

    @Override
    public void handle() {
        System.out.println("Handling create room");
    }
}
