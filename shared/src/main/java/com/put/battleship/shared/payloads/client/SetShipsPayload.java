package com.put.battleship.shared.payloads.client;

import com.put.battleship.shared.Ship;

import java.util.ArrayList;

public record SetShipsPayload(ArrayList<Ship> ships) {
}
