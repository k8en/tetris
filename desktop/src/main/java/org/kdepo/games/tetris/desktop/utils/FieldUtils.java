package org.kdepo.games.tetris.desktop.utils;

import org.kdepo.games.tetris.desktop.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldUtils {

    public static void printField(int[][] fieldData) {
        for (int row = 0; row < fieldData.length; row++) {
            for (int column = 0; column < fieldData[row].length; column++) {
                System.out.print(fieldData[row][column]);
            }
            System.out.println();
        }
    }

    public static boolean canMoveDown(int[][] fieldData, int[][] figureData, int figureFieldCellX, int figureFieldCellY) {
        if (figureFieldCellY + figureData.length + 1 > fieldData.length) {
            return false;
        }

        for (int row = 0; row < figureData.length; row++) {
            for (int column = 0; column < figureData[row].length; column++) {
                if (figureData[row][column] == 0) {
                    continue;
                }
                if (fieldData[figureFieldCellY + row + 1][figureFieldCellX + column] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean canMoveRight(int[][] fieldData, int[][] figureData, int figureFieldCellX, int figureFieldCellY) {
        for (int row = 0; row < figureData.length; row++) {
            for (int column = 0; column < figureData[row].length; column++) {
                if (figureData[row][column] == 0) {
                    continue;
                }
                if (figureFieldCellX + column + 1 >= fieldData[row].length) {
                    return false;
                }
                if (fieldData[figureFieldCellY + row][figureFieldCellX + column + 1] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean canMoveLeft(int[][] fieldData, int[][] figureData, int figureFieldCellX, int figureFieldCellY) {
        for (int row = 0; row < figureData.length; row++) {
            for (int column = 0; column < figureData[row].length; column++) {
                if (figureData[row][column] == 0) {
                    continue;
                }
                if (figureFieldCellX + column - 1 < 0) {
                    return false;
                }
                if (fieldData[figureFieldCellY + row][figureFieldCellX + column - 1] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void mergeData(int[][] fieldData, int[][] figureData, int figureFieldCellX, int figureFieldCellY) {
        for (int row = 0; row < figureData.length; row++) {
            for (int column = 0; column < figureData[row].length; column++) {
                if (figureData[row][column] != 0) {
                    fieldData[figureFieldCellY + row][figureFieldCellX + column] = figureData[row][column];
                }
            }
        }
    }

    public static boolean isFieldOverflow(int[][] data) {
        for (int row = 0; row < Constants.FIELD_ROWS_HIDDEN; row++) {
            for (int column = 0; column < data[row].length; column++) {
                if (data[row][column] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Integer> getCompletedLinesIndexes(int[][] data) {
        List<Integer> linesIndexes = new ArrayList<>();
        for (int row = 0; row < data.length; row++) {
            boolean isLineCompleted = true;
            for (int column = 0; column < data[row].length; column++) {
                if (data[row][column] == 0) {
                    isLineCompleted = false;
                    break;
                }
            }
            if (isLineCompleted) {
                linesIndexes.add(row);
            }
        }
        return linesIndexes;
    }

    public static void removeLines(int[][] data, List<Integer> linesIndexes) {
        for (Integer lineIndex : linesIndexes) {
            for (int row = lineIndex; row > 0; row--) {
                for (int column = 0; column < data[row].length; column++) {
                    data[row][column] = data[row - 1][column];
                }
            }
        }

        // Clear first row
        Arrays.fill(data[0], 0);
    }
}
