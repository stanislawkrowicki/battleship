package com.put.battleship.server.handlers;

import com.put.battleship.shared.frames.ClientFrame;
import io.netty.channel.ChannelHandlerContext;

public class ClientFrameHandlerFactory {
    public static ClientFrameHandler getHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        return switch (frame.type) {
            case CREATE_GAME -> new CreateGameHandler(frame, ctx);
            case JOIN_GAME -> new JoinGameHandler(frame, ctx);
            case SHOOT -> new ShootHandler(frame, ctx);
            case SYNC -> new SyncHandler(frame, ctx);
            default -> throw new IllegalArgumentException("Unknown client frame type to factory: " + frame.type);
        };
    }
}
