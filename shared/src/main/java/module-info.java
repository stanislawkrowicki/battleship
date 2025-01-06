module com.put.battleship.shared {
    requires com.fasterxml.jackson.databind;
    exports com.put.battleship.shared;
    exports com.put.battleship.shared.frames;
    exports com.put.battleship.shared.payloads.client;
    exports com.put.battleship.shared.payloads.server;
}