package org.kdepo.games.tetris.bot;

import org.kdepo.games.tetris.bot.model.BotAction;
import org.kdepo.games.tetris.shared.utils.DataUtils;
import org.kdepo.games.tetris.shared.utils.FieldUtils;
import org.kdepo.games.tetris.shared.utils.FigureUtils;

import java.util.ArrayList;
import java.util.List;

public class SimpleBot extends AbstractBot {

    public SimpleBot() {
        super();
    }

    @Override
    public void think(int[][] fieldData, int currentFigureId, int currentFigureOrientationId, int currentFigureFieldCellX, int nextFigureId, int nextFigureOrientationId) {
        double maxFieldDensity = 0;
        int additionalFigureRotations = 0;
        int bestFigureFieldCellX = 0;

        // Check for all rotations for the current figure
        for (int rotationCurrentFigure = 0; rotationCurrentFigure < 4; rotationCurrentFigure++) {
            // Prepare current figure data according to the current figure configuration
            int[][] currentFigureData = FigureUtils.getFigureData(currentFigureId, currentFigureOrientationId + rotationCurrentFigure);

            // Resolve minimal available left cell position after the current figure rotation
            int minFieldCellXCurrentFigure = getFigureLeftmostPosition(fieldData, currentFigureData);

            // Resolve maximal available right cell position after the current figure rotation
            int maxFieldCellXCurrentFigure = getFigureRightmostPosition(fieldData, currentFigureData);

            // Check for all positions from left to right for the current figure
            for (int fieldCellXCurrentFigure = minFieldCellXCurrentFigure; fieldCellXCurrentFigure <= maxFieldCellXCurrentFigure; fieldCellXCurrentFigure++) {
                // Drop down
                int fieldCellYCurrentFigure = getFigureLowestPosition(fieldData, currentFigureData, fieldCellXCurrentFigure);

                // Merge figure data to field
                int[][] currentFieldData = DataUtils.clone(fieldData);
                FieldUtils.mergeData(currentFieldData, currentFigureData, fieldCellXCurrentFigure, fieldCellYCurrentFigure);

                // Estimate field configuration
                double currentFieldDataDensity = FieldUtils.getFieldDensity(currentFieldData);

                // Update best results
                if (currentFieldDataDensity > maxFieldDensity) {
                    maxFieldDensity = currentFieldDataDensity;
                    additionalFigureRotations = rotationCurrentFigure;
                    bestFigureFieldCellX = fieldCellXCurrentFigure;
                }

//                // Check for all rotations for the next figure
//                for (int rotationNextFigure = 0; rotationNextFigure < 4; rotationNextFigure++) {
//                    // Prepare next figure data according to the next figure configuration
//                    int[][] nextFigureData = FigureUtils.getFigureData(nextFigureId, nextFigureOrientationId + rotationNextFigure);
//
//                    // Resolve minimal available left cell position after the next figure rotation
//                    int minFieldCellXNextFigure = getFigureLeftmostPosition(currentFieldData, nextFigureData);
//
//                    // Resolve maximal available right cell position after the next figure rotation
//                    int maxFieldCellXNextFigure = getFigureRightmostPosition(currentFieldData, nextFigureData);
//
//                    // Check for all positions from left to right for the next figure
//                    for (int fieldCellXNextFigure = minFieldCellXNextFigure; fieldCellXNextFigure <= maxFieldCellXNextFigure; fieldCellXNextFigure++) {
//                        // Drop down
//                        int fieldCellYNextFigure = getFigureLowestPosition(currentFieldData, nextFigureData, fieldCellXNextFigure);
//
//                        // Merge figure data to field
//                        int[][] nextFieldData = DataUtils.clone(currentFieldData);
//                        FieldUtils.mergeData(nextFieldData, nextFigureData, fieldCellXNextFigure, fieldCellYNextFigure);
//
//                        // Estimate field configuration
//                        double currentFieldDataDensity = FieldUtils.getFieldDensity(nextFieldData);
//
//                        // Update best results
//                        if (currentFieldDataDensity > maxFieldDensity) {
//                            maxFieldDensity = currentFieldDataDensity;
//                            additionalFigureRotations = rotationCurrentFigure;
//                            bestFigureFieldCellX = fieldCellXCurrentFigure;
//                            System.out.println("maxFieldDensity=" + maxFieldDensity + ", additionalFigureRotations=" + additionalFigureRotations + ", bestFigureFieldCellX=" + bestFigureFieldCellX);
//                        }
//                    }
//                }
            }
        }

        // Convert results to bot actions
        botActionList = prepareActionList(additionalFigureRotations, currentFigureFieldCellX, bestFigureFieldCellX);
    }

    protected int getFigureLeftmostPosition(int[][] fieldData, int[][] figureData) {
        int minFieldCellX = 3;
        while (FieldUtils.canMoveLeft(fieldData, figureData, minFieldCellX, 0)) {
            minFieldCellX--;
        }
        return minFieldCellX;
    }

    protected int getFigureRightmostPosition(int[][] fieldData, int[][] figureData) {
        int maxFieldCellX = 3;
        while (FieldUtils.canMoveRight(fieldData, figureData, maxFieldCellX, 0)) {
            maxFieldCellX++;
        }
        return maxFieldCellX;
    }

    protected int getFigureLowestPosition(int[][] fieldData, int[][] figureData, int fieldCellX) {
        int fieldCellY = 0;
        while (FieldUtils.canMoveDown(fieldData, figureData, fieldCellX, fieldCellY)) {
            fieldCellY++;
        }
        return fieldCellY;
    }

    protected List<BotAction> prepareActionList(int rotations, int sourceCellX, int targetCellX) {
        List<BotAction> actionList = new ArrayList<>();

        // Rotate figure first
        for (int rotation = 0; rotation < rotations; rotation++) {
            actionList.add(BotAction.ROTATE_CLOCKWISE);
        }

        // Shift figure position then
        if (targetCellX != sourceCellX) {
            int stepsTotal = Math.abs(targetCellX - sourceCellX);
            BotAction botAction = null;
            if (targetCellX > sourceCellX) {
                botAction = BotAction.MOVE_RIGHT;
            } else {
                botAction = BotAction.MOVE_LEFT;
            }

            for (int step = 1; step <= stepsTotal; step++) {
                actionList.add(botAction);
            }
        }

        return actionList;
    }
}
