package com.put.battleship.server.exceptions;

public class GameIsFullException extends Exception {
    public GameIsFullException(String message) {
        super(message);
    }
}
