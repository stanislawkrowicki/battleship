package com.put.battleship.server;

import com.put.battleship.server.exceptions.GameDoesNotExistException;
import com.put.battleship.server.exceptions.GameIsFullException;

import java.util.ArrayList;

public class GameManager {
    private static final ArrayList<Game> games = new ArrayList<>();

    public static void addGame(Game game) {
        games.add(game);
    }

    public static Game createGame(Player host) {
        Game game = new Game(host);
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
        return games.stream().filter(game -> game.getHost().equals(player) || game.getGuest().equals(player)).findFirst().orElse(null);
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void removeGame(String id) {
        games.removeIf(game -> game.getId().toString().equals(id));
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

    public static void connectPlayerToRoomByJoinCode(Player player, String joinCode) throws GameDoesNotExistException, GameIsFullException {
        Game game = getGameByJoinCode(joinCode);

        if (game == null) {
            throw new GameDoesNotExistException("Game does not exist.");
        }
        if (game.getGuest() != null || game.isStarted()) {
            throw new GameIsFullException("There are already two players in the game.");
        }

        game.joinGuest(player);
    }
}
