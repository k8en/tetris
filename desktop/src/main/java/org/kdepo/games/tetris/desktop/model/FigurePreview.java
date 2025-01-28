package org.kdepo.games.tetris.desktop.model;

import org.kdepo.games.tetris.desktop.Constants;

import java.awt.*;

public class FigurePreview {

    private final int x;
    private final int y;
    private final int columns;
    private final int rows;

    public FigurePreview(int x, int y, int columns, int rows) {
        this.x = x;
        this.y = y;
        this.columns = columns;
        this.rows = rows;
    }

    public void render(Graphics2D g) {
        // Draw grid
        for (int row = 0; row <= rows; row++) {
            g.drawLine(x, y + row * Constants.BLOCK_SIZE, x + rows * Constants.BLOCK_SIZE, y + row * Constants.BLOCK_SIZE);
        }
        for (int column = 0; column <= columns; column++) {
            g.drawLine(x + column * Constants.BLOCK_SIZE, y, x + column * Constants.BLOCK_SIZE, y + columns * Constants.BLOCK_SIZE);
        }
    }
}
