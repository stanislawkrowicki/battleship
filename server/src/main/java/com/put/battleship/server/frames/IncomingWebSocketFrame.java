package com.put.battleship.server.frames;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FrameDeserializer.class)
public class IncomingWebSocketFrame {
    public IncomingFrameType type;
    public Object payload;
}
