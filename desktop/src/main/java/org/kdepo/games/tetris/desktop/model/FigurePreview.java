package org.kdepo.games.tetris.desktop.model;

import org.kdepo.games.tetris.desktop.Constants;

import java.awt.*;

public class FigurePreview {

    private final int screenPositionX;
    private final int screenPositionY;
    private final int columns;
    private final int rows;

    public FigurePreview(int screenPositionX, int screenPositionY, int columns, int rows) {
        this.screenPositionX = screenPositionX;
        this.screenPositionY = screenPositionY;
        this.columns = columns;
        this.rows = rows;
    }

    public void render(Graphics2D g) {
        // Draw grid
        for (int row = 0; row <= rows; row++) {
            g.drawLine(screenPositionX, screenPositionY + row * Constants.BLOCK_SIZE, screenPositionX + rows * Constants.BLOCK_SIZE, screenPositionY + row * Constants.BLOCK_SIZE);
        }
        for (int column = 0; column <= columns; column++) {
            g.drawLine(screenPositionX + column * Constants.BLOCK_SIZE, screenPositionY, screenPositionX + column * Constants.BLOCK_SIZE, screenPositionY + columns * Constants.BLOCK_SIZE);
        }
    }

    public void renderFigure(Graphics2D g, Figure figure, int fieldPositionX, int fieldPositionY) {
        for (int figureRow = 0; figureRow < figure.getData().length; figureRow++) {
            for (int figureColumn = 0; figureColumn < figure.getData()[0].length; figureColumn++) {

                if (figure.getData()[figureRow][figureColumn] == 1) {
                    g.fillRect(
                            screenPositionX + (fieldPositionX + figureColumn) * Constants.BLOCK_SIZE,
                            screenPositionY + (fieldPositionY + figureRow) * Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE
                    );
                }
            }
        }
    }
}
