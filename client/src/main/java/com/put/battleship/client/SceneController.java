package com.put.battleship.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Parent root;
    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    private static final int SPACING_BETWEEN_GRIDS = 100;


    @FXML
    private HBox hBoxVirtual;

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();

    }
    public void switchToLoadingScreen(ActionEvent event) throws IOException{
        switchScene(event, "loading_screen.fxml");
    }

    public void switchToBattleScreen(ActionEvent event) throws IOException{


        switchScene(event, "battle_screen.fxml");


        GridPane gridPane1 = createGrid(Color.LIGHTBLUE);
        GridPane gridPane2 = createGrid(Color.LIGHTGREEN);

        hBoxVirtual.getChildren().addAll(gridPane1, gridPane2);

    }

    public void switchToEndScreen(ActionEvent event) throws IOException{
        switchScene(event, "end_screen.fxml");

    }
    public void switchToTitleScreen(ActionEvent event) throws IOException{
        switchScene(event, "title_screen.fxml");

    }

    private GridPane createGrid(Color color){
        GridPane gridPane = new GridPane();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);

                rectangle.setFill(color.darker().interpolate(color.brighter(), Math.random()));

                gridPane.add(rectangle, col, row);
                System.out.println("Added rectangle at (" + col + ", " + row + ")");
            }
        }
        return gridPane;
    }
}
