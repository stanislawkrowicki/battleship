package com.put.battleship.server;

public class Game {
    private final String id;
    private final Player host;
    private Player guest;
    private boolean isStarted = false;

    public Game(String id, Player host) {
        this.id = id;
        this.host = host;
    }

    public String getId() {
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

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
