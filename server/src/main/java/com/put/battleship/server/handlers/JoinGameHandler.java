package com.put.battleship.server.handlers;

import com.put.battleship.server.ContextManager;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.server.exceptions.GameDoesNotExistException;
import com.put.battleship.server.exceptions.GameIsFullException;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.payloads.client.JoinGamePayload;
import com.put.battleship.shared.payloads.server.GameJoinedPayload;
import io.netty.channel.ChannelHandlerContext;

public class JoinGameHandler extends ClientFrameHandler {

    public JoinGameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        JoinGamePayload payload = (JoinGamePayload) this.frame.payload;
        Player player = ContextManager.getPlayerFromContext(this.ctx);

        try {
            GameManager.connectPlayerToRoomByJoinCode(player, payload.joinCode());
            this.sendFrame(new ServerFrame(ServerFrameType.GAME_JOINED, new GameJoinedPayload(payload.joinCode())));
        } catch (GameDoesNotExistException notExistException) {
            this.sendFrame(new ServerFrame(ServerFrameType.GAME_NOT_FOUND, payload.joinCode()));
        } catch (GameIsFullException isFullException) {
            this.sendFrame(new ServerFrame(ServerFrameType.GAME_IS_FULL, payload.joinCode()));
        }
    }
}
