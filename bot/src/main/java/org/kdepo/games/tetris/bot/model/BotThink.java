package org.kdepo.games.tetris.bot.model;

public interface BotThink {

    /**
     * Prepare sequence of actions based on game state
     *
     * @param fieldData                  current field data
     * @param currentFigureId            figure id currently controlled by player or bot
     * @param currentFigureOrientationId current figure orientation id
     * @param currentFigureFieldCellX    horizontal position of current figure
     * @param nextFigureId               figure id to be played next
     * @param nextFigureOrientationId    next figure orientation id
     */
    void think(int[][] fieldData,
               int currentFigureId,
               int currentFigureOrientationId,
               int currentFigureFieldCellX,
               int nextFigureId,
               int nextFigureOrientationId);

}
