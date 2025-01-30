package com.put.battleship.server;

import com.put.battleship.server.exceptions.GameAlreadyExistsException;
import com.put.battleship.server.exceptions.GameDoesNotExistException;
import com.put.battleship.server.exceptions.GameIsFullException;

import java.util.ArrayList;
import java.util.Objects;

public class GameManager {
    private static final ArrayList<Game> games = new ArrayList<>();

    public static void addGame(Game game) {
        games.add(game);
    }

    public static Game createGame(Player host, String joinCode) throws GameAlreadyExistsException {
        if (getGameByJoinCode(joinCode) != null) {
            throw new GameAlreadyExistsException("Game with this join code already exists.");
        }

        Game game = new Game(host, joinCode);
        addGame(game);
        return game;
    }

    public static Game getGameById(String id) {
        return games.stream().filter(game -> game.getId().toString().equals(id)).findFirst().orElse(null);
    }

    public static Game getGameByJoinCode(String joinCode) {
        return games.stream().filter(game -> game.getJoinCode().equals(joinCode)).findFirst().orElse(null);
    }

    public static Game getGameByHost(Player host) {
        return games.stream().filter(game -> game.getHost().equals(host)).findFirst().orElse(null);
    }

    public static Game getGameByPlayer(Player player) {
        return games.stream()
                .filter(game -> Objects.equals(game.getHost(), player) || Objects.equals(game.getGuest(), player))
                .findFirst()
                .orElse(null);
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void removeGame(Game game) {
        games.remove(game);
    }

    public static void connectPlayerToRoomById(Player player, String roomId) throws GameDoesNotExistException, GameIsFullException {
        Game game = getGameById(roomId);

        if (game == null) {
            throw new GameDoesNotExistException("Game does not exist.");
        }
        if (game.getGuest() != null || game.isStarted()) {
            throw new GameIsFullException("There are already two players in the game.");
        }

        game.joinGuest(player);
    }

    public static Game connectPlayerToRoomByJoinCode(Player player, String joinCode) throws GameDoesNotExistException, GameIsFullException {
        Game game = getGameByJoinCode(joinCode);

        if (game == null) {
            throw new GameDoesNotExistException("Game does not exist.");
        }
        if (game.getGuest() != null || game.isStarted()) {
            throw new GameIsFullException("There are already two players in the game.");
        }

        game.joinGuest(player);

        return game;
    }
}
