package com.put.battleship.server;

import com.put.battleship.server.exceptions.GameDoesNotExistException;
import com.put.battleship.server.exceptions.GameIsFullException;

import java.util.ArrayList;

public class GameManager {
    private static final ArrayList<Game> games = new ArrayList<>();

    public static void addGame(Game game) {
        games.add(game);
    }

    public static Game getGame(String id) {
        return games.stream().filter(game -> game.getId().equals(id)).findFirst().orElse(null);
    }

    public static Game getGameByHost(Player host) {
        return games.stream().filter(game -> game.getHost().equals(host)).findFirst().orElse(null);
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void removeGame(String id) {
        games.removeIf(game -> game.getId().equals(id));
    }

    public static void connectPlayerToRoom(Player player, String roomId) throws GameDoesNotExistException, GameIsFullException {
        Game game = getGame(roomId);

        if (game == null) {
            throw new GameDoesNotExistException("Game does not exist.");
        }
        if (game.getGuest() != null || game.isStarted()) {
            throw new GameIsFullException("There are already two players in the game.");
        }

        game.joinGuest(player);
    }
}
