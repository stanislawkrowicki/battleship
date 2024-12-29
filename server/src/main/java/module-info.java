module com.put.battleship.server {
    requires io.github.cdimascio.dotenv.java;
    requires com.fasterxml.jackson.databind;
    requires io.netty.transport;
    requires io.netty.codec.http;
    exports com.put.battleship.server;
    exports com.put.battleship.server.handlers;
}