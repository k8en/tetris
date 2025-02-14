package org.kdepo.games.tetris.bot.model;

public enum BotAction {
    /**
     * Move figure to one block left
     */
    MOVE_LEFT,

    /**
     * Move figure to one block right
     */
    MOVE_RIGHT,

    /**
     * Rotate figure to 90 degrees clockwise
     */
    ROTATE_CLOCKWISE,

    /**
     * Rotate figure to 90 degrees counterclockwise
     */
    ROTATE_COUNTER_CLOCKWISE,

    /**
     * Move figure to on block down
     */
    DROP
}
