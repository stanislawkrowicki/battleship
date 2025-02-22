package com.put.battleship.shared.frames;

public enum ServerFrameType {
    INVALID_FRAME,
    CONNECTED,
    GAME_CREATED,
    GAME_NOT_FOUND,
    GAME_IS_FULL,
    GAME_ALREADY_EXISTS,
    WAITING_FOR_OPPONENT,
    PLAYER_IS_NOT_HOST,
    GAME_JOINED,
    GAME_STARTED,
    GAME_SHIPS_SET,
    GAME_LOST,
    GAME_WON,
    SHIPS_OK,
    SHIPS_NOT_OK,
    SHOT_HIT,
    SHOT_MISS,
    ENEMY_SHOT,
    YOUR_TURN
}
