package com.put.battleship.server.handlers;

import com.put.battleship.server.IncomingWebSocketFrame;

public class IncomingFrameHandlerFactory {
    public static IncomingFrameHandler getHandler(IncomingWebSocketFrame frame) {
        return switch (frame.type()) {
            case FETCH_SERVERS -> new FetchServersHandler(frame);
            case CREATE_GAME -> new CreateGameHandler(frame);
            case JOIN_GAME -> new JoinGameHandler(frame);
            case SHOOT -> new ShootHandler(frame);
            case SYNC -> new SyncHandler(frame);
            default -> throw new IllegalArgumentException("Unknown frame type: " + frame.type());
        };
    }
}
