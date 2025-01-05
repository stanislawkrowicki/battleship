package com.put.battleship.shared.frames;

public class InvalidFrameTypeException extends IllegalArgumentException {
    public InvalidFrameTypeException(ClientFrameType type) {
        super("Invalid frame type: " + type);
    }
}
