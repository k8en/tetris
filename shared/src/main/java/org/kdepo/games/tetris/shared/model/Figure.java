package org.kdepo.games.tetris.shared.model;

import org.kdepo.games.tetris.shared.utils.DataUtils;

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

    public Figure cloneFigure() {
        int clonedFigureId = figureId;
        int clonedOrientationId = orientationId;
        int[][] clonedData = DataUtils.clone(data);

        return new Figure(clonedFigureId, clonedOrientationId, clonedData);
    }
}
