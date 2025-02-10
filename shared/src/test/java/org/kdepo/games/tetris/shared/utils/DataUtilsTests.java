package org.kdepo.games.tetris.shared.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kdepo.games.tetris.shared.Constants;

import java.util.Arrays;

public class DataUtilsTests {

    @BeforeAll
    static void setup() {
        System.out.println("DataUtilsTests - Tests started");
    }

    @BeforeEach
    void init() {

    }

    @Test
    void testClone() {
        System.out.println("DataUtilsTests.testClone - Tests started");

        int[][] fieldData1 = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];
        int[][] fieldData2 = DataUtils.clone(fieldData1);
        Assertions.assertArrayEquals(fieldData1, fieldData2, "Data cloning error!");

        fieldData1[21][4] = 1;
        fieldData1[22][3] = 1;
        fieldData1[22][4] = 1;
        fieldData1[23][3] = 1;
        Assertions.assertFalse(Arrays.deepEquals(fieldData1, fieldData2), "Data cloning error!");

        fieldData2[21][4] = 1;
        fieldData2[22][3] = 1;
        fieldData2[22][4] = 1;
        fieldData2[23][3] = 1;
        Assertions.assertArrayEquals(fieldData1, fieldData2, "Data cloning error!");

        System.out.println("DataUtilsTests.testClone - Tests completed");
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void done() {
        System.out.println("DataUtilsTests - Tests completed");
    }
}
