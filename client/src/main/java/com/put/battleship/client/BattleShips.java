package com.put.battleship.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.Ship;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ClientFrameType;
import com.put.battleship.shared.payloads.client.CreateGamePayload;
import com.put.battleship.shared.payloads.client.ShootPayload;
import javafx.scene.paint.Color;

public class BattleShips {

    private final Board yourBoard;
    private final Board enemyBoard;
    private final Object lock = new Object();
    public Color yourColor = Color.rgb(255, 255, 200);
    public Color enemyColor = Color.rgb(50, 168, 82);
    private String roomCode;
    private boolean canAttack = false;
    private boolean youShotHim = false;
    private boolean flag = false;

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

    public void setYouShotHim(boolean youShotHim) {
        this.youShotHim = youShotHim;
    }

    public void setFlag(boolean flagValue) {
        flag = flagValue;
    }

    public Object getLock() { // Getter for lock
        return lock;
    }

    public boolean handleAttack(Integer rowIndex, Integer columnIndex) throws AttackNotPermitted, InterruptedException {
        if (!canAttack)
            throw new AttackNotPermitted();

        FrameSender.sendFrame(new ClientFrame(ClientFrameType.SHOOT,
                new ShootPayload(rowIndex, columnIndex)));


        synchronized (lock) { // Ensure the thread owns the monitor
            while (!flag) {
                try {
                    lock.wait(); // This will now work correctly
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            flag = false;
            canAttack = false;
        }
        boolean hit = enemyBoard.handleAttack(rowIndex, columnIndex);
        return youShotHim;
    }

    public void setYourShips(Ship[] ships) {
        yourBoard.setShips(ships);
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

    public void enableAttack() {
        canAttack = true;
    }

    public void disableAttack() {
        canAttack = false;
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
