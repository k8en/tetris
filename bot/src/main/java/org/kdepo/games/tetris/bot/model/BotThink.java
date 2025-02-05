package org.kdepo.games.tetris.bot.model;

public interface BotThink {

    void think(int[][] fieldData,
               int currentFigureId,
               int currentFigureOrientationId,
               int currentFigureFieldCellX,
               int nextFigureId,
               int nextFigureOrientationId);

}
