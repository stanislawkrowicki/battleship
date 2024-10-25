module com.put.battleship.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.put.battleship.client to javafx.fxml;
    exports com.put.battleship.client;
}