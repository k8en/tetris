package org.kdepo.games.tetris.bot;

import org.kdepo.games.tetris.bot.model.BotAction;

import java.util.Random;

/**
 * Bot to test game base logic
 */
public class TestBot extends AbstractBot {

    private static final Random RND = new Random(System.currentTimeMillis());

    private boolean isMoveLeft;

    public TestBot() {
        super();
        isMoveLeft = true;
    }

    @Override
    public void think(int[][] fieldData, int currentFigureId, int currentFigureOrientationId, int currentFigureFieldCellX, int nextFigureId, int nextFigureOrientationId) {
        botActionList.clear();

        int rotations = RND.nextInt(4);
        for (int i = 0; i <= rotations; i++) {
            botActionList.add(BotAction.ROTATE_CLOCKWISE);
        }

        if (isMoveLeft) {
            botActionList.add(BotAction.MOVE_LEFT);
            botActionList.add(BotAction.MOVE_LEFT);
            botActionList.add(BotAction.MOVE_LEFT);
        } else {
            botActionList.add(BotAction.MOVE_RIGHT);
            botActionList.add(BotAction.MOVE_RIGHT);
            botActionList.add(BotAction.MOVE_RIGHT);
        }
        isMoveLeft = !isMoveLeft;
    }
}
