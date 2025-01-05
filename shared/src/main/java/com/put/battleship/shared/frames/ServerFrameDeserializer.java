package com.put.battleship.shared.frames;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.payloads.client.EmptyPayload;

import java.io.IOException;

public class ServerFrameDeserializer extends JsonDeserializer<Object> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode payloadNode = node.get("payload");

        ServerFrameType type = ServerFrameType.valueOf(node.get("type").asText());
        ServerFrame frame = new ServerFrame(type, null);

        switch (type) {
            case CONNECTED -> frame.payload = mapper.treeToValue(payloadNode, EmptyPayload.class);
            default -> throw new IllegalArgumentException("Unknown server frame type: " + type);
        }

        return frame;
    }
}
