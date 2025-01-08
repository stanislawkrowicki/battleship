package com.put.battleship.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.Ship;
import javafx.scene.paint.Color;

public class BattleShips {

    private final Board yourBoard;
    private final Board enemyBoard;
    public Color yourColor = Color.rgb(255, 255, 200);
    public Color enemyColor = Color.rgb(50, 168, 82);
    private String roomCode;
    private boolean canAttack = true;

    public BattleShips() {
        yourBoard = new Board();
        enemyBoard = new Board();
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public int getSizex() {
        return yourBoard.getSizex();
    }

    public int getSizey() {
        return yourBoard.getSizey();
    }

    public boolean enemyCellNotYetShot(int row, int col) {
        return enemyBoard.cellNotYetShot(row, col);
    }

    public boolean yourCellNotYetShot(int row, int col) {
        return yourBoard.cellNotYetShot(row, col);
    }

    public Color getEnemyBackgroundColor() {
        return enemyColor;
    }

    public Color getYourBackgroundColor() {
        return yourColor;
    }

    public boolean handleAttack(Integer rowIndex, Integer columnIndex) throws AttackNotPermitted {
        if (!canAttack)
            throw new AttackNotPermitted();

        boolean hit = enemyBoard.handleAttack(rowIndex, columnIndex);
        if (!hit)
            canAttack = true;
        return hit;
    }

    public void setYourShips(Ship[] ships) {
        yourBoard.setShips(ships);
        enemyBoard.setShips(ships);
        //send to server
        System.out.println("Ships set");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(ships);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public boolean isShipInYourBoard(int y, int x) {
        return yourBoard.isShipInBoard(y, x);
    }

    public int[] shipsConfig() {
        return new int[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    }

    public int yourShipCount() {
        return yourBoard.shipCount();
    }

    public boolean youWin() {
        return enemyBoard.allShipsGone();
    }

    public boolean youLose() {
        return yourBoard.allShipsGone();
    }

    public class AttackNotPermitted extends Throwable {
    }
}
