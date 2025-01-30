package com.put.battleship.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {
    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneSwitcher.stage = stage;
    }

    public static void switchScene(String fxmlFile) {
        assert fxmlFile != null;
        URL resource = SceneSwitcher.class.getResource(fxmlFile);
        assert resource != null;

        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(resource);
                stage.getScene().setRoot(root);
                stage.show();
            } catch (IOException e) {
                System.out.println("Failed to load scene: " + fxmlFile);
                e.printStackTrace();
            }
        });
    }

    public static void switchToLoadingScreen() {
        switchScene("loading_screen.fxml");
    }

    public static void switchToSetShipScreen() {
        switchScene("set_ship_screen.fxml");
    }

    public static void switchToBattleScreen() {
        switchScene("battle_screen.fxml");
    }

    public static void switchToTitleScreen() {
        switchScene("title_screen.fxml");
    }

    public static void switchToVictoryScreen() {
        switchScene("victory_screen.fxml");
    }

    public static void switchToDefeatScreen() {
        switchScene("defeat_screen.fxml");
    }
}
