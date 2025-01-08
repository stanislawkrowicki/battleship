package com.put.battleship.client.senders;

import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ClientFrameType;

public class CreateGameFrameSender extends FrameSender {

    @Override
    public void send() {
        _send(new ClientFrame(ClientFrameType.CREATE_GAME, null));
    }
}
