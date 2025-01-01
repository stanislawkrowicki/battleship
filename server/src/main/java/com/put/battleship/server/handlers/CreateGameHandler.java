package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.server.Game;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.CreateGamePayload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.UUID;

public class CreateGameHandler extends ClientFrameHandler {

    public CreateGameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        CreateGamePayload payload = (CreateGamePayload) this.frame.payload;

        Game game = new Game(UUID.randomUUID().toString(), new Player("", "", null));

        ObjectMapper objectMapper = new ObjectMapper();

        GameManager.addGame(game);
        ServerFrame successFrame = new ServerFrame(ServerFrameType.GAME_CREATED, game.getId());
        try {
            String json = objectMapper.writeValueAsString(successFrame);
            this.ctx.writeAndFlush(new TextWebSocketFrame(json));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
