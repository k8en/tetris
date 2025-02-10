package org.kdepo.games.tetris.shared.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kdepo.games.tetris.shared.utils.FigureUtils;

public class FigureTests {

    @BeforeAll
    static void setup() {
        System.out.println("FigureTests - Tests started");
    }

    @BeforeEach
    void init() {

    }

    @Test
    void testClone() {
        System.out.println("FigureTests.testClone - Tests started");

        Figure figure = FigureUtils.getNextFigure(1);
        Figure figureCloned = figure.cloneFigure();

        Assertions.assertNotSame(figure, figureCloned, "Not same object error!");
        Assertions.assertNotSame(figure.getData(), figureCloned.getData(), "Not same object error!");
        Assertions.assertEquals(figure, figureCloned, "Inner data not equals error!");
        Assertions.assertArrayEquals(figure.getData(), figureCloned.getData(), "Inner data not cloned error!");

        System.out.println("FigureTests.testClone - Tests completed");
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void done() {
        System.out.println("FigureTests - Tests completed");
    }
}
