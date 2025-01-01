package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.server.Room;
import com.put.battleship.server.RoomManager;
import com.put.battleship.server.exceptions.RoomAlreadyExistsException;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.CreateRoomPayload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.UUID;

public class CreateRoomHandler extends ClientFrameHandler {

    public CreateRoomHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        CreateRoomPayload payload = (CreateRoomPayload) this.frame.payload;

        Room room = new Room(UUID.randomUUID().toString(), payload.name(), payload.size());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            RoomManager.addRoom(room);
            ServerFrame successFrame = new ServerFrame(ServerFrameType.ROOM_CREATED, room.getId());
            try {
                String json = objectMapper.writeValueAsString(successFrame);
                this.ctx.writeAndFlush(new TextWebSocketFrame(json));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (RoomAlreadyExistsException e) {
            ServerFrame alreadyExists = new ServerFrame(ServerFrameType.ROOM_ALREADY_EXISTS, "Room with specified name already exists.");
            try {
                String json = objectMapper.writeValueAsString(alreadyExists);
                this.ctx.writeAndFlush(new TextWebSocketFrame(json));
            } catch (JsonProcessingException jpe) {
                jpe.printStackTrace();
            }
        }
    }
}
