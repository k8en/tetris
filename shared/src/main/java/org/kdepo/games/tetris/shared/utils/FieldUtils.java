package org.kdepo.games.tetris.shared.utils;

import org.kdepo.games.tetris.shared.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldUtils {

    /**
     * Checks if figure can be placed one step down
     *
     * @param fieldData        field data
     * @param figureData       figure data
     * @param figureFieldCellX top left position of figure data on a field data
     * @param figureFieldCellY top left position of figure data on a field data
     * @return true if figure can be placed one step down, otherwise false
     */
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

    /**
     * Checks if figure can be placed one step right
     *
     * @param fieldData        field data
     * @param figureData       figure data
     * @param figureFieldCellX top left position of figure data on a field data
     * @param figureFieldCellY top left position of figure data on a field data
     * @return true if figure can be placed one step right, otherwise false
     */
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

    /**
     * Checks if figure can be placed one step left
     *
     * @param fieldData        field data
     * @param figureData       figure data
     * @param figureFieldCellX top left position of figure data on a field data
     * @param figureFieldCellY top left position of figure data on a field data
     * @return true if figure can be placed one step left, otherwise false
     */
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

    /**
     * Checks if figure data can be placed on a field data with selected position
     *
     * @param fieldData        field data
     * @param figureData       figure data
     * @param figureFieldCellX top left position of figure data on a field data
     * @param figureFieldCellY top left position of figure data on a field data
     * @return true if figure data can be placed on a field data with selected position, otherwise false
     */
    public static boolean canPlaceFigure(int[][] fieldData, int[][] figureData, int figureFieldCellX, int figureFieldCellY) {
        for (int row = 0; row < figureData.length; row++) {
            for (int column = 0; column < figureData[row].length; column++) {
                if (figureData[row][column] == 0) {
                    continue;
                }
                if (figureFieldCellX + column >= fieldData[row].length) {
                    return false;
                }
                if (figureFieldCellY + row >= fieldData.length) {
                    return false;
                }
                if (fieldData[figureFieldCellY + row][figureFieldCellX + column] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Copy figure data on a field data with selected position
     *
     * @param fieldData        field data
     * @param figureData       figure data
     * @param figureFieldCellX top left position of figure data on a field data
     * @param figureFieldCellY top left position of figure data on a field data
     */
    public static void mergeData(int[][] fieldData, int[][] figureData, int figureFieldCellX, int figureFieldCellY) {
        for (int row = 0; row < figureData.length; row++) {
            for (int column = 0; column < figureData[row].length; column++) {
                if (figureData[row][column] != 0) {
                    fieldData[figureFieldCellY + row][figureFieldCellX + column] = figureData[row][column];
                }
            }
        }
    }

    /**
     * Checks if any data present on a hidden part of the field
     *
     * @param fieldData field data
     * @return true if any data present on a hidden part of the field, otherwise false
     */
    public static boolean isFieldOverflowing(int[][] fieldData) {
        for (int row = 0; row < Constants.FIELD_ROWS_HIDDEN; row++) {
            for (int column = 0; column < fieldData[row].length; column++) {
                if (fieldData[row][column] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates field density
     *
     * @param fieldData field data
     * @return field density
     */
    public static double getFieldDensity(int[][] fieldData) {
        double notEmptyCells = 0;

        for (int row = 0; row < fieldData.length; row++) {
            for (int column = 0; column < fieldData[row].length; column++) {
                if (fieldData[row][column] != 0) {
                    notEmptyCells++;
                }
            }
        }

        int maxHeight = getFieldMaxHeight(fieldData);

        return notEmptyCells / (maxHeight * Constants.FIELD_BLOCKS_HORIZONTALLY);
    }

    /**
     * Returns field max height
     *
     * @param fieldData field data
     * @return field max height
     */
    public static int getFieldMaxHeight(int[][] fieldData) {
        int maxHeight = 0;
        boolean isBlockFound = false;
        for (int row = 0; row < fieldData.length; row++) {
            for (int column = 0; column < fieldData[row].length; column++) {
                if (fieldData[row][column] != 0) {
                    maxHeight = fieldData.length - row;
                    isBlockFound = true;
                    break;
                }
            }
            if (isBlockFound) {
                break;
            }
        }
        return maxHeight;
    }

    /**
     * Returns all columns heights
     *
     * @param fieldData field data
     * @return all columns heights
     */
    public static int[] getFieldHeights(int[][] fieldData) {
        int[] fieldHeights = new int[fieldData[0].length];
        for (int column = 0; column < fieldData[0].length; column++) {
            int height = 0;
            for (int row = fieldData.length - 1; row >= 0; row--) {
                if (fieldData[row][column] != 0) {
                    height = fieldData.length - row;
                }
            }
            fieldHeights[column] = height;
        }
        return fieldHeights;
    }

    /**
     * Returns field rows indexes where data line is completed
     *
     * @param fieldData field data
     * @return field rows indexes where data line is completed
     */
    public static List<Integer> getCompletedRowsIndexes(int[][] fieldData) {
        List<Integer> rowsIndexes = new ArrayList<>();
        for (int row = 0; row < fieldData.length; row++) {
            boolean isRowCompleted = true;
            for (int column = 0; column < fieldData[row].length; column++) {
                if (fieldData[row][column] == 0) {
                    isRowCompleted = false;
                    break;
                }
            }
            if (isRowCompleted) {
                rowsIndexes.add(row);
            }
        }
        return rowsIndexes;
    }

    /**
     * Removes field rows with selected indexes
     *
     * @param fieldData   field data
     * @param rowsIndexes rows indexes to delete
     */
    public static void removeRows(int[][] fieldData, List<Integer> rowsIndexes) {
        for (Integer rowIndex : rowsIndexes) {
            for (int row = rowIndex; row > 0; row--) {
                for (int column = 0; column < fieldData[row].length; column++) {
                    fieldData[row][column] = fieldData[row - 1][column];
                }
            }
        }

        // Clear first row
        Arrays.fill(fieldData[0], 0);
    }

    /**
     * Returns figure leftmost available position
     *
     * @param fieldData  field data
     * @param figureData figure data
     * @return figure leftmost available position
     */
    public static int getFigureLeftmostPosition(int[][] fieldData, int[][] figureData) {
        int minFieldCellX = 3;
        while (FieldUtils.canMoveLeft(fieldData, figureData, minFieldCellX, 0)) {
            minFieldCellX--;
        }
        return minFieldCellX;
    }

    /**
     * Returns figure rightmost available position
     *
     * @param fieldData  field data
     * @param figureData figure data
     * @return figure rightmost available position
     */
    public static int getFigureRightmostPosition(int[][] fieldData, int[][] figureData) {
        int maxFieldCellX = 3;
        while (FieldUtils.canMoveRight(fieldData, figureData, maxFieldCellX, 0)) {
            maxFieldCellX++;
        }
        return maxFieldCellX;
    }

    /**
     * Returns figure lowest available position
     *
     * @param fieldData  field data
     * @param figureData figure data
     * @param fieldCellX horizontal position on a field
     * @return figure lowest available position
     */
    public static int getFigureLowestPosition(int[][] fieldData, int[][] figureData, int fieldCellX) {
        int fieldCellY = 0;
        while (FieldUtils.canMoveDown(fieldData, figureData, fieldCellX, fieldCellY)) {
            fieldCellY++;
        }
        return fieldCellY;
    }
}
