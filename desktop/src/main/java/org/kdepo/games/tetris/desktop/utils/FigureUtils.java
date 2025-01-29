package org.kdepo.games.tetris.desktop.utils;

import org.kdepo.games.tetris.desktop.model.Figure;

import java.util.List;
import java.util.Random;

public class FigureUtils {

    private static final Random RND = new Random(System.currentTimeMillis());

    private static final int[][] FIGURE_01 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 1, 0, 0}
    };

    private static final int[][] FIGURE_02 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1}
    };

    private static final int[][] FIGURE_03 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 1, 0}
    };

    private static final int[][] FIGURE_04 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0}
    };

    private static final int[][] FIGURE_05 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0}
    };

    private static final int[][] FIGURE_06 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 1, 0}
    };

    private static final int[][] FIGURE_07 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 1, 0},
            {1, 1, 1, 0}
    };

    private static final List<int[][]> FIGURES = List.of(
            FIGURE_01,
            FIGURE_02,
            FIGURE_03,
            FIGURE_04,
            FIGURE_05,
            FIGURE_06,
            FIGURE_07
    );

    public static Figure getNextFigure() {
        int figureId = RND.nextInt(7);
        int orientationId = 0;

        System.out.println("Next figure selected " + figureId);
        int[][] figureDataToCopy = FIGURES.get(figureId);
        int[][] figureData = new int[figureDataToCopy.length][figureDataToCopy[0].length];
        for (int row = 0; row < figureDataToCopy.length; row++) {
            System.arraycopy(figureDataToCopy[row], 0, figureData[row], 0, figureDataToCopy[0].length);
        }

        return new Figure(figureId, orientationId, figureData);
    }

    public static Figure getNextFigure(int figureId) {
        if (figureId < 0 || figureId >= 7) {
            throw new RuntimeException("Figure ID is out of range: " + figureId);
        }
        int orientationId = 0;

        System.out.println("Next figure selected " + figureId);
        int[][] figureDataToCopy = FIGURES.get(figureId);
        int[][] figureData = new int[figureDataToCopy.length][figureDataToCopy[0].length];
        for (int row = 0; row < figureDataToCopy.length; row++) {
            System.arraycopy(figureDataToCopy[row], 0, figureData[row], 0, figureDataToCopy[0].length);
        }

        return new Figure(figureId, orientationId, figureData);
    }
}
