package com.put.battleship.server.handlers;

import com.put.battleship.server.*;
import com.put.battleship.server.exceptions.GameAlreadyExistsException;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.client.CreateGamePayload;
import com.put.battleship.shared.payloads.server.GameCreatedPayload;
import io.netty.channel.ChannelHandlerContext;

public class CreateGameHandler extends ClientFrameHandler {

    public CreateGameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        Player player = ContextManager.getPlayerFromContext(this.ctx);
        CreateGamePayload payload = (CreateGamePayload) frame.payload;

        if (payload.joinCode() == null || payload.joinCode().length() > 8 || payload.joinCode().length() < 4) {
            throwInvalidFrame();
            return;
        }

        try {
            Game game = GameManager.createGame(player, payload.joinCode());
            System.out.println("Game created: " + game.getId() +
                    " with join code " + game.getJoinCode() + "for player " + game.getHost().getId());
            this.sendFrame(new ServerFrame(ServerFrameType.GAME_CREATED, new GameCreatedPayload(game.getId().toString(), game.getJoinCode())));
        } catch (GameAlreadyExistsException e) {
            this.sendFrame(new ServerFrame(ServerFrameType.GAME_ALREADY_EXISTS, null));
        }
    }
}
