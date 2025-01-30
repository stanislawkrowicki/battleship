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
    String nickname;

    @FXML
    private TextField roomCodeField;
    @FXML
    private TextField nickNameField;

    @FXML
    private Label errorLabel;

    public boolean isCorrectFormatCode(String code) {
        return code.length() <= 8 && code.length() >= 4;
    }

    public boolean isCorrectFormatNick(String code) {
        return code.length() <= 20 && code.length() >= 1;
    }

    public void createRoom(ActionEvent event) throws IOException {
        roomCode = roomCodeField.getText();
        nickname = nickNameField.getText();
        if (isCorrectFormatCode(roomCode) && isCorrectFormatNick(nickname)) {
            BattleShipsApp.model.setRoomCode(roomCode);
            BattleShipsApp.model.setNickname(nickname);
            FrameSender.sendFrame(new ClientFrame(ClientFrameType.CREATE_GAME,
                    new CreateGamePayload(roomCode)));
        } else {
            errorLabel.setText("Enter correct data format :)");

        }
    }

    public void joinRoom(ActionEvent event) throws IOException {
        roomCode = roomCodeField.getText();
        nickname = nickNameField.getText();
        if (isCorrectFormatCode(roomCode) && isCorrectFormatNick(nickname)) {
            BattleShipsApp.model.setRoomCode(roomCode);
            BattleShipsApp.model.setNickname(nickname);
            FrameSender.sendFrame(new ClientFrame(ClientFrameType.JOIN_GAME,
                    new JoinGamePayload(roomCode)));
        } else {
            errorLabel.setText("Enter correct data format :)");
        }
    }

}
