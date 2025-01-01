package com.put.battleship.server;

import java.util.UUID;

public class Game {
    private final UUID id;
    private final Player host;
    private Player guest;
    private boolean isStarted = false;

    public Game(Player host) {
        this.host = host;
        this.id = UUID.randomUUID();
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

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
