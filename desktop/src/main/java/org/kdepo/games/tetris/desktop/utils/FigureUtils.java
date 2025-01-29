package org.kdepo.games.tetris.desktop.utils;

import org.kdepo.games.tetris.desktop.model.Figure;

import java.util.List;
import java.util.Random;

public class FigureUtils {

    private static final Random RND = new Random(System.currentTimeMillis());

    /**
     * figure 0, orientations 0,1,2,3
     */
    private static final int[][] FIGURE_01 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 1, orientations 0,2
     */
    private static final int[][] FIGURE_02 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1}
    };

    /**
     * figure 1, orientations 1,3
     */
    private static final int[][] FIGURE_02_1 = {
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 3, orientation 0
     */
    private static final int[][] FIGURE_03 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 1, 0}
    };

    /**
     * figure 3, orientation 1
     */
    private static final int[][] FIGURE_03_1 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 3, orientation 2
     */
    private static final int[][] FIGURE_03_2 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0}
    };

    /**
     * figure 3, orientation 3
     */
    private static final int[][] FIGURE_03_3 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0}
    };

    /**
     * figure 4, orientations 0,2
     */
    private static final int[][] FIGURE_04 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0}
    };

    /**
     * figure 4, orientations 1,3
     */
    private static final int[][] FIGURE_04_1 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 5, orientations 0,2
     */
    private static final int[][] FIGURE_05 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 5, orientations 1,3
     */
    private static final int[][] FIGURE_05_1 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 1, 0}
    };

    /**
     * figure 6, orientation 0
     */
    private static final int[][] FIGURE_06 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 1, 0}
    };

    /**
     * figure 6, orientation 1
     */
    private static final int[][] FIGURE_06_1 = {
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 6, orientation 2
     */
    private static final int[][] FIGURE_06_2 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 0, 1, 0}
    };

    /**
     * figure 6, orientation 3
     */
    private static final int[][] FIGURE_06_3 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 7, orientation 0
     */
    private static final int[][] FIGURE_07 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 1, 0},
            {1, 1, 1, 0}
    };

    /**
     * figure 7, orientation 1
     */
    private static final int[][] FIGURE_07_1 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 7, orientation 2
     */
    private static final int[][] FIGURE_07_2 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 7, orientation 3
     */
    private static final int[][] FIGURE_07_3 = {
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0}
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

    public static Figure getRotatedFigure(Figure currentFigure) {
        int rotatedOrientation = currentFigure.getOrientationId() + 1;
        if (rotatedOrientation > 3) {
            rotatedOrientation = 0;
        }

        int[][] rotatedData = null;
        if (currentFigure.getFigureId() == 0) {
            rotatedData = FIGURE_01;

        } else if (currentFigure.getFigureId() == 1) {
            if (rotatedOrientation == 0 || rotatedOrientation == 2) {
                rotatedData = FIGURE_02;
            } else if (rotatedOrientation == 1 || rotatedOrientation == 3) {
                rotatedData = FIGURE_02_1;
            }

        } else if (currentFigure.getFigureId() == 2) {
            if (rotatedOrientation == 0) {
                rotatedData = FIGURE_03;
            } else if (rotatedOrientation == 1) {
                rotatedData = FIGURE_03_1;
            } else if (rotatedOrientation == 2) {
                rotatedData = FIGURE_03_2;
            } else if (rotatedOrientation == 3) {
                rotatedData = FIGURE_03_3;
            }

        } else if (currentFigure.getFigureId() == 3) {
            if (rotatedOrientation == 0 || rotatedOrientation == 2) {
                rotatedData = FIGURE_04;
            } else if (rotatedOrientation == 1 || rotatedOrientation == 3) {
                rotatedData = FIGURE_04_1;
            }

        } else if (currentFigure.getFigureId() == 4) {
            if (rotatedOrientation == 0 || rotatedOrientation == 2) {
                rotatedData = FIGURE_05;
            } else if (rotatedOrientation == 1 || rotatedOrientation == 3) {
                rotatedData = FIGURE_05_1;
            }

        } else if (currentFigure.getFigureId() == 5) {
            if (rotatedOrientation == 0) {
                rotatedData = FIGURE_06;
            } else if (rotatedOrientation == 1) {
                rotatedData = FIGURE_06_1;
            } else if (rotatedOrientation == 2) {
                rotatedData = FIGURE_06_2;
            } else if (rotatedOrientation == 3) {
                rotatedData = FIGURE_06_3;
            }

        } else if (currentFigure.getFigureId() == 6) {
            if (rotatedOrientation == 0) {
                rotatedData = FIGURE_07;
            } else if (rotatedOrientation == 1) {
                rotatedData = FIGURE_07_1;
            } else if (rotatedOrientation == 2) {
                rotatedData = FIGURE_07_2;
            } else if (rotatedOrientation == 3) {
                rotatedData = FIGURE_07_3;
            }
        }

        if (rotatedData == null) {
            throw new RuntimeException("Figure data not found for figure id = " + currentFigure.getFigureId() + " and orientation id = " + rotatedOrientation);
        }

        return new Figure(currentFigure.getFigureId(), rotatedOrientation, rotatedData);
    }
}
