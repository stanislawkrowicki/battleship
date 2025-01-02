package com.put.battleship.server.handlers;

import com.put.battleship.server.Game;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.server.PlayerManager;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class CreateGameHandler extends ClientFrameHandler {

    public CreateGameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        Player player = PlayerManager.getPlayerFromContext(this.ctx);
        Game game = new Game(player);

        GameManager.addGame(game);

        System.out.println("Game created: " + game.getId() + " for player: " + game.getHost().getId());

        this.sendFrame(new ServerFrame(ServerFrameType.GAME_CREATED, game.getId()));
    }
}
