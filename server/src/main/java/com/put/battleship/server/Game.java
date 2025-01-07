package com.put.battleship.server;

import com.put.battleship.shared.Ship;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Game {
    private final UUID id;
    private final Player host;
    private Player guest;
    private final String joinCode;
    private boolean isStarted = false;

    private final int BOARD_SIZE = 10;

    private ArrayList<Ship> hostShips = new ArrayList<>();
    private ArrayList<Ship> guestShips = new ArrayList<>();

    private int[][] hostBoard = new int[BOARD_SIZE][BOARD_SIZE];
    private int[][] guestBoard = new int[BOARD_SIZE][BOARD_SIZE];

    private Player hasTurn = null;

    public Game(Player host) {
        this.host = host;
        this.id = UUID.randomUUID();

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        this.joinCode = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public UUID getId() {
        return id;
    }

    public Player getHost() {
        return host;
    }

    public void joinGuest(Player player) {
        guest = player;
    }

    public Player getGuest() {
        return guest;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public Player getOpponent(Player player) throws IllegalArgumentException {
        if (player == host) {
            return guest;
        } else if (player == guest) {
            return host;
        } else {
            throw new IllegalArgumentException("Player is not in this game");
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void start() {
        isStarted = true;
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

    public void setShipsForPlayer(Player player, ArrayList<Ship> ships) throws IllegalArgumentException {
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < ships.size(); i++) {
            fillShip(ships.get(i), board, i + 1);
        }

        if (player == host) {
            hostShips = ships;
            hostBoard = board;
        } else if (player == guest) {
            guestShips = ships;
            guestBoard = board;
        } else {
            throw new IllegalArgumentException("Player is not in this game");
        }
    }

    public boolean isPlayerReady(Player player) throws IllegalArgumentException {
        if (player == host)
            return hostShips.size() == 10;
        else if (player == guest)
            return guestShips.size() == 10;
        else
            throw new IllegalArgumentException("Player is not in this game");
    }

    public boolean areBothPlayersReady() {
        return hostShips.size() == 10 && guestShips.size() == 10;
    }

    public boolean isPlayerTurn(Player player) {
        return hasTurn == player;
    }

    public Player switchTurn() {
        return hasTurn = hasTurn == host ? guest : host;
    }

    public boolean handleShoot(Player player, int x, int y) throws IllegalArgumentException {
        int[][] board;

        if (player == host) {
            board = guestBoard;
        } else if (player == guest) {
            board = hostBoard;
        } else {
            throw new IllegalArgumentException("Player is not in this game");
        }

        if (board[y][x] > 0) {
            board[y][x] = -1;
            return true;
        } else {
            board[y][x] = 0;
            return false;
        }
    }

    public boolean allShipsDestroyed(Player player) throws IllegalArgumentException {
        int[][] board;

        if (player == host) {
            board = guestBoard;
        } else if (player == guest) {
            board = hostBoard;
        } else {
            throw new IllegalArgumentException("Player is not in this game");
        }

        for (int[] row : board) {
            for (int cell : row) {
                if (cell > 0) {
                    return false;
                }
            }
        }

        return true;
    }
}
