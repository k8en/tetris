package org.kdepo.games.tetris.bot;

import org.junit.jupiter.api.*;
import org.kdepo.games.tetris.bot.model.BotAction;
import org.kdepo.games.tetris.shared.Constants;
import org.kdepo.games.tetris.shared.utils.DataUtils;

public class SimpleBotTests {

    @BeforeAll
    static void setup() {
        System.out.println("SimpleBotTests - Tests started");
    }

    @BeforeEach
    void init() {

    }

    @Test
    void testThink() {
        System.out.println("SimpleBotTests.testThink - Tests started");

        int[][] fieldData = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];
        int currentFigureFieldCellX = 3;

        fieldData[21][4] = 1;
        fieldData[22][3] = 1;
        fieldData[22][4] = 1;
        fieldData[23][3] = 1;

        fieldData[22][1] = 1;
        fieldData[23][1] = 1;

        DataUtils.printToConsole(fieldData);

        SimpleBot simpleBot = new SimpleBot();
        simpleBot.think(fieldData, 0, 0, currentFigureFieldCellX, 0, 0);

        BotAction botAction;
        do {
            botAction = simpleBot.getNextAction();
            System.out.println(botAction);
        } while (botAction != null);

        System.out.println("SimpleBotTests.testThink - Tests completed");
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void done() {
        System.out.println("SimpleBotTests - Tests completed");
    }
}
