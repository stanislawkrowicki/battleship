package com.put.battleship.client.handlers;

import com.put.battleship.client.BattleShipsApp;
import com.put.battleship.client.FrameSender;
import com.put.battleship.shared.frames.ServerFrame;
import com.put.battleship.shared.payloads.server.ConnectedPayload;
import com.put.battleship.shared.payloads.server.EnemyShotPayload;
import com.put.battleship.shared.payloads.server.GameCreatedPayload;
import io.netty.channel.ChannelHandlerContext;

public class EnemyShotHandler extends ServerFrameHandler {
    public EnemyShotHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        EnemyShotPayload payload = (EnemyShotPayload) frame.payload;
        BattleShipsApp.model.handleEnemyResult(payload.hit(), payload.y(), payload.x());
    }
}