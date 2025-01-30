package com.put.battleship.client;

@FunctionalInterface
public interface HitHandler {
    void handleHit(boolean hit, int rowIndex, int colIndex, boolean yours);
}
