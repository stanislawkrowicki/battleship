package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.server.Room;
import com.put.battleship.server.RoomManager;
import com.put.battleship.server.exceptions.RoomAlreadyExistsException;
import com.put.battleship.server.frames.IncomingWebSocketFrame;
import com.put.battleship.server.frames.OutgoingFrameType;
import com.put.battleship.server.frames.OutgoingWebSocketFrame;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

record CreateRoomPayload(String name, int size) {
}

public class CreateRoomHandler extends IncomingFrameHandler {

    public CreateRoomHandler(IncomingWebSocketFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        CreateRoomPayload payload;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            payload = objectMapper.readValue(this.frame.payload(), CreateRoomPayload.class);
        } catch (JacksonException e) {
            System.err.println("JSON parsing error in CreateRoomHandler, " + e.getMessage());
            throwInvalidFrame();
            return;
        }

        Room room = new Room(UUID.randomUUID().toString(), payload.name(), payload.size());
        try {
            RoomManager.addRoom(room);
            OutgoingWebSocketFrame successFrame = new OutgoingWebSocketFrame(OutgoingFrameType.ROOM_CREATED, room.getId());
            this.ctx.writeAndFlush(successFrame);
        } catch (RoomAlreadyExistsException e) {
            OutgoingWebSocketFrame alreadyExists = new OutgoingWebSocketFrame(OutgoingFrameType.ROOM_ALREADY_EXISTS, "Room with specified name already exists.");
            this.ctx.writeAndFlush(alreadyExists);
        }
    }
}
