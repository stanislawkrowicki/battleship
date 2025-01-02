package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.server.Game;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.server.PlayerManager;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class CreateGameHandler extends ClientFrameHandler {

    public CreateGameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        Player player = PlayerManager.getPlayerFromContext(this.ctx);

        Game game = new Game(player);

        ObjectMapper objectMapper = new ObjectMapper();

        GameManager.addGame(game);

        System.out.println("Game created: " + game.getId() + " for player: " + game.getHost().getId());

        ServerFrame successFrame = new ServerFrame(ServerFrameType.GAME_CREATED, game.getId());
        try {
            String json = objectMapper.writeValueAsString(successFrame);
            this.ctx.writeAndFlush(new TextWebSocketFrame(json));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
