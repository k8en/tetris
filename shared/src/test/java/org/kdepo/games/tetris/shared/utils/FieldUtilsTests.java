package org.kdepo.games.tetris.shared.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kdepo.games.tetris.shared.Constants;
import org.kdepo.games.tetris.shared.model.Field;

public class FieldUtilsTests {

    @BeforeAll
    static void setup() {
        System.out.println("FieldUtilsTests - Tests started");
    }

    @BeforeEach
    void init() {

    }

    @Test
    void testMergeData() {
        System.out.println("FieldUtilsTests.testMergeData - Tests started");

        int[][] fieldData = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];
        int[][] figureData = FigureUtils.getNextFigure(4).getData();
        int currentFigureFieldCellX = 3;
        int currentFigureFieldCellY = 20;

        FieldUtils.mergeData(fieldData, figureData, currentFigureFieldCellX, currentFigureFieldCellY);

        int[][] fieldDataAfterMerge = new Field().getData();
        fieldDataAfterMerge[21][4] = 1;
        fieldDataAfterMerge[22][3] = 1;
        fieldDataAfterMerge[22][4] = 1;
        fieldDataAfterMerge[23][3] = 1;

        Assertions.assertArrayEquals(fieldDataAfterMerge, fieldData, "Figure data not merged!");

        System.out.println("FieldUtilsTests.testMergeData - Tests completed");
    }

    @Test
    void testCanMoveDown() {
        System.out.println("FieldUtilsTests.testCanMoveDown - Tests started");

        int[][] fieldData = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];
        int[][] figureData = FigureUtils.getNextFigure(4).getData();

        fieldData[21][4] = 1;
        fieldData[22][3] = 1;
        fieldData[22][4] = 1;
        fieldData[23][3] = 1;

        int currentFigureFieldCellX = 3;

        int currentFigureFieldCellY = 20 - 4;
        Assertions.assertTrue(FieldUtils.canMoveDown(fieldData, figureData, currentFigureFieldCellX, currentFigureFieldCellY), "Can move down failed!");

        currentFigureFieldCellY = 20 - 3;
        Assertions.assertTrue(FieldUtils.canMoveDown(fieldData, figureData, currentFigureFieldCellX, currentFigureFieldCellY), "Can move down failed!");

        currentFigureFieldCellY = 20 - 2;
        Assertions.assertFalse(FieldUtils.canMoveDown(fieldData, figureData, currentFigureFieldCellX, currentFigureFieldCellY), "Can move down failed!");

        System.out.println("FieldUtilsTests.testCanMoveDown - Tests completed");
    }

    @Test
    void testGetFieldDensity() {
        System.out.println("FieldUtilsTests.testGetFieldDensity - Tests started");

        int[][] fieldData = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];
        int[][] figureData = FigureUtils.getNextFigure(0).getData();
        int currentFigureFieldCellX = 3;
        int currentFigureFieldCellY = 20;

        FieldUtils.mergeData(fieldData, figureData, currentFigureFieldCellX, currentFigureFieldCellY);

        double density = FieldUtils.getFieldDensity(fieldData);

        Assertions.assertEquals(0.2d, density, "Field density error!");

        System.out.println("FieldUtilsTests.testGetFieldDensity - Tests completed");
    }

    @Test
    void testGetFieldDensity2() {
        System.out.println("FieldUtilsTests.testGetFieldDensity2 - Tests started");

        int[][] fieldData = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];

        fieldData[21][4] = 1;
        fieldData[22][3] = 1;
        fieldData[22][4] = 1;
        fieldData[23][3] = 1;

        fieldData[22][1] = 1;
        fieldData[23][1] = 1;

        DataUtils.printToConsole(fieldData);

        double density = FieldUtils.getFieldDensity(fieldData);
        System.out.println(density);

        fieldData[20][0] = 1;
        fieldData[20][1] = 1;
        fieldData[21][0] = 1;
        fieldData[21][1] = 1;

        DataUtils.printToConsole(fieldData);

        density = FieldUtils.getFieldDensity(fieldData);
        System.out.println(density);

        Assertions.assertEquals(0.2d, density, "Field density error!");

        System.out.println("FieldUtilsTests.testGetFieldDensity2 - Tests completed");
    }

    @Test
    void testGetFieldHeights() {
        System.out.println("DataUtilsTests.testGetFieldDensity - Tests started");

        int[][] fieldData = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];

        int[] fieldHeights = FieldUtils.getFieldHeights(fieldData);
        int[] expectedHeights = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Assertions.assertArrayEquals(expectedHeights, fieldHeights, "Field heights error!");

        fieldData[21][4] = 1;
        fieldData[22][3] = 1;
        fieldData[22][4] = 1;
        fieldData[23][3] = 1;
        fieldHeights = FieldUtils.getFieldHeights(fieldData);
        int[] expectedHeights2 = {0, 0, 0, 2, 3, 0, 0, 0, 0, 0};
        Assertions.assertArrayEquals(expectedHeights2, fieldHeights, "Field heights error!");

        System.out.println("DataUtilsTests.testGetFieldDensity - Tests completed");
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void done() {
        System.out.println("FieldUtilsTests - Tests completed");
    }
}
