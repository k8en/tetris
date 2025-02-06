package org.kdepo.games.tetris.shared.utils;

public class DataUtils {

    public static int[][] clone(int[][] data) {
        int[][] clonedData = new int[data.length][data[0].length];
        for (int row = 0; row < data.length; row++) {
            for (int column = 0; column < data[row].length; column++) {
                clonedData[row][column] = data[row][column];
            }
        }
        return clonedData;
    }

    public static void printToConsole(int[][] data) {
        for (int row = 0; row < data.length; row++) {
            for (int column = 0; column < data[row].length; column++) {
                System.out.print(data[row][column] + ",");
            }
            System.out.println();
        }
    }
}
