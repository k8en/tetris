package org.kdepo.games.tetris.shared.model;

import org.kdepo.games.tetris.shared.utils.DataUtils;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return figureId == figure.figureId
                && orientationId == figure.orientationId
                && Objects.deepEquals(data, figure.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figureId, orientationId, Arrays.deepHashCode(data));
    }

    @Override
    public String toString() {
        return "Figure{" +
                "figureId=" + figureId +
                ", orientationId=" + orientationId +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
