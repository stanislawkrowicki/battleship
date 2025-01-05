package com.put.battleship.client;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridController {
    public void fillYourGrid(GridPane yourGrid) {
        for (javafx.scene.Node node : yourGrid.getChildren()) {
            int y = GridPane.getRowIndex(node);
            int x = GridPane.getColumnIndex(node);
            if (BattleShipsApp.model.isShipInYourBoard(y, x)) {
                Rectangle rectangle = (Rectangle) node;
                rectangle.setFill(Color.ORANGE);
            }
        }
    }

}
