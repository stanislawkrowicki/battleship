package com.put.battleship.server.frames;

public record OutgoingWebSocketFrame(OutgoingFrameType type, String payload) {
}
