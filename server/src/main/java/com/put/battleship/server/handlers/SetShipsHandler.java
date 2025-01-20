package com.put.battleship.server.handlers;

import com.put.battleship.server.ContextManager;
import com.put.battleship.server.Game;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.payloads.client.SetShipsPayload;
import io.netty.channel.ChannelHandlerContext;

import static com.put.battleship.server.handlers.WebSocketFrameHandler.sendFrameToCtx;

public class SetShipsHandler extends ClientFrameHandler {
    public SetShipsHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        SetShipsPayload payload = (SetShipsPayload) frame.payload;
        Player player = ContextManager.getPlayerFromContext(ctx);
        Game game = GameManager.getGameByPlayer(player);

        if (game == null) {
            throw new IllegalStateException("Player is not in any game");
        }

        try {
            game.setShipsForPlayer(player, payload.ships());
            sendFrame(new ServerFrame(ServerFrameType.SHIPS_OK, null));
        } catch (IllegalArgumentException e) {
            // TODO: Player not in game, handle this
            sendFrame(new ServerFrame(ServerFrameType.SHIPS_NOT_OK, null));
            return;
        }

        if (game.areBothPlayersReady()) {
            game.start();
            Player opponent = game.getOpponent(player);
            ServerFrame gameStartedFrame = new ServerFrame(ServerFrameType.GAME_SHIPS_SET, null);
            ChannelHandlerContext enemyCtx = ContextManager.getContextFromPlayer(opponent);

            if (enemyCtx == null) {
                // TODO: Stop the game
                throw new IllegalStateException("Opponent is not connected");
            }

            sendFrame(gameStartedFrame);
            sendFrameToCtx(enemyCtx, gameStartedFrame);

            ChannelHandlerContext startingPlayerCtx = ContextManager.getContextFromPlayer(game.getCurrentPlayer());
            assert startingPlayerCtx != null;

            sendFrameToCtx(startingPlayerCtx, new ServerFrame(ServerFrameType.YOUR_TURN, null));
        }
    }
}
