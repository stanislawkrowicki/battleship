package com.put.battleship.shared.frames;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ServerFrameDeserializer.class)
public class ServerFrame {
    public ServerFrameType type;
    public Object payload;

    public ServerFrame(ServerFrameType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
