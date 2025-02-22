package com.put.battleship.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private static final HashMap<UUID, Player> playerMap = new HashMap<>();

    public static Player createPlayer() {
        UUID id = UUID.randomUUID();
        Player player = new Player(id);
        playerMap.put(id, player);
        return player;
    }

    public static Player getPlayer(UUID id) {
        return playerMap.get(id);
    }
}
