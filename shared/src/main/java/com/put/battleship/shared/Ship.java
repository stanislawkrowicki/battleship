package com.put.battleship.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ship {
    private final int size;
    private boolean vertical = false;
    private int headX, headY;


    @JsonCreator
    public Ship(
            @JsonProperty("size") int shipLength,
            @JsonProperty("headX") int x,
            @JsonProperty("headY") int y,
            @JsonProperty("vertical") boolean vertical) {
        size = shipLength;
        headX = x;
        headY = y;
        this.vertical = vertical;
    }

    public void setHeadLocation(int x, int y) {
        headX = x;
        headY = y;
    }

    public int getSize() {
        return size;
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    @JsonProperty("vertical")
    public boolean isVertical() {
        return vertical;
    }
}
