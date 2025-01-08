package com.put.battleship.client.senders;

import com.put.battleship.shared.frames.ClientFrame;

public abstract class FrameSender {
    protected void _send(ClientFrame frame) {
        FrameSenderManager.sendFrame(frame);
    }

    public abstract void send();
}
