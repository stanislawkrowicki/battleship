package com.put.battleship.client;

import com.put.battleship.shared.frames.ClientFrame;
import com.put.battleship.shared.frames.ClientFrameType;
import com.put.battleship.shared.payloads.client.CreateGamePayload;
import com.put.battleship.shared.payloads.client.JoinGamePayload;
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
            FrameSender.sendFrame(new ClientFrame(ClientFrameType.CREATE_GAME,
                    new CreateGamePayload(roomCode)));
        } else {
            errorLabel.setText("Enter correct code format :)");

        }
    }

    public void joinRoom(ActionEvent event) throws IOException {
        roomCode = roomCodeField.getText();
        if (isCorrectFormat(roomCode)) {
            BattleShipsApp.model.setRoomCode(roomCode);
            FrameSender.sendFrame(new ClientFrame(ClientFrameType.JOIN_GAME,
                    new JoinGamePayload(roomCode)));
        } else {
            errorLabel.setText("Enter correct code format :)");
        }
    }

}
