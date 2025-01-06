package com.put.battleship.server.handlers;

import com.put.battleship.server.*;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import io.netty.channel.ChannelHandlerContext;

import static com.put.battleship.server.handlers.WebSocketFrameHandler.sendFrameToCtx;

public class StartGameHandler extends ClientFrameHandler {
    public StartGameHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        Player player = ContextManager.getPlayerFromContext(ctx);
        Game game = GameManager.getGameByHost(player);

        if (game == null) {
            this.sendFrame(new ServerFrame(ServerFrameType.PLAYER_IS_NOT_HOST, null));
            return;
        }

        if (game.getGuest() == null) {
            this.sendFrame(new ServerFrame(ServerFrameType.WAITING_FOR_OPPONENT, null));
            return;
        }

        game.start();

        ChannelHandlerContext opponentCtx = ContextManager.findContextMappedToPlayer(game.getGuest());
        assert opponentCtx != null;

        this.sendFrame(new ServerFrame(ServerFrameType.GAME_STARTED, null));
        sendFrameToCtx(opponentCtx, new ServerFrame(ServerFrameType.GAME_STARTED, null));
    }
}
