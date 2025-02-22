package com.put.battleship.shared.frames;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.payloads.client.EmptyPayload;
import com.put.battleship.shared.payloads.server.*;

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
            case CONNECTED -> frame.payload = mapper.treeToValue(payloadNode, ConnectedPayload.class);
            case GAME_CREATED -> frame.payload = mapper.treeToValue(payloadNode, GameCreatedPayload.class);
            case GAME_JOINED -> frame.payload = mapper.treeToValue(payloadNode, GameJoinedPayload.class);
            case ENEMY_SHOT -> frame.payload = mapper.treeToValue(payloadNode, EnemyShotPayload.class);
            case GAME_SHIPS_SET -> frame.payload = mapper.treeToValue(payloadNode, GameShipsSetPayload.class);
            default -> mapper.treeToValue(payloadNode, EmptyPayload.class);
        }

        return frame;
    }
}
