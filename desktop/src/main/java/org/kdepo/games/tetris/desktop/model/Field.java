package org.kdepo.games.tetris.desktop.model;

import org.kdepo.games.tetris.desktop.Constants;

import java.awt.*;

public class Field {

    private int screenPositionX;
    private int screenPositionY;

    private int[][] data;

    public Field() {
        data = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];
    }

    public int getScreenPositionX() {
        return screenPositionX;
    }

    public void setScreenPositionX(int screenPositionX) {
        this.screenPositionX = screenPositionX;
    }

    public int getScreenPositionY() {
        return screenPositionY;
    }

    public void setScreenPositionY(int screenPositionY) {
        this.screenPositionY = screenPositionY;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public void render(Graphics2D g) {
        // Draw grid
        for (int row = 0; row <= data.length - Constants.FIELD_ROWS_HIDDEN; row++) {
            g.drawLine(
                    screenPositionX,
                    screenPositionY + row * Constants.BLOCK_SIZE,
                    screenPositionX + Constants.FIELD_BLOCKS_HORIZONTALLY * Constants.BLOCK_SIZE,
                    screenPositionY + row * Constants.BLOCK_SIZE
            );
        }
        for (int column = 0; column <= data[0].length; column++) {
            g.drawLine(
                    screenPositionX + column * Constants.BLOCK_SIZE,
                    screenPositionY,
                    screenPositionX + column * Constants.BLOCK_SIZE,
                    screenPositionY + Constants.FIELD_BLOCKS_VERTICALLY * Constants.BLOCK_SIZE
            );
        }

        // Draw field data (skip hidden space)
        for (int row = 0; row < data.length; row++) {
            if (row < Constants.FIELD_ROWS_HIDDEN) {
                continue;
            }
            for (int column = 0; column < data[0].length; column++) {
                if (data[row][column] == 1) {
                    g.fillRect(
                            screenPositionX + column * Constants.BLOCK_SIZE,
                            screenPositionY + (row - Constants.FIELD_ROWS_HIDDEN) * Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE);
                }
            }
        }
    }

    public void renderFigure(Graphics2D g, Figure figure, int fieldPositionX, int fieldPositionY) {
        for (int figureRow = 0; figureRow < figure.getData().length; figureRow++) {
            for (int figureColumn = 0; figureColumn < figure.getData()[0].length; figureColumn++) {

                // If row is outside the hidden space
                if (fieldPositionY + figureRow >= Constants.FIELD_ROWS_HIDDEN) {
                    if (figure.getData()[figureRow][figureColumn] == 1) {
                        g.fillRect(
                                screenPositionX + (fieldPositionX + figureColumn) * Constants.BLOCK_SIZE,
                                screenPositionY + (fieldPositionY + figureRow - Constants.FIELD_ROWS_HIDDEN) * Constants.BLOCK_SIZE,
                                Constants.BLOCK_SIZE,
                                Constants.BLOCK_SIZE
                        );
                    }
                }
            }
        }
    }
}
