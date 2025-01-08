package com.put.battleship.client.senders;

import com.put.battleship.shared.Ship;
import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ClientFrameType;
import com.put.battleship.shared.payloads.client.SetShipsPayload;

public class SetShipsFrameSender extends FrameSender {
    private final Ship[] ships;

    public SetShipsFrameSender(Ship[] ships) {
        this.ships = ships;
    }

    @Override
    public void send() {
        _send(new ClientFrame(ClientFrameType.SET_SHIPS, new SetShipsPayload(ships)));
    }
}
