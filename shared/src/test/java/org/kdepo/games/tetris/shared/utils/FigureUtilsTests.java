package org.kdepo.games.tetris.shared.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kdepo.games.tetris.shared.model.Figure;

public class FigureUtilsTests {

    @BeforeAll
    static void setup() {
        System.out.println("FigureUtilsTests - Tests started");
    }

    @BeforeEach
    void init() {

    }

    @Test
    void testGetNextFigure() {
        System.out.println("FigureUtilsTests.testGetNextFigure - Tests started");

        Figure figure0 = FigureUtils.getNextFigure(0);
        Assertions.assertEquals(0, figure0.getFigureId(), "Figure id error!");
        Assertions.assertEquals(0, figure0.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_00, figure0.getData(), "Figure data error!");

        Figure figure1 = FigureUtils.getNextFigure(1);
        Assertions.assertEquals(1, figure1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(0, figure1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_01, figure1.getData(), "Figure data error!");

        Figure figure2 = FigureUtils.getNextFigure(2);
        Assertions.assertEquals(2, figure2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(0, figure2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_02, figure2.getData(), "Figure data error!");

        Figure figure3 = FigureUtils.getNextFigure(3);
        Assertions.assertEquals(3, figure3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(0, figure3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_03, figure3.getData(), "Figure data error!");

        Figure figure4 = FigureUtils.getNextFigure(4);
        Assertions.assertEquals(4, figure4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(0, figure4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_04, figure4.getData(), "Figure data error!");

        Figure figure5 = FigureUtils.getNextFigure(5);
        Assertions.assertEquals(5, figure5.getFigureId(), "Figure id error!");
        Assertions.assertEquals(0, figure5.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_05, figure5.getData(), "Figure data error!");

        Figure figure6 = FigureUtils.getNextFigure(6);
        Assertions.assertEquals(6, figure6.getFigureId(), "Figure id error!");
        Assertions.assertEquals(0, figure6.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_06, figure6.getData(), "Figure data error!");

        System.out.println("FigureUtilsTests.testGetNextFigure - Tests completed");
    }

    @Test
    void testGetFigureRotated() {
        System.out.println("FigureUtilsTests.testGetFigureRotated - Tests started");

        Figure figure0_0 = FigureUtils.getNextFigure(0);

        Figure figure0_1 = FigureUtils.getFigureRotated(figure0_0);
        Assertions.assertEquals(0, figure0_1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(1, figure0_1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_00, figure0_1.getData(), "Figure data error!");

        Figure figure0_2 = FigureUtils.getFigureRotated(figure0_1);
        Assertions.assertEquals(0, figure0_2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(2, figure0_2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_00, figure0_2.getData(), "Figure data error!");

        Figure figure0_3 = FigureUtils.getFigureRotated(figure0_2);
        Assertions.assertEquals(0, figure0_3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(3, figure0_3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_00, figure0_3.getData(), "Figure data error!");

        Figure figure0_4 = FigureUtils.getFigureRotated(figure0_3);
        Assertions.assertEquals(figure0_0.getFigureId(), figure0_4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(figure0_0.getOrientationId(), figure0_4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(figure0_0.getData(), figure0_4.getData(), "Figure data error!");

        Figure figure1_0 = FigureUtils.getNextFigure(1);

        Figure figure1_1 = FigureUtils.getFigureRotated(figure1_0);
        Assertions.assertEquals(1, figure1_1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(1, figure1_1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_01_1, figure1_1.getData(), "Figure data error!");

        Figure figure1_2 = FigureUtils.getFigureRotated(figure1_1);
        Assertions.assertEquals(1, figure1_2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(2, figure1_2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_01, figure1_2.getData(), "Figure data error!");

        Figure figure1_3 = FigureUtils.getFigureRotated(figure1_2);
        Assertions.assertEquals(1, figure1_3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(3, figure1_3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_01_1, figure1_3.getData(), "Figure data error!");

        Figure figure1_4 = FigureUtils.getFigureRotated(figure1_3);
        Assertions.assertEquals(figure1_0.getFigureId(), figure1_4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(figure1_0.getOrientationId(), figure1_4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(figure1_0.getData(), figure1_4.getData(), "Figure data error!");

        Figure figure2_0 = FigureUtils.getNextFigure(2);

        Figure figure2_1 = FigureUtils.getFigureRotated(figure2_0);
        Assertions.assertEquals(2, figure2_1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(1, figure2_1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_02_1, figure2_1.getData(), "Figure data error!");

        Figure figure2_2 = FigureUtils.getFigureRotated(figure2_1);
        Assertions.assertEquals(2, figure2_2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(2, figure2_2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_02_2, figure2_2.getData(), "Figure data error!");

        Figure figure2_3 = FigureUtils.getFigureRotated(figure2_2);
        Assertions.assertEquals(2, figure2_3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(3, figure2_3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_02_3, figure2_3.getData(), "Figure data error!");

        Figure figure2_4 = FigureUtils.getFigureRotated(figure2_3);
        Assertions.assertEquals(figure2_0.getFigureId(), figure2_4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(figure2_0.getOrientationId(), figure2_4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(figure2_0.getData(), figure2_4.getData(), "Figure data error!");

        Figure figure3_0 = FigureUtils.getNextFigure(3);

        Figure figure3_1 = FigureUtils.getFigureRotated(figure3_0);
        Assertions.assertEquals(3, figure3_1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(1, figure3_1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_03_1, figure3_1.getData(), "Figure data error!");

        Figure figure3_2 = FigureUtils.getFigureRotated(figure3_1);
        Assertions.assertEquals(3, figure3_2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(2, figure3_2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_03, figure3_2.getData(), "Figure data error!");

        Figure figure3_3 = FigureUtils.getFigureRotated(figure3_2);
        Assertions.assertEquals(3, figure3_3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(3, figure3_3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_03_1, figure3_3.getData(), "Figure data error!");

        Figure figure3_4 = FigureUtils.getFigureRotated(figure3_3);
        Assertions.assertEquals(figure3_0.getFigureId(), figure3_4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(figure3_0.getOrientationId(), figure3_4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(figure3_0.getData(), figure3_4.getData(), "Figure data error!");

        Figure figure4_0 = FigureUtils.getNextFigure(4);

        Figure figure4_1 = FigureUtils.getFigureRotated(figure4_0);
        Assertions.assertEquals(4, figure4_1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(1, figure4_1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_04_1, figure4_1.getData(), "Figure data error!");

        Figure figure4_2 = FigureUtils.getFigureRotated(figure4_1);
        Assertions.assertEquals(4, figure4_2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(2, figure4_2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_04, figure4_2.getData(), "Figure data error!");

        Figure figure4_3 = FigureUtils.getFigureRotated(figure4_2);
        Assertions.assertEquals(4, figure4_3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(3, figure4_3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_04_1, figure4_3.getData(), "Figure data error!");

        Figure figure4_4 = FigureUtils.getFigureRotated(figure4_3);
        Assertions.assertEquals(figure4_0.getFigureId(), figure4_4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(figure4_0.getOrientationId(), figure4_4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(figure4_0.getData(), figure4_4.getData(), "Figure data error!");

        Figure figure5_0 = FigureUtils.getNextFigure(5);

        Figure figure5_1 = FigureUtils.getFigureRotated(figure5_0);
        Assertions.assertEquals(5, figure5_1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(1, figure5_1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_05_1, figure5_1.getData(), "Figure data error!");

        Figure figure5_2 = FigureUtils.getFigureRotated(figure5_1);
        Assertions.assertEquals(5, figure5_2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(2, figure5_2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_05_2, figure5_2.getData(), "Figure data error!");

        Figure figure5_3 = FigureUtils.getFigureRotated(figure5_2);
        Assertions.assertEquals(5, figure5_3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(3, figure5_3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_05_3, figure5_3.getData(), "Figure data error!");

        Figure figure5_4 = FigureUtils.getFigureRotated(figure5_3);
        Assertions.assertEquals(figure5_0.getFigureId(), figure5_4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(figure5_0.getOrientationId(), figure5_4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(figure5_0.getData(), figure5_4.getData(), "Figure data error!");

        Figure figure6_0 = FigureUtils.getNextFigure(6);

        Figure figure6_1 = FigureUtils.getFigureRotated(figure6_0);
        Assertions.assertEquals(6, figure6_1.getFigureId(), "Figure id error!");
        Assertions.assertEquals(1, figure6_1.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_06_1, figure6_1.getData(), "Figure data error!");

        Figure figure6_2 = FigureUtils.getFigureRotated(figure6_1);
        Assertions.assertEquals(6, figure6_2.getFigureId(), "Figure id error!");
        Assertions.assertEquals(2, figure6_2.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_06_2, figure6_2.getData(), "Figure data error!");

        Figure figure6_3 = FigureUtils.getFigureRotated(figure6_2);
        Assertions.assertEquals(6, figure6_3.getFigureId(), "Figure id error!");
        Assertions.assertEquals(3, figure6_3.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(FigureUtils.FIGURE_06_3, figure6_3.getData(), "Figure data error!");

        Figure figure6_4 = FigureUtils.getFigureRotated(figure6_3);
        Assertions.assertEquals(figure6_0.getFigureId(), figure6_4.getFigureId(), "Figure id error!");
        Assertions.assertEquals(figure6_0.getOrientationId(), figure6_4.getOrientationId(), "Figure orientation id error!");
        Assertions.assertArrayEquals(figure6_0.getData(), figure6_4.getData(), "Figure data error!");

        System.out.println("FigureUtilsTests.testGetFigureRotated - Tests completed");
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void done() {
        System.out.println("FigureUtilsTests - Tests completed");
    }
}
