package com.example.gameoflife;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Grid extends GridPane {

    private final int rows;
    private final int columns;
    private final Rectangle[][] cells;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new Rectangle[rows][columns];
        initializeGrid();
    }

    private void initializeGrid() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Rectangle cell = new Rectangle(10, 10);

                int randomNumber = random.nextInt(100);
                cell.setFill(randomNumber < 40 ? Color.WHITE : Color.BLACK);

                GridPane.setConstraints(cell, j, i);
                this.add(cell, j, i);
                cells[i][j] = cell;
            }
        }
    }

    public void updateGrid() {
        boolean[][] nextGrid = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                if (cells[i][j].getFill() == Color.WHITE) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        nextGrid[i][j] = false;
                    } else {
                        nextGrid[i][j] = true;
                    }
                } else {
                    if (liveNeighbors == 3) {
                        nextGrid[i][j] = true;
                    } else {
                        nextGrid[i][j] = false;
                    }
                }
            }
        }

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                cells[i][j].setFill(nextGrid[i][j] ? Color.WHITE : Color.BLACK);
            }
        }
    }

    private int countLiveNeighbors(int row, int column) {
        int liveNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = (row + i + rows) % rows;
                int newColumn = (column + j + columns) % columns;
                if (cells[newRow][newColumn].getFill() == Color.WHITE) {
                    liveNeighbors++;
                }
            }
        }
        return liveNeighbors;
    }
}
