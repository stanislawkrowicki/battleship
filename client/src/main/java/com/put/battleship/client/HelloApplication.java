package com.put.battleship.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("title_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 563);

        Image icon = new Image(getClass().getResource("battleship.jpg").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Bajtelszips :D");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}