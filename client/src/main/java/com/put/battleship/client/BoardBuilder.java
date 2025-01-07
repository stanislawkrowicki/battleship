package com.put.battleship.client;

import com.put.battleship.shared.Ship;

public class BoardBuilder extends Board {
    public void addShip(Ship ship) {
        ships.add(ship);
        fillShip(ship, boardMatrix, ships.size());
    }

    public Ship removeLastShip() {
        Ship ship = ships.removeLast();
        fillShip(ship, boardMatrix, 0);
        return ship;
    }

    public boolean isValidPosition(Ship ship) {
        int x = ship.getHeadX();
        int y = ship.getHeadY();
        for (int i = 0; i < ship.getSize(); i++) {
            if (!isValidPosition(x, y))
                return false;
            if (ship.isVertical())
                y++;
            else x++;
        }
        return true;
    }

    public boolean isValidPosition(int x, int y) {
        if (x >= sizex || y >= sizey || x < 0 || y < 0)
            return false;
        if (boardMatrix[y][x] > 0)
            return false;
        if (y > 0 && boardMatrix[y - 1][x] > 0)
            return false;
        if (y < sizey - 1 && boardMatrix[y + 1][x] > 0)
            return false;
        if (x > 0 && boardMatrix[y][x - 1] > 0)
            return false;
        return x >= sizex - 1 || boardMatrix[y][x + 1] <= 0;
    }


}
