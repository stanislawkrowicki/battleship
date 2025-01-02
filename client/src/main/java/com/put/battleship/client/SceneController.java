package com.put.battleship.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Parent root;


    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();


    }

    public void switchToLoadingScreen(ActionEvent event) throws IOException {
        switchScene(event, "loading_screen.fxml");
    }


    public void switchToSetShipScene(ActionEvent event) throws IOException {
        switchScene(event, "set_ship_screen.fxml");
    }

    public void switchToBattleScreen(ActionEvent event) throws IOException {
        switchScene(event, "battle_screen.fxml");
    }

    public void switchToTitleScreen(ActionEvent event) throws IOException {
        switchScene(event, "title_screen.fxml");

    }


}
