package com.put.battleship.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.put.battleship.shared.Ship;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class SetShipController extends GridController {

    private static final int RECTANGLE_WIDTH = 50;
    private static final int RECTANGLE_HEIGHT = 50;
    private final boolean canSetShips = false;
    SceneController sceneController = new SceneController();
    GridPane yourBoard;
    int[] shipSizes;
    int currentShip;
    boolean vertical;
    Color baseColor = Color.YELLOW;
    @FXML
    private HBox hBoxVirtual;

    @FXML
    public void initialize() {
        yourBoard = createGrid();
        fillYourGrid(yourBoard);
        hBoxVirtual.getChildren().addAll(yourBoard);
        shipSizes = BattleShipsApp.model.shipsConfig();
        vertical = false;
    }

    private GridPane createGrid() {
        GridPane gridPane = new GridPane();
        for (int row = 0; row < BattleShipsApp.model.getSizey(); row++) {
            for (int col = 0; col < BattleShipsApp.model.getSizex(); col++) {
                Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                rectangle.setFill(BattleShipsApp.model.getYourBackgroundColor());
                rectangle.setOnMouseExited(event -> {
                    clearCurrentShip(rectangle);
                });
                rectangle.setOnMouseEntered(event -> {
                    paintCurrentShip(rectangle);
                });
                rectangle.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        updateGrid();
                    }
                    if (event.isAltDown()) {
                        if (BattleShipsApp.model.yourShipCount() > 0) {
                            clearCurrentShip(rectangle);
                            Ship removed = BattleShipsApp.model.removeLastShip();
                            clearShip(removed);
                            paintCurrentShip(rectangle);
                        }
                    } else {
                        if (BattleShipsApp.model.yourShipCount() == shipSizes.length - 1) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                objectMapper.writeValue(System.out, BattleShipsApp.model.getYourBoard().getShips());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return;
                        }
                        if (BattleShipsApp.model.yourShipCount() == shipSizes.length)
                            return;
                        if (event.isSecondaryButtonDown() || event.getButton().name().equals("SECONDARY")) {
                            clearCurrentShip(rectangle);
                            vertical = !vertical;
                            paintCurrentShip(rectangle);
                        } else {
                            Ship ship = currentShip(rectangle);
                            if (ship != null && BattleShipsApp.model.isValidPosition(ship))
                                BattleShipsApp.model.addShip(ship);
                        }
                    }
                });
                gridPane.add(rectangle, col, row);
            }
        }

        return gridPane;
    }

    private void updateGrid() {
        for (Node rec : yourBoard.getChildren()) {
            Rectangle r = (Rectangle) rec;
            int x = GridPane.getColumnIndex(r);
            int y = GridPane.getRowIndex(r);
            r.setFill(BattleShipsApp.model.isShipInYourBoard(y, x) ? Color.ORANGE : color(Math.random()));
        }
    }

    private void paintCurrentShip(Rectangle rectangle) {
        Ship ship = currentShip(rectangle);
        if (ship != null && BattleShipsApp.model.isValidPosition(ship))
            paintShip(ship);
    }

    private void clearCurrentShip(Rectangle rectangle) {
        Ship ship = currentShip(rectangle);
        if (ship != null && BattleShipsApp.model.isValidPosition(ship))
            clearShip(ship);
    }

    private Ship currentShip(Rectangle rectangle) {
        if (BattleShipsApp.model.yourShipCount() == shipSizes.length)
            return null;
        int row = GridPane.getRowIndex(rectangle);
        int col = GridPane.getColumnIndex(rectangle);
        return new Ship(shipSizes[BattleShipsApp.model.yourShipCount()],
                col, row, vertical);
    }

    private void paintShip(Ship ship) {
        fillShip(ship, Color.ORANGE);
    }

    private void clearShip(Ship removed) {
        fillShip(removed, color(Math.random()));
    }

    private void fillShip(Ship ship, Color color) {
        ObservableList<Node> nodes = yourBoard.getChildren();
        int x = ship.getHeadX();
        int y = ship.getHeadY();
        for (int i = 0; i < ship.getSize(); i++) {
            ((Rectangle) nodes.get(y * BattleShipsApp.model.getSizex() + x)).setFill(color);
            if (ship.isVertical())
                y++;
            else x++;
        }
    }

    private Color color(double brightness) {
        return Color.rgb(255, 255, 200);
        //return baseColor.darker().interpolate(baseColor.brighter(), brightness);
    }


    public void switchToBattleScreen(ActionEvent event) throws IOException {
        sceneController.switchScene(event, "battle_screen.fxml");

    }
}
