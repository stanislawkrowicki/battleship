package com.put.battleship.server;

import java.util.Random;
import java.util.UUID;

public class Game {
    private final UUID id;
    private final Player host;
    private Player guest;
    private final String joinCode;
    private boolean isStarted = false;

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
    
    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
