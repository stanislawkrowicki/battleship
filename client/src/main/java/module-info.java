module com.put.battleship.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires io.netty.transport;
    requires io.netty.codec.http;
    requires com.put.battleship.shared;
    requires com.fasterxml.jackson.databind;
    requires io.github.cdimascio.dotenv.java;


    opens com.put.battleship.client to javafx.fxml;
    exports com.put.battleship.client;
}