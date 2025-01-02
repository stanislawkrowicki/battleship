package com.put.battleship.server.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.put.battleship.server.Game;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.server.PlayerManager;
import com.put.battleship.server.exceptions.GameDoesNotExistException;
import com.put.battleship.server.exceptions.GameIsFullException;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.payloads.JoinGamePayload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import static com.put.battleship.server.handlers.WebSocketFrameHandler.objectMapper;

public class JoinGameHandler extends ClientFrameHandler {

    public JoinGameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        JoinGamePayload payload = (JoinGamePayload) this.frame.payload;
        Player player = PlayerManager.getPlayerFromContext(this.ctx);

        try {
            GameManager.connectPlayerToRoom(player, payload.id());
            ServerFrame successFrame = new ServerFrame(ServerFrameType.GAME_JOINED, payload.id());
            try {
                String json = objectMapper.writeValueAsString(successFrame);
                this.ctx.writeAndFlush(new TextWebSocketFrame(json));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (GameDoesNotExistException notExistException) {
            ServerFrame invalidFrame = new ServerFrame(ServerFrameType.GAME_NOT_FOUND, payload.id());
            try {
                String jsonString = objectMapper.writeValueAsString(invalidFrame);
                ctx.writeAndFlush(new TextWebSocketFrame(jsonString));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (GameIsFullException isFullException) {
            ServerFrame invalidFrame = new ServerFrame(ServerFrameType.GAME_IS_FULL, payload.id());
            try {
                String jsonString = objectMapper.writeValueAsString(invalidFrame);
                ctx.writeAndFlush(new TextWebSocketFrame(jsonString));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
