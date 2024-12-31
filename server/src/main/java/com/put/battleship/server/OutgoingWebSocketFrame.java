package com.put.battleship.server;

public record OutgoingWebSocketFrame(OutgoingFrameType type, String payload) {
}
