package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.server.Room;
import com.put.battleship.server.RoomManager;
import com.put.battleship.server.exceptions.RoomAlreadyExistsException;
import com.put.battleship.server.frames.IncomingWebSocketFrame;
import com.put.battleship.server.frames.OutgoingFrameType;
import com.put.battleship.server.frames.OutgoingWebSocketFrame;
import com.put.battleship.server.payloads.CreateRoomPayload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.UUID;

public class CreateRoomHandler extends IncomingFrameHandler {

    public CreateRoomHandler(IncomingWebSocketFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        CreateRoomPayload payload = (CreateRoomPayload) this.frame.payload;

        Room room = new Room(UUID.randomUUID().toString(), payload.name(), payload.size());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            RoomManager.addRoom(room);
            OutgoingWebSocketFrame successFrame = new OutgoingWebSocketFrame(OutgoingFrameType.ROOM_CREATED, room.getId());
            try {
                String json = objectMapper.writeValueAsString(successFrame);
                this.ctx.writeAndFlush(new TextWebSocketFrame(json));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (RoomAlreadyExistsException e) {
            OutgoingWebSocketFrame alreadyExists = new OutgoingWebSocketFrame(OutgoingFrameType.ROOM_ALREADY_EXISTS, "Room with specified name already exists.");
            try {
                String json = objectMapper.writeValueAsString(alreadyExists);
                this.ctx.writeAndFlush(new TextWebSocketFrame(json));
            } catch (JsonProcessingException jpe) {
                jpe.printStackTrace();
            }
        }
    }
}
