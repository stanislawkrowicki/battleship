package com.put.battleship.client;

import com.put.battleship.shared.Ship;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] boardMatrix;
    private final List<Ship> ships;
    private final int sizey = 10;
    private final int sizex = 10;
    public Color backgroundColor;

    public Board(Color color) {
        boardMatrix = new int[sizey][sizex];
        ships = new ArrayList<Ship>();
        backgroundColor = color;
    }

    public int getSizex() {
        return sizex;
    }

    public int getSizey() {
        return sizey;
    }

    public int shipCount() {
        return ships.size();
    }


    public void addShip(Ship ship) {
        ships.add(ship);
        fillShip(ship, boardMatrix, ships.size());
    }

    public Ship removeLastShip() {
        Ship ship = ships.removeLast();
        fillShip(ship, boardMatrix, 0);
        return ship;
    }

    public boolean cellNotYetShot(int row, int col) {
        return boardMatrix[row][col] >= 0;
    }

    public boolean isShipInBoard(int y, int x) {
        return boardMatrix[y][x] > 0;
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

    private void fillShip(Ship ship, int[][] matrix, int indexOfShip) {
        int x = ship.getHeadX();
        int y = ship.getHeadY();
        for (int i = 0; i < ship.getSize(); i++) {
            matrix[y][x] = indexOfShip;
            if (ship.isVertical())
                y++;
            else x++;
        }
    }

//    public void example1() {
//        Ship ship1 = new Ship(4, 0, 0, true);
//        //Ship ship2 = new Ship(4, 3, 7, true);
//        //Ship ship3 = new Ship(3, 8, 3, false);
//        addShip(ship1);
//        // addShip(ship2);
//        //addShip(ship3);
//    }


    public boolean allShipsGone() {
        for (int row = 0; row < sizey; row++) {
            for (int col = 0; col < sizex; col++) {
                if (boardMatrix[row][col] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean handleAttack(Integer row, Integer col) {
        boolean hit = isShipInBoard(row, col);
        if (!hit) {
            boardMatrix[row][col] = -shipCount() - 1;
        } else {
            boardMatrix[row][col] = -boardMatrix[row][col];
        }
        return hit;
    }
}
