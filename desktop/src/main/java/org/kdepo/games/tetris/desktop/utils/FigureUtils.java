package org.kdepo.games.tetris.desktop.utils;

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

    public static int[][] getNextFigure() {
        int figureIndex = RND.nextInt(7);
        System.out.println("Next figure selected " + figureIndex);
        int[][] figure = FIGURES.get(figureIndex);
        int[][] nextFigure = new int[figure.length][figure[0].length];
        for (int row = 0; row < figure.length; row++) {
            System.arraycopy(figure[row], 0, nextFigure[row], 0, figure[0].length);
        }
        return nextFigure;
    }
}
