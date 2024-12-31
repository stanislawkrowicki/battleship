package com.put.battleship.server.handlers;

import com.put.battleship.server.frames.IncomingWebSocketFrame;
import io.netty.channel.ChannelHandlerContext;

public class IncomingFrameHandlerFactory {
    public static IncomingFrameHandler getHandler(IncomingWebSocketFrame frame, ChannelHandlerContext ctx) {
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
