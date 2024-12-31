package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;

public class JoinRoomHandler extends IncomingFrameHandler {

    public JoinRoomHandler(IncomingWebSocketFrame frame) {
        super(frame);
    }

    @Override
    public void handle() {
        System.out.println("Handling join room");
    }
}
