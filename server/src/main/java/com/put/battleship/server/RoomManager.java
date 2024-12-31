package com.put.battleship.server;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomManager {
    private final HashMap<String, Room> rooms = new HashMap<>();

    public void addRoom(Room room) throws Exception {
        if (!rooms.containsKey(room.getId()))
            rooms.put(room.getId(), room);
        else
            throw new Exception("Room already exists");
    }

    public Room getRoom(String id) {
        return rooms.get(id);
    }

    public ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms.values());
    }
}
