package com.put.battleship.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;


import java.io.IOException;

public class BattleController {

    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    private static final int SPACING_BETWEEN_GRIDS = 100;
    private final Map<String, Object> carrier = new HashMap<>();
    private final boolean canSetShips = false;
    int[][] yourBoardMatrix = new int[10][10];
    int[][] enemyBoardMatrix = new int[10][10];
    SceneController sceneController = new SceneController();
    private boolean canAttack = true;
    @FXML
    private HBox hBoxVirtual;

    @FXML
    public void initialize() {
        GridPane yourBoard = createGrid(Color.YELLOW, false);
        GridPane enemyBoard = createGrid(Color.LIGHTGREEN, true);
        createCarrier();
        translateToMatrix();
        fillYourGrid(yourBoard);
        hBoxVirtual.getChildren().addAll(yourBoard, enemyBoard);
    }

    private GridPane createGrid(Color color, boolean isEnemy) {
        GridPane gridPane = new GridPane();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                final int currentRow = row;
                final int currentCol = col;
                rectangle.setFill(color.darker().interpolate(color.brighter(), Math.random()));
                if (isEnemy) {
                    rectangle.setOnMouseClicked(event -> {
                        if (canAttack) {
                            handleAttack(currentCol, currentRow, rectangle);
                        }
                    });
                }
                gridPane.add(rectangle, col, row);
            }
        }

        return gridPane;
    }

    public void createCarrier() {
        carrier.put("type", "carrier");
        List<int[]> positions = new ArrayList<>();
        positions.add(new int[]{0, 4});
        positions.add(new int[]{1, 4});
        positions.add(new int[]{2, 4});
        positions.add(new int[]{3, 4});
        carrier.put("positions", positions);
    }

    public void translateToMatrix() {
        List<int[]> retrievedPositions = (List<int[]>) carrier.get("positions");
        for (int[] position : retrievedPositions) {
            yourBoardMatrix[position[0]][position[1]] = 1;
            enemyBoardMatrix[position[0]][position[1]] = 1;
        }
    }

    public void fillYourGrid(GridPane yourGrid) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (yourBoardMatrix[i][j] == 1) {
                    for (javafx.scene.Node node : yourGrid.getChildren()) {
                        if (GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) {
                            Rectangle rectangle = (Rectangle) node;
                            rectangle.setFill(Color.ORANGE);
                        }
                    }
                }
            }
        }
    }

    private void handleAttack(int col, int row, Rectangle rectangle) {
        if (enemyBoardMatrix[GridPane.getRowIndex(rectangle)][GridPane.getColumnIndex(rectangle)] == 1)
            rectangle.setFill(Color.RED);
        else {
            rectangle.setFill(Color.BLUE);
            canAttack = false;
            enemyAttack();
        }
    }

    private void enemyAttack() {
        //tutaj czekamy na szczylanie przeciwnika
        canAttack = true;
    }

    public void switchToEndScreen(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "end_screen.fxml");

    }
}
