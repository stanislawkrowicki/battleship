package com.put.battleship.client.senders;

import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ClientFrameType;
import com.put.battleship.shared.payloads.client.JoinGamePayload;

public class JoinGameFrameSender extends FrameSender {
    private final String joinCode;

    public JoinGameFrameSender(String joinCode) {
        super();
        this.joinCode = joinCode;
    }

    @Override
    public void send() {
        _send(new ClientFrame(ClientFrameType.JOIN_GAME, new JoinGamePayload(joinCode)));
    }
}
