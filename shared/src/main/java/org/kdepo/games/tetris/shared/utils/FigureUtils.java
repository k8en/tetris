package org.kdepo.games.tetris.shared.utils;

import org.kdepo.games.tetris.shared.model.Figure;

import java.util.List;
import java.util.Random;

public class FigureUtils {

    private static final Random RND = new Random(System.currentTimeMillis());

    /**
     * figure 0, orientations 0,1,2,3
     */
    public static final int[][] FIGURE_00 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 1, orientations 0,2
     */
    public static final int[][] FIGURE_01 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1}
    };

    /**
     * figure 1, orientations 1,3
     */
    public static final int[][] FIGURE_01_1 = {
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 2, orientation 0
     */
    public static final int[][] FIGURE_02 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 1, 0}
    };

    /**
     * figure 2, orientation 1
     */
    public static final int[][] FIGURE_02_1 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 2, orientation 2
     */
    public static final int[][] FIGURE_02_2 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0}
    };

    /**
     * figure 2, orientation 3
     */
    public static final int[][] FIGURE_02_3 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0}
    };

    /**
     * figure 3, orientations 0,2
     */
    public static final int[][] FIGURE_03 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0}
    };

    /**
     * figure 3, orientations 1,3
     */
    public static final int[][] FIGURE_03_1 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 4, orientations 0,2
     */
    public static final int[][] FIGURE_04 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 4, orientations 1,3
     */
    public static final int[][] FIGURE_04_1 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 1, 0}
    };

    /**
     * figure 5, orientation 0
     */
    public static final int[][] FIGURE_05 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 1, 0}
    };

    /**
     * figure 5, orientation 1
     */
    public static final int[][] FIGURE_05_1 = {
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 5, orientation 2
     */
    public static final int[][] FIGURE_05_2 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 0, 1, 0}
    };

    /**
     * figure 5, orientation 3
     */
    public static final int[][] FIGURE_05_3 = {
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 6, orientation 0
     */
    public static final int[][] FIGURE_06 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 1, 0},
            {1, 1, 1, 0}
    };

    /**
     * figure 6, orientation 1
     */
    public static final int[][] FIGURE_06_1 = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0}
    };

    /**
     * figure 6, orientation 2
     */
    public static final int[][] FIGURE_06_2 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 0},
            {1, 0, 0, 0}
    };

    /**
     * figure 6, orientation 3
     */
    public static final int[][] FIGURE_06_3 = {
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0}
    };

    private static final List<int[][]> FIGURES = List.of(
            FIGURE_00,
            FIGURE_01,
            FIGURE_02,
            FIGURE_03,
            FIGURE_04,
            FIGURE_05,
            FIGURE_06
    );

    public static Figure getNextFigure() {
        int figureId = RND.nextInt(7);
        return FigureUtils.getNextFigure(figureId);
    }

    public static Figure getNextFigure(int figureId) {
        if (figureId < 0 || figureId >= 7) {
            throw new RuntimeException("Figure ID is out of range: " + figureId);
        }
        int orientationId = 0;
        int[][] figureData = DataUtils.clone(FIGURES.get(figureId));

        return new Figure(figureId, orientationId, figureData);
    }

    public static Figure getFigureRotated(Figure currentFigure) {
        int rotatedOrientation = currentFigure.getOrientationId() + 1;
        if (rotatedOrientation > 3) {
            rotatedOrientation = 0;
        }

        int[][] rotatedData = null;
        if (currentFigure.getFigureId() == 0) {
            rotatedData = FIGURE_00;

        } else if (currentFigure.getFigureId() == 1) {
            if (rotatedOrientation == 0 || rotatedOrientation == 2) {
                rotatedData = FIGURE_01;
            } else if (rotatedOrientation == 1 || rotatedOrientation == 3) {
                rotatedData = FIGURE_01_1;
            }

        } else if (currentFigure.getFigureId() == 2) {
            if (rotatedOrientation == 0) {
                rotatedData = FIGURE_02;
            } else if (rotatedOrientation == 1) {
                rotatedData = FIGURE_02_1;
            } else if (rotatedOrientation == 2) {
                rotatedData = FIGURE_02_2;
            } else if (rotatedOrientation == 3) {
                rotatedData = FIGURE_02_3;
            }

        } else if (currentFigure.getFigureId() == 3) {
            if (rotatedOrientation == 0 || rotatedOrientation == 2) {
                rotatedData = FIGURE_03;
            } else if (rotatedOrientation == 1 || rotatedOrientation == 3) {
                rotatedData = FIGURE_03_1;
            }

        } else if (currentFigure.getFigureId() == 4) {
            if (rotatedOrientation == 0 || rotatedOrientation == 2) {
                rotatedData = FIGURE_04;
            } else if (rotatedOrientation == 1 || rotatedOrientation == 3) {
                rotatedData = FIGURE_04_1;
            }

        } else if (currentFigure.getFigureId() == 5) {
            if (rotatedOrientation == 0) {
                rotatedData = FIGURE_05;
            } else if (rotatedOrientation == 1) {
                rotatedData = FIGURE_05_1;
            } else if (rotatedOrientation == 2) {
                rotatedData = FIGURE_05_2;
            } else if (rotatedOrientation == 3) {
                rotatedData = FIGURE_05_3;
            }

        } else if (currentFigure.getFigureId() == 6) {
            if (rotatedOrientation == 0) {
                rotatedData = FIGURE_06;
            } else if (rotatedOrientation == 1) {
                rotatedData = FIGURE_06_1;
            } else if (rotatedOrientation == 2) {
                rotatedData = FIGURE_06_2;
            } else if (rotatedOrientation == 3) {
                rotatedData = FIGURE_06_3;
            }
        }

        if (rotatedData == null) {
            throw new RuntimeException("Figure data not found for figure id = " + currentFigure.getFigureId() + " and orientation id = " + rotatedOrientation);
        }

        return new Figure(currentFigure.getFigureId(), rotatedOrientation, rotatedData);
    }

    public static int[][] getFigureData(int figureId, int orientationId) {

        int[][] figureData = null;

        switch (figureId) {
            case (0): {
                figureData = DataUtils.clone(FIGURE_00);
                break;
            }
            case (1): {
                if (orientationId == 0 || orientationId == 2) {
                    figureData = DataUtils.clone(FIGURE_01);
                } else if (orientationId == 1 || orientationId == 3) {
                    figureData = DataUtils.clone(FIGURE_01_1);
                } else {
                    throw new RuntimeException("Unknown orientation id " + orientationId + " for figure id " + figureId);
                }
                break;
            }
            case (2): {
                if (orientationId == 0) {
                    figureData = DataUtils.clone(FIGURE_02);
                } else if (orientationId == 1) {
                    figureData = DataUtils.clone(FIGURE_02_1);
                } else if (orientationId == 2) {
                    figureData = DataUtils.clone(FIGURE_02_2);
                } else if (orientationId == 3) {
                    figureData = DataUtils.clone(FIGURE_02_3);
                } else {
                    throw new RuntimeException("Unknown orientation id " + orientationId + " for figure id " + figureId);
                }
                break;
            }
            case (3): {
                if (orientationId == 0 || orientationId == 2) {
                    figureData = DataUtils.clone(FIGURE_03);
                } else if (orientationId == 1 || orientationId == 3) {
                    figureData = DataUtils.clone(FIGURE_03_1);
                } else {
                    throw new RuntimeException("Unknown orientation id " + orientationId + " for figure id " + figureId);
                }
                break;
            }
            case (4): {
                if (orientationId == 0 || orientationId == 2) {
                    figureData = DataUtils.clone(FIGURE_04);
                } else if (orientationId == 1 || orientationId == 3) {
                    figureData = DataUtils.clone(FIGURE_04_1);
                } else {
                    throw new RuntimeException("Unknown orientation id " + orientationId + " for figure id " + figureId);
                }
                break;
            }
            case (5): {
                if (orientationId == 0) {
                    figureData = DataUtils.clone(FIGURE_05);
                } else if (orientationId == 1) {
                    figureData = DataUtils.clone(FIGURE_05_1);
                } else if (orientationId == 2) {
                    figureData = DataUtils.clone(FIGURE_05_2);
                } else if (orientationId == 3) {
                    figureData = DataUtils.clone(FIGURE_05_3);
                } else {
                    throw new RuntimeException("Unknown orientation id " + orientationId + " for figure id " + figureId);
                }
                break;
            }
            case (6): {
                if (orientationId == 0) {
                    figureData = DataUtils.clone(FIGURE_06);
                } else if (orientationId == 1) {
                    figureData = DataUtils.clone(FIGURE_06_1);
                } else if (orientationId == 2) {
                    figureData = DataUtils.clone(FIGURE_06_2);
                } else if (orientationId == 3) {
                    figureData = DataUtils.clone(FIGURE_06_3);
                } else {
                    throw new RuntimeException("Unknown orientation id " + orientationId + " for figure id " + figureId);
                }
                break;
            }
            default: {
                throw new RuntimeException("Unknown figure found: " + figureId);
            }
        }

        return figureData;
    }
}
