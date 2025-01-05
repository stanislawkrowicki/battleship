package com.put.battleship.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ship {
    private final int size;
    private boolean verticality = false;
    private int headX, headY;


    public Ship(int ship_length, int x, int y, boolean vert) {
        size = ship_length;
        headX = x;
        headY = y;
        verticality = vert;
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

    public boolean isVertical() {
        return verticality;
    }
}
