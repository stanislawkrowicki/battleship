package com.put.battleship.server.exceptions;

public class RoomAlreadyExistsException extends Exception {
    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}
