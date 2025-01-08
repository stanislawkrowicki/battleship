package com.put.battleship.client.handlers;

import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class ServerFrameHandlerFactory {
    public static ServerFrameHandler getHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        return switch (frame.type) {
            case INVALID_FRAME -> new InvalidFrameHandler(frame, ctx);
            case CONNECTED -> new ConnectedFrameHandler(frame, ctx);
            case GAME_CREATED -> new GameCreatedHandler(frame, ctx);
            case SHIPS_OK -> new ShipsOkHandler(frame, ctx);
            case SHIPS_NOT_OK -> new ShipsNotOkHandler(frame, ctx);
            case GAME_SHIPS_SET -> new GameShipsSetHandler(frame, ctx);
            default -> throw new IllegalArgumentException("Unknown client frame type to factory: " + frame.type);
        };
    }
}
