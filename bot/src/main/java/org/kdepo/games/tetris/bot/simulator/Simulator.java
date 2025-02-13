package org.kdepo.games.tetris.bot.simulator;

import org.kdepo.games.tetris.bot.AbstractBot;
import org.kdepo.games.tetris.bot.AdvancedBot;
import org.kdepo.games.tetris.bot.model.BotAction;
import org.kdepo.games.tetris.shared.model.Field;
import org.kdepo.games.tetris.shared.model.Figure;
import org.kdepo.games.tetris.shared.utils.FieldUtils;
import org.kdepo.games.tetris.shared.utils.FigureUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

    private static final Random RND = new Random(System.currentTimeMillis());

    private static final int[] PREDEFINED_FIGURES_SEQUENCE = {
            0, 1, 0, 0, 1, 3, 5, 4, 3, 3, 0, 2, 2, 0, 4, 0, 3, 6, 6, 0, 1, 0, 4, 4, 4,
            2, 4, 0, 6, 4, 4, 0, 4, 4, 1, 4, 2, 3, 6, 4, 5, 3, 3, 2, 6, 0, 0, 4, 2, 4,
            0, 5, 1, 4, 0, 3, 5, 2, 5, 0, 2, 3, 3, 2, 3, 2, 3, 0, 5, 1, 2, 5, 0, 3, 1,
            0, 1, 4, 6, 0, 6, 1, 1, 4, 4, 4, 0, 3, 6, 2, 0, 4, 4, 1, 3, 5, 0, 0, 4, 4,
            1, 2, 1, 4, 3, 5, 3, 5, 2, 2, 1, 6, 4, 0, 3, 2, 2, 2, 0, 6, 4, 4, 6, 4, 3,
            4, 4, 1, 4, 1, 5, 1, 1, 6, 2, 1, 5, 4, 6, 1, 5, 6, 0, 4, 3, 4, 6, 5, 6, 5,
            4, 1, 5, 5, 4, 3, 5, 4, 1, 0, 0, 4, 0, 5, 6, 6, 0, 0, 6, 1, 0, 1, 6, 0, 2,
            3, 2, 0, 4, 2, 2, 2, 5, 5, 2, 2, 3, 4, 0, 0, 0, 3, 3, 0, 0, 6, 3, 4, 6, 1,
            3, 4, 4, 3, 1, 3, 0, 0, 0, 3, 0, 5, 0, 4, 0, 4, 1, 6, 6, 0, 4, 3, 2, 1, 4,
            6, 2, 2, 3, 5, 2, 3, 6, 6, 1, 0, 5, 6, 4, 3, 4, 6, 2, 6, 6, 1, 6, 2, 4, 3,
            1, 4, 0, 4, 5, 0, 2, 1, 1, 2, 0, 0, 0, 4, 0, 2, 2, 6, 2, 6, 2, 1, 3, 0, 0,
            4, 5, 2, 5, 3, 3, 1, 3, 0, 3, 1, 6, 6, 5, 6, 6, 1, 5, 4, 2, 5, 2, 1, 0, 6,
            2, 5, 6, 0, 6, 1, 6, 4, 6, 2, 6, 2, 5, 2, 0, 6, 1, 4, 2, 5, 0, 3, 4, 2, 3,
            1, 5, 3, 5, 1, 2, 6, 0, 2, 0, 6, 3, 3, 5, 1, 3, 2, 4, 6, 0, 0, 0, 3, 5, 1,
            4, 4, 4, 6, 0, 3, 3, 5, 0, 0, 4, 6, 2, 3, 0, 4, 1, 4, 6, 5, 1, 5, 1, 0, 0,
            4, 4, 4, 1, 6, 0, 1, 0, 5, 5, 5, 2, 2, 6, 5, 2, 0, 1, 4, 3, 6, 6, 6, 5, 2,
            1, 5, 6, 1, 6, 4, 3, 3, 0, 2, 0, 1, 0, 6, 3, 3, 2, 2, 0, 3, 3, 5, 2, 2, 1,
            5, 6, 0, 5, 2, 2, 2, 2, 3, 6, 6, 4, 1, 4, 6, 4, 3, 2, 3, 4, 2, 3, 0, 4, 5,
            2, 2, 0, 1, 2, 1, 5, 2, 4, 2, 5, 5, 2, 5, 5, 4, 3, 3, 4, 6, 1, 3, 2, 6, 2,
            3, 2, 6, 1, 3, 5, 5, 1, 1, 4, 1, 2, 1, 3, 4, 3, 4, 3, 0, 3, 0, 2, 2, 5, 6,
            1, 1, 3, 2, 0, 5, 5, 6, 3, 6, 2, 5, 3, 4, 3, 5, 3, 1, 1, 3, 3, 3, 4, 2, 5,
            6, 0, 5, 5, 6, 5, 3, 0, 0, 4, 6, 4, 1, 1, 6, 3, 1, 3, 5, 6, 5, 1, 6, 1, 2,
            6, 2, 1, 5, 1, 5, 0, 3, 0, 4, 4, 3, 3, 2, 4, 6, 2, 6, 4, 4, 5, 3, 6, 2, 6,
            2, 5, 6, 5, 3, 5, 4, 2, 0, 6, 5, 4, 6, 1, 2, 5, 5, 6, 6, 2, 4, 0, 6, 2, 4,
            3, 3, 2, 2, 1, 3, 5, 1, 6, 4, 1, 5, 1, 1, 3, 1, 3, 0, 4, 6, 0, 0, 6, 3, 2,
            0, 6, 1, 2, 0, 0, 0, 0, 0, 4, 1, 3, 1, 2, 3, 1, 1, 6, 5, 1, 5, 6, 6, 2, 6,
            0, 6, 6, 3, 3, 6, 1, 1, 6, 0, 0, 0, 1, 0, 3, 2, 1, 6, 1, 6, 2, 6, 5, 1, 1,
            3, 3, 2, 3, 3, 5, 2, 1, 5, 1, 5, 3, 5, 3, 3, 1, 5, 6, 2, 0, 3, 1, 4, 3, 6,
            6, 3, 3, 0, 4, 3, 4, 0, 0, 0, 3, 0, 1, 4, 5, 2, 4, 5, 0, 4, 1, 2, 4, 5, 2,
            4, 1, 3, 3, 0, 2, 4, 6, 1, 6, 2, 2, 4, 0, 1, 1, 3, 0, 5, 4, 4, 3, 6, 3, 3,
            2, 1, 4, 5, 1, 6, 3, 0, 3, 3, 4, 6, 6, 6, 5, 4, 2, 3, 4, 5, 6, 1, 2, 4, 4,
            6, 0, 3, 0, 6, 6, 2, 1, 2, 5, 0, 3, 1, 6, 3, 5, 3, 5, 3, 6, 1, 4, 4, 2, 6,
            6, 0, 4, 6, 4, 3, 1, 6, 3, 1, 5, 5, 0, 3, 4, 0, 1, 1, 2, 5, 4, 5, 0, 3, 3,
            6, 2, 2, 0, 4, 1, 4, 6, 5, 0, 1, 2, 0, 2, 4, 2, 1, 2, 4, 1, 6, 4, 4, 5, 1,
            5, 4, 3, 6, 5, 3, 6, 0, 5, 1, 3, 5, 3, 2, 3, 0, 2, 3, 4, 6, 5, 2, 0, 2, 4,
            1, 4, 1, 0, 0, 3, 1, 4, 2, 6, 2, 2, 0, 2, 2, 4, 3, 6, 1, 3, 3, 6, 3, 6, 2,
            2, 0, 6, 4, 6, 0, 4, 3, 2, 3, 1, 3, 6, 4, 2, 1, 4, 4, 2, 5, 3, 5, 4, 5, 2,
            2, 3, 4, 5, 1, 3, 0, 5, 4, 3, 6, 3, 6, 5, 4, 6, 5, 5, 0, 4, 4, 5, 3, 0, 5,
            4, 2, 5, 5, 1, 5, 0, 6, 0, 5, 0, 3, 6, 1, 3, 0, 4, 4, 2, 4, 4, 0, 4, 0, 0,
            3, 6, 0, 2, 1, 5, 5, 1, 6, 6, 6, 3, 6, 4, 0, 5, 0, 0, 6, 0, 6, 3, 2, 4, 0
    };

    private int figureSelectIndex;

    public Simulator() {
        figureSelectIndex = 0;
    }

    public int getNextFigureId() {
        int selectedFigureId = PREDEFINED_FIGURES_SEQUENCE[figureSelectIndex];
        figureSelectIndex++;

        if (figureSelectIndex >= PREDEFINED_FIGURES_SEQUENCE.length) {
            figureSelectIndex = 0;
        }

        return selectedFigureId;
    }

    public SimulationResult simulate(AbstractBot bot) {
        SimulationResult result = new SimulationResult();

        // Prepare field
        Field field = new Field();

        // Prepare figures
        int nextFigureId = getNextFigureId();
        int currentFigureId = getNextFigureId();

        Figure currentFigure = FigureUtils.getNextFigure(currentFigureId);

        for (int iteration = 0; iteration < 1000; iteration++) {
            // Set initial position
            int fieldCellX = 3;
            int fieldCellY = 0;

            // Let bot to analyze and prepare actions
            bot.think(field.getData(), currentFigure.getFigureId(), 0, fieldCellX, nextFigureId, 0);

            // Orient figure in space according to bot decision
            BotAction botAction;
            do {
                botAction = bot.getNextAction();
                if (botAction != null) {
                    if (BotAction.MOVE_LEFT.equals(botAction)) {
                        if (FieldUtils.canMoveLeft(field.getData(), currentFigure.getData(), fieldCellX, fieldCellY)) {
                            fieldCellX = fieldCellX - 1;
                        }

                    } else if (BotAction.MOVE_RIGHT.equals(botAction)) {
                        if (FieldUtils.canMoveRight(field.getData(), currentFigure.getData(), fieldCellX, fieldCellY)) {
                            fieldCellX = fieldCellX + 1;
                        }

                    } else if (BotAction.ROTATE_CLOCKWISE.equals(botAction)) {
                        Figure rotatedFigure = FigureUtils.getFigureRotated(currentFigure);
                        if (FieldUtils.canPlaceFigure(field.getData(), rotatedFigure.getData(), fieldCellX, fieldCellY)) {
                            currentFigure.setOrientationId(rotatedFigure.getOrientationId());
                            currentFigure.setData(rotatedFigure.getData());
                        }
                    }
                }
            } while (botAction != null);

            // Drop down figure
            while (FieldUtils.canMoveDown(field.getData(), currentFigure.getData(), fieldCellX, fieldCellY)) {
                fieldCellY = fieldCellY + 1;
            }

            // Merge figure data to field data
            FieldUtils.mergeData(field.getData(), currentFigure.getData(), fieldCellX, fieldCellY);

            // Check for the completed lines
            List<Integer> completedLinesIndexes = FieldUtils.getCompletedLinesIndexes(field.getData());
            if (!completedLinesIndexes.isEmpty()) {
                if (completedLinesIndexes.size() == 1) {
                    result.addLinesCount1();
                    result.addScore(100);

                } else if (completedLinesIndexes.size() == 2) {
                    result.addLinesCount2();
                    result.addScore(300);

                } else if (completedLinesIndexes.size() == 3) {
                    result.addLinesCount3();
                    result.addScore(600);

                } else if (completedLinesIndexes.size() == 4) {
                    result.addLinesCount4();
                    result.addScore(1000);

                }

                FieldUtils.removeLines(field.getData(), completedLinesIndexes);
            }

            // Check for end game
            if (FieldUtils.isFieldOverflow(field.getData())) {
                result.setGameOver();
                break;

            } else {
                currentFigure = FigureUtils.getNextFigure(nextFigureId);
                nextFigureId = getNextFigureId();
            }

            // Increase completed iterations counter
            result.completeIteration();

        }

        return result;
    }

    public AdvancedBot generateBotWithRandomParameters(int coveredBlocksWeightMax, int pitsPeaksWeightMax, int densityWeightMax, int overheightWeightMax) {
        AdvancedBot bot = new AdvancedBot();
        bot.setCoveredBlocksWeight(RND.nextInt(coveredBlocksWeightMax));
        bot.setPitsPeaksWeight(RND.nextInt(pitsPeaksWeightMax));
        bot.setDensityWeight(RND.nextInt(densityWeightMax));
        bot.setOverheightWeight(RND.nextInt(overheightWeightMax));

        return bot;
    }

    public List<AdvancedBot> generateInitialPopulation(int populationSize, int coveredBlocksWeightMax, int pitsPeaksWeightMax, int densityWeightMax, int overheightWeightMax) {
        List<AdvancedBot> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(generateBotWithRandomParameters(coveredBlocksWeightMax, pitsPeaksWeightMax, densityWeightMax, overheightWeightMax));
        }
        return initialPopulation;
    }
}
