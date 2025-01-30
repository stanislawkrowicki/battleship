package com.put.battleship.server.handlers;

import com.put.battleship.server.ContextManager;
import com.put.battleship.server.Game;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.payloads.client.SetShipsPayload;
import com.put.battleship.shared.payloads.server.GameShipsSetPayload;
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
            Player host = game.getHost();
            Player guest = game.getGuest();

            ChannelHandlerContext hostCtx = ContextManager.getContextFromPlayer(host);
            ChannelHandlerContext guestCtx = ContextManager.getContextFromPlayer(guest);

            if (guestCtx == null) {
                // TODO: Stop the game
                throw new IllegalStateException("Opponent is not connected");
            }

            assert hostCtx != null;

            sendFrameToCtx(hostCtx, new ServerFrame(ServerFrameType.GAME_SHIPS_SET, new GameShipsSetPayload(guest.getName())));
            sendFrameToCtx(guestCtx, new ServerFrame(ServerFrameType.GAME_SHIPS_SET, new GameShipsSetPayload(host.getName())));

            ChannelHandlerContext startingPlayerCtx = ContextManager.getContextFromPlayer(game.getCurrentPlayer());
            assert startingPlayerCtx != null;

            sendFrameToCtx(startingPlayerCtx, new ServerFrame(ServerFrameType.YOUR_TURN, null));
        }
    }
}
