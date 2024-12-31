package com.put.battleship.server;

import java.util.ArrayList;

public class Room {
    private final String id;
    private final String name;
    private final int size;
    private final ArrayList<Player> players = new ArrayList<>();

    public Room(String id, String name, int size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) throws Exception {
        if (players.size() < size)
            players.add(player);
        else
            throw new Exception("Room is full");
    }

}
