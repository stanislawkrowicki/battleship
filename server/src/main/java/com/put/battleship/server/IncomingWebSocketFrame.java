package com.put.battleship.server;

public record IncomingWebSocketFrame(IncomingFrameType type, String payload) {
}
