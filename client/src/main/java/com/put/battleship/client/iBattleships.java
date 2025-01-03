package com.put.battleship.client;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface iBattleships {
    int[] shipsConfig();

    int yourShipCount();

    Ship removeLastShip();

    boolean isValidPosition(Ship ship);

    void addShip(Ship ship);

    boolean isShipInYourBoard(int y, int x);

    int getSizex();

    int getSizey();

    boolean getCanAttack();

    double color(int row, int col);

    boolean handleAttack(Integer rowIndex, Integer columnIndex) throws BattleShips.AttackNotPermitted;


    Board getYourBoard();

    Board getEnemyBoard();

    Color getYourBackgroundColor();

    Color getEnemyBackgroundColor();

    boolean enemyCellNotYetShot(int row, int col);

    boolean yourCellNotYetShot(int row, int col);
}
