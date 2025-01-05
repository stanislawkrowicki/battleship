package com.put.battleship.server.exceptions;

public class RoomIsFullException extends Exception {
    public RoomIsFullException(String message) {
        super(message);
    }
}