package org.kdepo.games.tetris.desktop.model;

public class Figure {

    private int figureId;
    private int orientationId;
    private int[][] data;

    public Figure(int figureId, int orientationId, int[][] data) {
        this.figureId = figureId;
        this.orientationId = orientationId;
        this.data = data;
    }

    public int getFigureId() {
        return figureId;
    }

    public void setFigureId(int figureId) {
        this.figureId = figureId;
    }

    public int getOrientationId() {
        return orientationId;
    }

    public void setOrientationId(int orientationId) {
        this.orientationId = orientationId;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }
}
