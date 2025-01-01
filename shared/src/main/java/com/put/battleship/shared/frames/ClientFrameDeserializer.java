package com.put.battleship.shared.frames;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.payloads.CreateRoomPayload;

import java.io.IOException;

public class ClientFrameDeserializer extends JsonDeserializer<Object> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode payloadNode = node.get("payload");

        ClientFrameType type = ClientFrameType.valueOf(node.get("type").asText());
        ClientFrame frame = new ClientFrame(type, null);
        frame.type = type;

        if (type == ClientFrameType.CREATE_ROOM) {
            frame.payload = mapper.treeToValue(payloadNode, CreateRoomPayload.class);
        } else {
            throw new IllegalArgumentException("Unknown client frame type: " + type);
        }

        return frame;
    }
}
