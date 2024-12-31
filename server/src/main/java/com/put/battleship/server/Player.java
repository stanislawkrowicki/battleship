package com.put.battleship.server;

import io.netty.channel.ChannelHandlerContext;

public record Player(String id, String name, ChannelHandlerContext ctx) {
}
