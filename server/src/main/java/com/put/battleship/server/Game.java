package com.put.battleship.server;

import com.put.battleship.shared.Ship;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Game {
    private final UUID id;
    private final Player host;
    private Player guest;
    private final String joinCode;
    private boolean isStarted = false;

    private final int BOARD_SIZE = 10;

    private Ship[] hostShips = new Ship[10];
    private Ship[] guestShips = new Ship[10];

    private int[][] hostBoard = new int[BOARD_SIZE][BOARD_SIZE];
    private int[][] guestBoard = new int[BOARD_SIZE][BOARD_SIZE];

    private Player hasTurn = null;

    public Game(Player host, String joinCode) {
        this.host = host;
        this.id = UUID.randomUUID();

        this.joinCode = Objects.requireNonNullElseGet(joinCode, this::generateJoinCode);
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

    private String generateJoinCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
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

    public void setShipsForPlayer(Player player, Ship[] ships) throws IllegalArgumentException {
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < ships.length; i++) {
            fillShip(ships[i], board, i + 1);
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
            return hostShips.length == 10;
        else if (player == guest)
            return guestShips.length == 10;
        else
            throw new IllegalArgumentException("Player is not in this game");
    }

    public boolean areBothPlayersReady() {
        if (host == null || guest == null)
            return false;

        return hostShips.length == 10 && guestShips.length == 10;
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
