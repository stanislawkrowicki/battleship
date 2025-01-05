package com.put.battleship.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.UUID;

public class Player {
    public static final AttributeKey<String> PLAYER_UUID_KEY = AttributeKey.valueOf("player");

    private final UUID id;

    private String name = "";

    public Player(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean wasNamed() {
        return !name.isEmpty();
    }
}
