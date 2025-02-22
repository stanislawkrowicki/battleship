package com.put.battleship.shared.frames;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.payloads.client.*;

import java.io.IOException;

public class ClientFrameDeserializer extends JsonDeserializer<Object> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode payloadNode = node.get("payload");

        ClientFrameType type;

        try {
            type = ClientFrameType.valueOf(node.get("type").asText());
        } catch (IllegalArgumentException e) {
            throw new InvalidFrameTypeException(ClientFrameType.valueOf(node.get("type").asText()));
        }

        ClientFrame frame = new ClientFrame(type, null);
        frame.type = type;

        frame.payload = switch (type) {
            case CREATE_GAME -> mapper.treeToValue(payloadNode, CreateGamePayload.class);
            case JOIN_GAME -> mapper.treeToValue(payloadNode, JoinGamePayload.class);
            case SET_SHIPS -> mapper.treeToValue(payloadNode, SetShipsPayload.class);
            case SHOOT -> mapper.treeToValue(payloadNode, ShootPayload.class);
            default -> mapper.treeToValue(payloadNode, EmptyPayload.class);
        };

        return frame;
    }
}
