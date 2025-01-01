package com.put.battleship.server.handlers;

import com.put.battleship.shared.frames.ClientFrame;
import io.netty.channel.ChannelHandlerContext;

public class ClientFrameHandlerFactory {
    public static ClientFrameHandler getHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        return switch (frame.type) {
            case FETCH_SERVERS -> new FetchRoomsHandler(frame, ctx);
            case CREATE_ROOM -> new CreateRoomHandler(frame, ctx);
            case JOIN_GAME -> new JoinRoomHandler(frame, ctx);
            case SHOOT -> new ShootHandler(frame, ctx);
            case SYNC -> new SyncHandler(frame, ctx);
            default -> throw new IllegalArgumentException("Unknown frame type: " + frame.type);
        };
    }
}
