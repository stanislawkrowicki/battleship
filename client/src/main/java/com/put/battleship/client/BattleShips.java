package com.put.battleship.client;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleShips {

    private final int sizey;
    private final int sizex;
    private final List<Ship> yourShips;
    private final int[][] yourBoardMatrix;
    private final int[][] enemyBoardMatrix;
    private boolean canAttack = false;

    public BattleShips() {
        sizex = sizey = 10;
        yourBoardMatrix = new int[sizey][sizex];
        enemyBoardMatrix = new int[sizey][sizex];
        yourShips = new ArrayList<Ship>();
    }

    public int getSizex() {
        return sizex;
    }

    public int getSizey() {
        return sizey;
    }

    public double color(int row, int col) {
        return Math.random();
    }

    public boolean handleAttack(Integer rowIndex, Integer columnIndex) throws AttackNotPermitted {
        if (!canAttack)
            throw new AttackNotPermitted();
        boolean hit = enemyBoardMatrix[rowIndex][columnIndex] == 1;
        if (!hit)
            canAttack = false;
        return hit;
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public boolean isShipInYourBoard(int y, int x) {
        return yourBoardMatrix[y][x] > 0;
    }

    public int[] shipsConfig() {
        return new int[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    }

    public int yourShipCount() {
        return yourShips.size();
    }

    public Ship removeLastShip() {
        Ship ship = yourShips.removeLast();
        fillShip(ship, yourBoardMatrix, 0);
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
        if (yourBoardMatrix[y][x] > 0)
            return false;
        if (y > 0 && yourBoardMatrix[y - 1][x] > 0)
            return false;
        if (y < sizey - 1 && yourBoardMatrix[y + 1][x] > 0)
            return false;
        if (x > 0 && yourBoardMatrix[y][x - 1] > 0)
            return false;
        return x >= sizex - 1 || yourBoardMatrix[y][x + 1] <= 0;
    }

    public void addShip(Ship ship) {
        yourShips.add(ship);
        fillShip(ship, yourBoardMatrix, yourShips.size());
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

    //public final Map<String, Object> AsHashMap() {
//    public void createCarrier() {
//        carrier.put("type", "carrier");
//        List<int[]> positions = new ArrayList<>();
//        positions.add(new int[]{0, 4});
//        positions.add(new int[]{1, 4});
//        positions.add(new int[]{2, 4});
//        positions.add(new int[]{3, 4});
//        carrier.put("positions", positions);
//    }
    //}
//    public void translateToMatrix() {
//        List<int[]> retrievedPositions = (List<int[]>) carrier.get("positions");
//        for (int[] position : retrievedPositions) {
//            yourBoardMatrix[position[0]][position[1]] = 1;
//            enemyBoardMatrix[position[0]][position[1]] = 1;
//        }
//    }


    public class AttackNotPermitted extends Throwable {
    }
}
