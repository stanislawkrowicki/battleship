package com.put.battleship.server.exceptions;

public class GameAlreadyExistsException extends Exception {
    public GameAlreadyExistsException(String message) {
        super(message);
    }
}
