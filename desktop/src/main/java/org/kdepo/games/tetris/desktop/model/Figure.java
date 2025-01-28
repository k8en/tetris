package org.kdepo.games.tetris.desktop.model;

import org.kdepo.games.tetris.desktop.Constants;

import java.awt.*;

public class Figure {

    private final int x;
    private final int y;
    private int[][] data;

    public Figure(int x, int y) {
        this.x = x;
        this.y = y;
        data = new int[4][4];
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public void render(Graphics2D g) {
        for (int row = 0; row < data.length; row++) {
            for (int column = 0; column < data[0].length; column++) {
                if (data[row][column] == 1) {
                    g.fillRect(x + column * Constants.BLOCK_SIZE, y + row * Constants.BLOCK_SIZE, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                }
            }
        }
    }
}
