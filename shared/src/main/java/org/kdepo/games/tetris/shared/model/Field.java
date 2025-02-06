package org.kdepo.games.tetris.shared.model;

import org.kdepo.games.tetris.shared.Constants;

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
}
