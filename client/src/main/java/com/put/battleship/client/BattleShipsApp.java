package com.put.battleship.client;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class BattleShipsApp extends Application {
    public static BattleShips model = new BattleShips();

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("../").load();

        String host = dotenv.get("HOST");
        int port = Integer.parseInt(dotenv.get("PORT"));

        try {
            new WebSocketClient(host, port).run();
            System.out.println("Connected to the server!");
        } catch (InterruptedException e) {
            System.out.println("Failed to connect to the server!!!");
            e.printStackTrace();
        }

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