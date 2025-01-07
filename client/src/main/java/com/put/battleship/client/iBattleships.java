package com.put.battleship.client;

import com.put.battleship.shared.Ship;
import javafx.scene.paint.Color;

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

    Color getYourBackgroundColor();

    Color getEnemyBackgroundColor();

    Board getYourBoard();

    Board getEnemyBoard();

    boolean enemyCellNotYetShot(int row, int col);

    boolean yourCellNotYetShot(int row, int col);

    boolean youWin();
}
