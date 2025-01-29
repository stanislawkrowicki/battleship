package com.put.battleship.server.handlers;

import com.put.battleship.server.ContextManager;
import com.put.battleship.server.Game;
import com.put.battleship.server.GameManager;
import com.put.battleship.server.Player;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.frames.ServerFrameType;
import com.put.battleship.shared.payloads.client.ShootPayload;
import com.put.battleship.shared.payloads.server.EnemyShotPayload;
import io.netty.channel.ChannelHandlerContext;

import static com.put.battleship.server.handlers.WebSocketFrameHandler.sendFrameToCtx;

public class ShootHandler extends ClientFrameHandler {

    public ShootHandler(ClientFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        ShootPayload payload = (ShootPayload) frame.payload;
        Player player = ContextManager.getPlayerFromContext(ctx);
        Game game = GameManager.getGameByPlayer(player);

        Player opponent = game.getOpponent(player);
        ChannelHandlerContext enemyCtx = ContextManager.getContextFromPlayer(opponent);

        assert enemyCtx != null;

        try {
            // TODO: Check if the player isn't shooting in a place that was already shot
            boolean hit = game.handleShoot(player, payload.x(), payload.y());
            EnemyShotPayload enemyShotPayload = new EnemyShotPayload(hit, payload.x(), payload.y());
            if (hit) {
                sendFrame(new ServerFrame(ServerFrameType.SHOT_HIT, null));
                sendFrame(new ServerFrame(ServerFrameType.YOUR_TURN, null));
            } else {
                sendFrame(new ServerFrame(ServerFrameType.SHOT_MISS, null));
                sendFrameToCtx(enemyCtx, new ServerFrame(ServerFrameType.YOUR_TURN, null));


            }

            sendFrameToCtx(enemyCtx, new ServerFrame(ServerFrameType.ENEMY_SHOT, enemyShotPayload));

        } catch (IllegalArgumentException e) {
            System.out.println("Exception in ShootHandler:");
            System.out.println(e.getMessage());
        }
    }
}
