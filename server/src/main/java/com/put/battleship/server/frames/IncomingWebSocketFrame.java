package com.put.battleship.server.frames;

public record IncomingWebSocketFrame(IncomingFrameType type, String payload) {
}
