package com.put.battleship.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class BattleShipsApp extends Application {
    public static iBattleships model = new BattleShips();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BattleShipsApp.class.getResource("title_screen.fxml"));
        Parent controller = fxmlLoader.load();
        Scene scene = new Scene(controller, 1500, 800);

        Image icon = new Image(getClass().getResource("battleship.jpg").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Bajtelszips :D");
        stage.setScene(scene);
        stage.show();
    }
}