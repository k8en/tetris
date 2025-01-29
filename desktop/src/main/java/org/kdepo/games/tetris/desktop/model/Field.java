package org.kdepo.games.tetris.desktop.model;

import org.kdepo.games.tetris.desktop.Constants;

import java.awt.*;

public class Field {

    private final int x;
    private final int y;

    private final int[][] data;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        data = new int[Constants.BLOCKS_VERTICALLY + 4][Constants.BLOCKS_HORIZONTALLY];
    }

    public void render(Graphics2D g) {
        // Draw grid
        for (int row = 0; row <= data.length - 4; row++) {
            g.drawLine(x, y + row * Constants.BLOCK_SIZE, x + Constants.BLOCKS_HORIZONTALLY * Constants.BLOCK_SIZE, y + row * Constants.BLOCK_SIZE);
        }
        for (int column = 0; column <= data[0].length; column++) {
            g.drawLine(x + column * Constants.BLOCK_SIZE, y, x + column * Constants.BLOCK_SIZE, y + Constants.BLOCKS_VERTICALLY * Constants.BLOCK_SIZE);
        }

        // Draw data
        for (int row = 0 + 4; row < data.length; row++) {
            for (int column = 0; column < data[0].length; column++) {
                if (data[row][column] == 1) {
                    g.fillRect(x + column * Constants.BLOCK_SIZE, y + row * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                }
            }
        }
    }
}
