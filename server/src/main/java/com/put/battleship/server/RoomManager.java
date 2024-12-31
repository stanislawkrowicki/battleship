package com.put.battleship.server;

import com.put.battleship.server.exceptions.RoomAlreadyExistsException;
import com.put.battleship.server.exceptions.RoomIsFullException;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomManager {
    private static final HashMap<String, Room> rooms = new HashMap<>();

    public static void addRoom(Room room) throws RoomAlreadyExistsException {
        if (!rooms.containsKey(room.getId()))
            rooms.put(room.getId(), room);
        else
            throw new RoomAlreadyExistsException("Room already exists");
    }

    public static Room getRoom(String id) {
        return rooms.get(id);
    }

    public static ArrayList<Room> getRooms() {
        return new ArrayList<>(rooms.values());
    }

    public static void removeRoom(String id) {
        rooms.remove(id);
    }

    public static void addPlayerToRoom(String roomId, Player player) throws RoomIsFullException {
        rooms.get(roomId).addPlayer(player);
    }
}
