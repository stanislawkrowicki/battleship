package com.put.battleship.client.senders;

import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ClientFrameType;
import com.put.battleship.shared.payloads.client.CreateGamePayload;

public class CreateGameFrameSender extends FrameSender {
    private final String joinCode;

    public CreateGameFrameSender(String joinCode) {
        super();
        this.joinCode = joinCode;
    }

    @Override
    public void send() {
        _send(new ClientFrame(ClientFrameType.CREATE_GAME, new CreateGamePayload(joinCode)));
    }
}
