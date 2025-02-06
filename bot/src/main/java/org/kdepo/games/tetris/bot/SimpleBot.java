package org.kdepo.games.tetris.bot;

import org.kdepo.games.tetris.bot.model.BotAction;
import org.kdepo.games.tetris.shared.utils.DataUtils;
import org.kdepo.games.tetris.shared.utils.FieldUtils;
import org.kdepo.games.tetris.shared.utils.FigureUtils;

public class SimpleBot extends AbstractBot {

    public SimpleBot() {
        super();
    }

    @Override
    public void think(int[][] fieldData, int currentFigureId, int currentFigureOrientationId, int currentFigureFieldCellX, int nextFigureId, int nextFigureOrientationId) {
        double maxFieldDensity = 0;
        int additionalFigureRotations = 0;
        int bestFigureFieldCellX = 0;

        // Check for all orientations
        for (int rotation = 0; rotation < 4; rotation++) {
            int[][] currentFigureData = FigureUtils.getFigureData(currentFigureId, currentFigureOrientationId + rotation);

            double currentFieldDataDensity = 0;

            // Resolve minimal available left cell position after figure rotation
            int minFieldCellX = 3;
            while (FieldUtils.canMoveLeft(fieldData, currentFigureData, minFieldCellX, 0)) {
                minFieldCellX--;
            }

            // Resolve maximal available right cell position after figure rotation
            int maxFieldCellX = 3;
            while (FieldUtils.canMoveRight(fieldData, currentFigureData, maxFieldCellX, 0)) {
                maxFieldCellX++;
            }

            for (int fieldCellX = minFieldCellX; fieldCellX <= maxFieldCellX; fieldCellX++) {
                // Drop down
                int fieldCellY = 0;
                while (FieldUtils.canMoveDown(fieldData, currentFigureData, fieldCellX, fieldCellY)) {
                    fieldCellY++;
                }

                // Merge figure data to field
                int[][] currentFieldData = DataUtils.clone(fieldData);
                FieldUtils.mergeData(currentFieldData, currentFigureData, fieldCellX, fieldCellY);

                // Calculate density
                currentFieldDataDensity = FieldUtils.getFieldDensity(currentFieldData);

                if (currentFieldDataDensity > maxFieldDensity) {
                    maxFieldDensity = currentFieldDataDensity;
                    additionalFigureRotations = rotation;
                    bestFigureFieldCellX = fieldCellX;
                }
            }
        }

        // Convert results to bot actions
        botActionList.clear();

        // Rotate figure first
        for (int rotation = 0; rotation < additionalFigureRotations; rotation++) {
            botActionList.add(BotAction.ROTATE_CLOCKWISE);
        }

        // Shift figure position then
        if (bestFigureFieldCellX != currentFigureFieldCellX) {
            int stepsTotal = Math.abs(bestFigureFieldCellX - currentFigureFieldCellX);
            BotAction botAction = null;
            if (bestFigureFieldCellX > currentFigureFieldCellX) {
                botAction = BotAction.MOVE_RIGHT;
            } else {
                botAction = BotAction.MOVE_LEFT;
            }

            for (int step = 1; step <= stepsTotal; step++) {
                botActionList.add(botAction);
            }
        }
    }
}
