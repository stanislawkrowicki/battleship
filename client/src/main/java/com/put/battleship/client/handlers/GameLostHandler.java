package com.put.battleship.client.handlers;

import com.put.battleship.client.SceneSwitcher;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;

public class GameLostHandler extends ServerFrameHandler {
    public GameLostHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        super(frame, ctx);
    }

    @Override
    public void handle() {
        SceneSwitcher.switchToDefeatScreen();
    }
}
