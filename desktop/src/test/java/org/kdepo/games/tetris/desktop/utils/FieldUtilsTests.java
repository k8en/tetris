package org.kdepo.games.tetris.desktop.utils;

import org.junit.jupiter.api.*;
import org.kdepo.games.tetris.desktop.Constants;
import org.kdepo.games.tetris.desktop.model.Field;

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

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void done() {
        System.out.println("FieldUtilsTests - Tests completed");
    }

}
