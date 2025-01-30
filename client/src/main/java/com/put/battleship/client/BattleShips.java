package com.put.battleship.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.Ship;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ClientFrameType;
import com.put.battleship.shared.payloads.client.ShootPayload;
import javafx.scene.paint.Color;

public class BattleShips {

    private final Board yourBoard;
    private final Board enemyBoard;
    private final Object lock = new Object();
    public Color yourColor = Color.rgb(255, 255, 200);
    public Color enemyColor = Color.rgb(50, 168, 82);
    public HitHandler hitHandler;
    private String roomCode;
    private String nickname;
    private boolean canAttack = false;
    private boolean youShotHim = false;
    private boolean flag = false;
    private int lastShotRowIndex;
    private int lastShotColIndex;

    public BattleShips() {
        yourBoard = new Board();
        enemyBoard = new Board();
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void handleAttack(Integer rowIndex, Integer columnIndex) throws AttackNotPermitted, InterruptedException {
        if (!canAttack)
            throw new AttackNotPermitted();
        lastShotRowIndex = rowIndex;
        lastShotColIndex = columnIndex;
        canAttack = false;
        FrameSender.sendFrame(new ClientFrame(ClientFrameType.SHOOT,
                new ShootPayload(columnIndex, rowIndex)));
    }

    public void handleMyResult(boolean hit) {
        enemyBoard.handleAttack(hit, lastShotRowIndex, lastShotColIndex);
        if (hitHandler != null)
            hitHandler.handleHit(hit, lastShotRowIndex, lastShotColIndex, false);


    }

    public void handleEnemyResult(boolean hit, int row, int col) {
        yourBoard.handleAttack(hit, row, col);
        if (hitHandler != null)
            hitHandler.handleHit(hit, row, col, true);

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

    public void resetGame() {
        canAttack = false;
        yourBoard.resetShips();
        enemyBoard.resetShips();
    }


    public class AttackNotPermitted extends Throwable {
    }
}
