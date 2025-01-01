package com.put.battleship.shared.frames;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ClientFrameDeserializer.class)
public class ClientFrame {
    public ClientFrameType type;
    public Object payload;

    public ClientFrame(ClientFrameType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
