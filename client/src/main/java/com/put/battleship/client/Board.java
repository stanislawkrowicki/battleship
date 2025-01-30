package com.put.battleship.client;

import com.put.battleship.shared.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    protected final int sizey = 10;
    protected final int sizex = 10;
    protected int[][] boardMatrix;
    protected List<Ship> ships;

    public Board() {
        boardMatrix = new int[sizey][sizex];
        ships = new ArrayList<Ship>();
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


    public boolean cellNotYetShot(int row, int col) {
        return boardMatrix[row][col] >= 0;
    }

    public boolean isShipInBoard(int y, int x) {
        return boardMatrix[y][x] > 0;
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

    public void handleAttack(boolean hit, Integer row, Integer col) {
        if (!hit) {
            boardMatrix[row][col] = -sizex * sizey - 1;
        } else {
            boardMatrix[row][col] = -boardMatrix[row][col];
            if (boardMatrix[row][col] == 0) {
                boardMatrix[row][col] = -1;
            }
        }
    }

    public Ship[] getShips() {
        return ships.toArray(new Ship[shipCount()]);
    }

    public void setShips(Ship[] shipArray) {
        ships = Arrays.stream(shipArray).toList();
        for (Ship s : ships) {
            fillShip(s, boardMatrix, ships.size());
        }

    }

    public void resetShips() {
        ships.clear();
        boardMatrix = new int[sizey][sizex];
    }

    protected void fillShip(Ship ship, int[][] matrix, int indexOfShip) {
        int x = ship.getHeadX();
        int y = ship.getHeadY();
        for (int i = 0; i < ship.getSize(); i++) {
            matrix[y][x] = indexOfShip;
            if (ship.isVertical())
                y++;
            else x++;
        }
    }
}
