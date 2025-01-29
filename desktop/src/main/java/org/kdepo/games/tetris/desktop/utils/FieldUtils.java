package org.kdepo.games.tetris.desktop.utils;

import org.kdepo.games.tetris.desktop.Constants;

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
}
