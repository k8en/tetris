package org.kdepo.games.tetris.desktop.utils;

import org.kdepo.games.tetris.shared.model.Figure;
import org.kdepo.games.tetris.shared.utils.DataUtils;
import org.kdepo.games.tetris.shared.utils.FieldUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataCollectionUtils {

    private static int START_FIGURE_ID;
    private static int NEXT_FIGURE_ID;
    private static int[][] FIELD_DATA;

    /**
     * Inputs:
     * - current figure id
     * - next figure id
     * - field heights (from left to right)
     * Outputs:
     * - orientation id (for the current figure)
     */
    private static final List<String> ORIENTATION_DATA = new ArrayList<>();

    /**
     * Inputs:
     * - current figure id
     * - current figure orientation id
     * - next figure id
     * - field heights (from left to right)
     * Outputs:
     * - cell position x (from left to right)
     */
    private static final List<String> POSITION_DATA = new ArrayList<>();

    private static boolean IS_DATA_PRINTED = false;

    public static void collect(int orientationId, int currentFigureFieldCellX) {
        int[] fieldHeights = FieldUtils.getFieldHeights(FIELD_DATA);

        StringBuilder sb = new StringBuilder();

        // Prepare orientation data
        sb.append(START_FIGURE_ID).append(",");
        sb.append(NEXT_FIGURE_ID).append(",");

        for (int fieldHeight : fieldHeights) {
            sb.append(fieldHeight).append(",");
        }

        sb.append(orientationId);
        ORIENTATION_DATA.add(sb.toString());

        sb.setLength(0);

        // Prepare position data
        sb.append(START_FIGURE_ID).append(",");
        sb.append(orientationId).append(",");
        sb.append(NEXT_FIGURE_ID).append(",");

        for (int fieldHeight : fieldHeights) {
            sb.append(fieldHeight).append(",");
        }

        sb.append(currentFigureFieldCellX);
        POSITION_DATA.add(sb.toString());

        System.out.println(" -> O:[" + orientationId + "] X:[" + currentFigureFieldCellX + "]");
    }

    public static void saveStartConditions(int startFigureId,
                                           int nextFigureId,
                                           int[][] fieldData) {
        START_FIGURE_ID = startFigureId;
        NEXT_FIGURE_ID = nextFigureId;
        FIELD_DATA = DataUtils.clone(fieldData);

        int[] fieldHeights = FieldUtils.getFieldHeights(FIELD_DATA);
        System.out.print("F:[" + START_FIGURE_ID + "] N:[" + NEXT_FIGURE_ID + "] H:" + Arrays.toString(fieldHeights));
    }

    public static void printCollectedData() {
        if (IS_DATA_PRINTED) {
            return;
        }

        System.out.println("Orientation data:");
        if (ORIENTATION_DATA.isEmpty()) {
            System.out.println("- no data collected");
        } else {
            for (String data : ORIENTATION_DATA) {
                System.out.println(data);
            }
        }

        System.out.println();

        System.out.println("Position data:");
        if (POSITION_DATA.isEmpty()) {
            System.out.println("- no data collected");
        } else {
            for (String data : POSITION_DATA) {
                System.out.println(data);
            }
        }

        IS_DATA_PRINTED = true;
    }

    private static int convertFigureToUniqueId(Figure figure) {

        if (figure.getFigureId() == 0) {
            if (figure.getOrientationId() == 0) {
                return 0;
            } else if (figure.getOrientationId() == 1) {
                return 0;
            } else if (figure.getOrientationId() == 2) {
                return 0;
            } else if (figure.getOrientationId() == 3) {
                return 0;
            } else {
                throw new RuntimeException("Wrong orientation id detected: " + figure.getOrientationId());
            }

        } else if (figure.getFigureId() == 1) {
            if (figure.getOrientationId() == 0) {
                return 1;
            } else if (figure.getOrientationId() == 1) {
                return 2;
            } else if (figure.getOrientationId() == 2) {
                return 1;
            } else if (figure.getOrientationId() == 3) {
                return 2;
            } else {
                throw new RuntimeException("Wrong orientation id detected: " + figure.getOrientationId());
            }

        } else if (figure.getFigureId() == 2) {
            if (figure.getOrientationId() == 0) {
                return 3;
            } else if (figure.getOrientationId() == 1) {
                return 4;
            } else if (figure.getOrientationId() == 2) {
                return 5;
            } else if (figure.getOrientationId() == 3) {
                return 6;
            } else {
                throw new RuntimeException("Wrong orientation id detected: " + figure.getOrientationId());
            }

        } else if (figure.getFigureId() == 3) {
            if (figure.getOrientationId() == 0) {
                return 7;
            } else if (figure.getOrientationId() == 1) {
                return 8;
            } else if (figure.getOrientationId() == 2) {
                return 7;
            } else if (figure.getOrientationId() == 3) {
                return 8;
            } else {
                throw new RuntimeException("Wrong orientation id detected: " + figure.getOrientationId());
            }

        } else if (figure.getFigureId() == 4) {
            if (figure.getOrientationId() == 0) {
                return 9;
            } else if (figure.getOrientationId() == 1) {
                return 10;
            } else if (figure.getOrientationId() == 2) {
                return 9;
            } else if (figure.getOrientationId() == 3) {
                return 10;
            } else {
                throw new RuntimeException("Wrong orientation id detected: " + figure.getOrientationId());
            }

        } else if (figure.getFigureId() == 5) {
            if (figure.getOrientationId() == 0) {
                return 11;
            } else if (figure.getOrientationId() == 1) {
                return 12;
            } else if (figure.getOrientationId() == 2) {
                return 13;
            } else if (figure.getOrientationId() == 3) {
                return 14;
            } else {
                throw new RuntimeException("Wrong orientation id detected: " + figure.getOrientationId());
            }

        } else if (figure.getFigureId() == 6) {
            if (figure.getOrientationId() == 0) {
                return 15;
            } else if (figure.getOrientationId() == 1) {
                return 16;
            } else if (figure.getOrientationId() == 2) {
                return 17;
            } else if (figure.getOrientationId() == 3) {
                return 18;
            } else {
                throw new RuntimeException("Wrong orientation id detected: " + figure.getOrientationId());
            }

        } else {
            throw new RuntimeException("Wrong figure id detected: " + figure.getFigureId());
        }
    }
}
