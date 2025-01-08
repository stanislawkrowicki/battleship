package com.put.battleship.client.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ServerFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public abstract class ServerFrameHandler {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    final ServerFrame frame;
    final ChannelHandlerContext ctx;

    public ServerFrameHandler(ServerFrame frame, ChannelHandlerContext ctx) {
        this.frame = frame;
        this.ctx = ctx;
    }

    public abstract void handle();
}
