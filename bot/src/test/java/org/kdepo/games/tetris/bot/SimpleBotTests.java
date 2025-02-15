package org.kdepo.games.tetris.bot;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        fieldData[23][0] = 1;
        fieldData[23][1] = 1;
        fieldData[23][2] = 1;
        fieldData[22][2] = 1;

        fieldData[23][3] = 1;
        fieldData[22][3] = 1;
        fieldData[23][4] = 1;
        fieldData[23][5] = 1;

        fieldData[23][7] = 1;
        fieldData[23][8] = 1;
        fieldData[23][9] = 1;
        fieldData[22][9] = 1;

        SimpleBot simpleBot = new SimpleBot();
        simpleBot.think(fieldData, 2, 0, currentFigureFieldCellX, 0, 0);

        Assertions.assertEquals(BotAction.ROTATE_CLOCKWISE, simpleBot.getNextAction());
        Assertions.assertEquals(BotAction.ROTATE_CLOCKWISE, simpleBot.getNextAction());
        Assertions.assertEquals(BotAction.MOVE_RIGHT, simpleBot.getNextAction());
        Assertions.assertEquals(BotAction.MOVE_RIGHT, simpleBot.getNextAction());
        Assertions.assertNull(simpleBot.getNextAction());

        BotAction botAction;
        do {
            botAction = simpleBot.getNextAction();
            System.out.println(botAction);
        } while (botAction != null);

        System.out.println("SimpleBotTests.testThink - Tests completed");
    }

    @Test
    void testThink2() {
        System.out.println("SimpleBotTests.testThink - Tests started");

        int[][] fieldData = new int[Constants.FIELD_BLOCKS_VERTICALLY + Constants.FIELD_ROWS_HIDDEN][Constants.FIELD_BLOCKS_HORIZONTALLY];
        int currentFigureFieldCellX = 3;

        fieldData[23][0] = 1;
        fieldData[23][1] = 1;
        fieldData[23][2] = 1;
        fieldData[23][3] = 1;
        fieldData[23][4] = 1;
        fieldData[23][5] = 1;
        fieldData[23][6] = 1;
        fieldData[23][7] = 1;
        fieldData[23][8] = 1;

        fieldData[22][0] = 1;
        fieldData[22][1] = 1;
        fieldData[22][2] = 1;
        fieldData[22][3] = 1;
        fieldData[22][4] = 1;
        fieldData[22][5] = 1;
        fieldData[22][6] = 1;
        fieldData[22][7] = 1;
        fieldData[22][8] = 1;

        fieldData[21][0] = 1;
        fieldData[21][1] = 1;
        fieldData[21][2] = 1;
        fieldData[21][3] = 1;
        fieldData[21][4] = 1;
        fieldData[21][5] = 1;
        fieldData[21][6] = 1;
        fieldData[21][7] = 1;
        fieldData[21][8] = 1;

        DataUtils.printToConsole(fieldData);

        SimpleBot simpleBot = new SimpleBot();
        simpleBot.think(fieldData, 1, 0, currentFigureFieldCellX, 0, 0);

//        Assertions.assertEquals(BotAction.ROTATE_CLOCKWISE, simpleBot.getNextAction());
//        Assertions.assertEquals(BotAction.ROTATE_CLOCKWISE, simpleBot.getNextAction());
//        Assertions.assertEquals(BotAction.MOVE_RIGHT, simpleBot.getNextAction());
//        Assertions.assertEquals(BotAction.MOVE_RIGHT, simpleBot.getNextAction());
//        Assertions.assertNull(simpleBot.getNextAction());

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
