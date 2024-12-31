package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;

public class IncomingFrameHandlerFactory {
    public static IncomingFrameHandler getHandler(IncomingWebSocketFrame frame) {
        return switch (frame.type()) {
            case FETCH_SERVERS -> new FetchRoomsHandler(frame);
            case CREATE_ROOM -> new CreateRoomHandler(frame);
            case JOIN_GAME -> new JoinRoomHandler(frame);
            case SHOOT -> new ShootHandler(frame);
            case SYNC -> new SyncHandler(frame);
            default -> throw new IllegalArgumentException("Unknown frame type: " + frame.type());
        };
    }
}
