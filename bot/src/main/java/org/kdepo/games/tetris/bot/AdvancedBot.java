package org.kdepo.games.tetris.bot;

import org.kdepo.games.tetris.shared.utils.DataUtils;
import org.kdepo.games.tetris.shared.utils.FieldUtils;
import org.kdepo.games.tetris.shared.utils.FigureUtils;

public class AdvancedBot extends SimpleBot {

    private int coveredBlocksWeight;

    private int pitsPeaksWeight;

    private int densityWeight;

    private int overheightWeight;

    public AdvancedBot() {
        super();

        // 59500 score in simulator
        coveredBlocksWeight = 166;
        pitsPeaksWeight = 23;
        densityWeight = 24;
        overheightWeight = 34;
    }

    @Override
    public void think(int[][] fieldData, int currentFigureId, int currentFigureOrientationId, int currentFigureFieldCellX, int nextFigureId, int nextFigureOrientationId) {
        double bestEstimation = -9999;
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

                // Check for all rotations for the next figure
                for (int rotationNextFigure = 0; rotationNextFigure < 4; rotationNextFigure++) {
                    // Prepare next figure data according to the next figure configuration
                    int[][] nextFigureData = FigureUtils.getFigureData(nextFigureId, nextFigureOrientationId + rotationNextFigure);

                    // Resolve minimal available left cell position after the next figure rotation
                    int minFieldCellXNextFigure = getFigureLeftmostPosition(currentFieldData, nextFigureData);

                    // Resolve maximal available right cell position after the next figure rotation
                    int maxFieldCellXNextFigure = getFigureRightmostPosition(currentFieldData, nextFigureData);

                    // Check for all positions from left to right for the next figure
                    for (int fieldCellXNextFigure = minFieldCellXNextFigure; fieldCellXNextFigure <= maxFieldCellXNextFigure; fieldCellXNextFigure++) {
                        // Drop down
                        int fieldCellYNextFigure = getFigureLowestPosition(currentFieldData, nextFigureData, fieldCellXNextFigure);

                        // Merge figure data to field
                        int[][] nextFieldData = DataUtils.clone(currentFieldData);
                        FieldUtils.mergeData(nextFieldData, nextFigureData, fieldCellXNextFigure, fieldCellYNextFigure);

                        // Estimate field configuration
                        double currentEstimation = estimateFieldData(nextFieldData);
//                        System.out.println("Current = " + currentEstimation + ", best=" + bestEstimation);

                        // Update best results
                        if (Double.compare(currentEstimation, bestEstimation) >= 1) {
                            bestEstimation = currentEstimation;
                            additionalFigureRotations = rotationCurrentFigure;
                            bestFigureFieldCellX = fieldCellXCurrentFigure;
//                            System.out.println("estimation=" + bestEstimation + ", additionalFigureRotations=" + additionalFigureRotations + ", bestFigureFieldCellX=" + bestFigureFieldCellX);
                        }
                    }
                }
            }
        }

//        System.out.println("Best=" + bestEstimation);

        // Convert results to bot actions
        botActionList = prepareActionList(additionalFigureRotations, currentFigureFieldCellX, bestFigureFieldCellX);
    }

    protected double estimateFieldData(int[][] fieldData) {

        double estimation = 0;

        // Estimate lines completeness
        int linesCompleted = FieldUtils.getCompletedLinesIndexes(fieldData).size();
        if (linesCompleted == 1) {
            estimation = estimation + 100;
        } else if (linesCompleted == 2) {
            estimation = estimation + 300;
        } else if (linesCompleted == 3) {
            estimation = estimation + 600;
        } else if (linesCompleted == 4) {
            estimation = estimation + 1000;
        } else if (linesCompleted == 5) {
            estimation = estimation + 1500;
        } else if (linesCompleted == 6) {
            estimation = estimation + 2100;
        } else if (linesCompleted == 7) {
            estimation = estimation + 2800;
        } else if (linesCompleted == 8) {
            estimation = estimation + 3700;
        }

        // Estimate covered blocks
        int coveredBlocks = 0;
        for (int column = 0; column < fieldData[0].length; column++) {
            boolean isCounterStarted = false;
            for (int row = 0; row < fieldData.length; row++) {
                if (fieldData[row][column] == 0) {
                    if (isCounterStarted) {
                        coveredBlocks++;
                    }
                } else {
                    if (!isCounterStarted) {
                        isCounterStarted = true;
                    }
                }
            }
        }
        estimation = estimation - coveredBlocks * coveredBlocksWeight;

        // Estimate pits/peaks (3 or more blocks)
        int[] fieldHeights = FieldUtils.getFieldHeights(fieldData);
        for (int i = 0; i < fieldHeights.length - 1; i++) {
            int difference = Math.abs(fieldHeights[i] - fieldHeights[i + 1]);
            if (difference >= 3) {
                estimation = estimation - (difference - 3) * pitsPeaksWeight;
            }
        }

        // Estimate density
        double density = FieldUtils.getFieldDensity(fieldData);
        estimation = estimation - (1 - density) * densityWeight;

        // Estimate max height (5 or more blocks)
        int height = FieldUtils.getFieldMaxHeight(fieldData);
        if (height > 4) {
            estimation = estimation - (height - 4) * overheightWeight;
        }

        return estimation;
    }

    public int getCoveredBlocksWeight() {
        return coveredBlocksWeight;
    }

    public void setCoveredBlocksWeight(int coveredBlocksWeight) {
        this.coveredBlocksWeight = coveredBlocksWeight;
    }

    public int getPitsPeaksWeight() {
        return pitsPeaksWeight;
    }

    public void setPitsPeaksWeight(int pitsPeaksWeight) {
        this.pitsPeaksWeight = pitsPeaksWeight;
    }

    public int getDensityWeight() {
        return densityWeight;
    }

    public void setDensityWeight(int densityWeight) {
        this.densityWeight = densityWeight;
    }

    public int getOverheightWeight() {
        return overheightWeight;
    }

    public void setOverheightWeight(int overheightWeight) {
        this.overheightWeight = overheightWeight;
    }

    @Override
    public String toString() {
        return "AdvancedBot{" +
                "coveredBlocksWeight=" + coveredBlocksWeight +
                ", pitsPeaksWeight=" + pitsPeaksWeight +
                ", densityWeight=" + densityWeight +
                ", overheightWeight=" + overheightWeight +
                '}';
    }
}
