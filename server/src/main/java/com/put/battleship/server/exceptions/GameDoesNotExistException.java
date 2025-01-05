package com.put.battleship.server.exceptions;

public class GameDoesNotExistException extends Exception {
    public GameDoesNotExistException(String message) {
        super(message);
    }
}
