package com.put.battleship.client;

import com.put.battleship.client.senders.CreateGameFrameSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RoomSelectController {
    SceneController sceneController = new SceneController();
    String roomCode;

    @FXML
    private TextField roomCodeField;

    @FXML
    private Label errorLabel;

    public boolean isCorrectFormat(String code) {
        return code.length() <= 8 && code.length() >= 4;
    }

    public void createRoom(ActionEvent event) throws IOException {
        roomCode = roomCodeField.getText();
        if (isCorrectFormat(roomCode)) {
            BattleShipsApp.model.setRoomCode(roomCode);
            new CreateGameFrameSender(roomCode).send();
        } else {
            errorLabel.setText("Enter correct code format :)");

        }
    }

    public void joinRoom(ActionEvent event) throws IOException {
        roomCode = roomCodeField.getText();
        if (isCorrectFormat(roomCode)) {
            BattleShipsApp.model.setRoomCode(roomCode);
            sceneController.switchScene(event, "loading_screen.fxml");
        } else {
            errorLabel.setText("Enter correct code format :)");

        }
    }
    
}
