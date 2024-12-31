package com.put.battleship.server.frames;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.server.payloads.CreateRoomPayload;

import java.io.IOException;

public class FrameDeserializer extends JsonDeserializer<Object> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode payloadNode = node.get("payload");

        IncomingFrameType type = IncomingFrameType.valueOf(node.get("type").asText());
        IncomingWebSocketFrame frame = new IncomingWebSocketFrame();
        frame.type = type;

        if (type == IncomingFrameType.CREATE_ROOM) {
            frame.payload = mapper.treeToValue(payloadNode, CreateRoomPayload.class);
        } else {
            throw new IllegalArgumentException("Unknown frame type: " + type);
        }

        return frame;
    }
}
