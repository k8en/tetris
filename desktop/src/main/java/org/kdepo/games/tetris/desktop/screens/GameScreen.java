package org.kdepo.games.tetris.desktop.screens;

import org.kdepo.games.tetris.bot.AbstractBot;
import org.kdepo.games.tetris.bot.SimpleBot;
import org.kdepo.games.tetris.bot.TestBot;
import org.kdepo.games.tetris.bot.model.BotAction;
import org.kdepo.games.tetris.desktop.model.FigurePreview;
import org.kdepo.games.tetris.desktop.model.Statistics;
import org.kdepo.games.tetris.desktop.utils.DataCollectionUtils;
import org.kdepo.games.tetris.shared.Constants;
import org.kdepo.games.tetris.shared.utils.FieldUtils;
import org.kdepo.games.tetris.shared.utils.FigureUtils;
import org.kdepo.games.tetris.shared.model.Field;
import org.kdepo.games.tetris.shared.model.Figure;
import org.kdepo.graphics.k2d.KeyHandler;
import org.kdepo.graphics.k2d.MouseHandler;
import org.kdepo.graphics.k2d.screens.AbstractScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameScreen extends AbstractScreen {

    private Map<String, Object> parameters;

    private List<Figure> figuresToPlay;

    private int leftNextFigureIndex;
    private Figure leftNextFigure;
    private Figure leftCurrentFigure;

    private int rightNextFigureIndex;
    private Figure rightNextFigure;
    private Figure rightCurrentFigure;

    private int leftCurrentFigureFieldCellX;
    private int leftCurrentFigureFieldCellY;

    private int rightCurrentFigureFieldCellX;
    private int rightCurrentFigureFieldCellY;

    private final int leftFieldScreenPositionX;
    private final int leftFieldScreenPositionY;
    private Field leftField;

    private final int rightFieldScreenPositionX;
    private final int rightFieldScreenPositionY;
    private Field rightField;

    private final int leftFieldPreviewScreenPositionX;
    private final int leftFieldPreviewScreenPositionY;
    private FigurePreview leftFigurePreview;

    private final int rightFieldPreviewScreenPositionX;
    private final int rightFieldPreviewScreenPositionY;
    private FigurePreview rightFigurePreview;

    private long nextStepTimer;
    private final int nextStepDelay;

    private Statistics leftStatistics;
    private Statistics rightStatistics;

    private long leftControlsTimer;
    private long rightControlsTimer;

    private AbstractBot botAtLeft;
    private AbstractBot botAtRight;

    private boolean isLeftGameOver;
    private boolean isRightGameOver;

    public GameScreen() {
        this.name = Constants.Screens.GAME;

        leftFieldScreenPositionX = 20;
        leftFieldScreenPositionY = 20;

        leftFieldPreviewScreenPositionX = leftFieldScreenPositionX + 320 + 10;
        leftFieldPreviewScreenPositionY = leftFieldScreenPositionY;

        rightFieldScreenPositionX = leftFieldScreenPositionX + 320 + 10 + 130 + 20;
        rightFieldScreenPositionY = leftFieldScreenPositionY;

        rightFieldPreviewScreenPositionX = rightFieldScreenPositionX + 320 + 10;
        rightFieldPreviewScreenPositionY = leftFieldScreenPositionY;

        nextStepDelay = 1000;
        nextStepTimer = System.currentTimeMillis() + nextStepDelay;
    }

    @Override
    public void initialize(Map<String, Object> parameters) {
        this.parameters = parameters;

        // Resolve players
        Integer leftPlayerId = (Integer) parameters.get(Constants.ScreenParameters.LEFT_PLAYER);
        botAtLeft = resolvePlayer(leftPlayerId);

        Integer rightPlayerId = (Integer) parameters.get(Constants.ScreenParameters.RIGHT_PLAYER);
        botAtRight = resolvePlayer(rightPlayerId);

        // Prepare game fields
        leftField = new Field();
        leftField.setScreenPositionX(leftFieldScreenPositionX);
        leftField.setScreenPositionY(leftFieldScreenPositionY);

        rightField = new Field();
        rightField.setScreenPositionX(rightFieldScreenPositionX);
        rightField.setScreenPositionY(rightFieldScreenPositionY);

        // Prepare figures preview
        leftFigurePreview = new FigurePreview(leftFieldPreviewScreenPositionX, leftFieldPreviewScreenPositionY, 4, 4);
        rightFigurePreview = new FigurePreview(rightFieldPreviewScreenPositionX, rightFieldPreviewScreenPositionY, 4, 4);

        // Prepare figures generator and selection
        figuresToPlay = new ArrayList<>();

        leftNextFigureIndex = 0;
        leftNextFigure = getFigureToPlay(leftNextFigureIndex);
        leftNextFigureIndex++;
        leftCurrentFigure = getFigureToPlay(leftNextFigureIndex);
        leftNextFigureIndex++;

        rightNextFigureIndex = 0;
        rightNextFigure = getFigureToPlay(rightNextFigureIndex);
        rightNextFigureIndex++;
        rightCurrentFigure = getFigureToPlay(rightNextFigureIndex);
        rightNextFigureIndex++;

        // Prepare figure start positions
        leftCurrentFigureFieldCellX = 3;
        leftCurrentFigureFieldCellY = 0;

        rightCurrentFigureFieldCellX = leftCurrentFigureFieldCellX;
        rightCurrentFigureFieldCellY = leftCurrentFigureFieldCellY;

        // Prepare statistics
        leftStatistics = new Statistics();
        rightStatistics = new Statistics();

        // Delay for controls
        leftControlsTimer = System.currentTimeMillis();
        rightControlsTimer = System.currentTimeMillis();

        if (botAtLeft != null) {
            botAtLeft.think(
                    leftField.getData(),
                    leftCurrentFigure.getFigureId(),
                    leftCurrentFigure.getOrientationId(),
                    leftCurrentFigureFieldCellX,
                    leftNextFigure.getFigureId(),
                    leftNextFigure.getOrientationId()
            );
        } else {
            DataCollectionUtils.saveStartConditions(
                    leftCurrentFigure.getFigureId(),
                    leftCurrentFigure.getOrientationId(),
                    leftNextFigure.getFigureId(),
                    leftField.getData()
            );
        }
        if (botAtRight != null) {
            botAtRight.think(
                    rightField.getData(),
                    rightCurrentFigure.getFigureId(),
                    rightCurrentFigure.getOrientationId(),
                    rightCurrentFigureFieldCellX,
                    rightNextFigure.getFigureId(),
                    rightNextFigure.getOrientationId()
            );
        }

        isLeftGameOver = false;
        isRightGameOver = false;
    }

    @Override
    public void update(KeyHandler keyHandler, MouseHandler mouseHandler) {
        // It is time to move figure for one step down
        if (System.currentTimeMillis() >= nextStepTimer) {
            // Update timer for figure drop
            nextStepTimer = System.currentTimeMillis() + nextStepDelay;

            // Left side figure drop down
            if (!isLeftGameOver) {
                if (FieldUtils.canMoveDown(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                    leftCurrentFigureFieldCellY = leftCurrentFigureFieldCellY + 1;

                } else {
                    // Collect this type of statistics for human play only
                    if (botAtLeft == null) {
                        DataCollectionUtils.collect(leftCurrentFigure.getOrientationId(), leftCurrentFigureFieldCellX);
                    }

                    FieldUtils.mergeData(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY);

                    updateStatisticsFigures(leftStatistics, leftCurrentFigure);

                    List<Integer> completedLinesIndexes = FieldUtils.getCompletedLinesIndexes(leftField.getData());
                    if (!completedLinesIndexes.isEmpty()) {
                        updateStatisticsScore(leftStatistics, completedLinesIndexes.size());
                        FieldUtils.removeLines(leftField.getData(), completedLinesIndexes);

                        // Print this type of statistics for human play only
                        if (botAtLeft == null && leftStatistics.getLines() >= 100) {
                            System.out.println("Score: " + leftStatistics.getScore());
                            System.out.println("Lines: " + leftStatistics.getLines());
                            DataCollectionUtils.printCollectedData();
                        }
                    }

                    if (FieldUtils.isFieldOverflow(leftField.getData())) {
                        System.out.println("Game Over (Left)");
                        isLeftGameOver = true;

                    } else {
                        leftCurrentFigure = leftNextFigure;

                        leftNextFigure = getFigureToPlay(leftNextFigureIndex);
                        leftNextFigureIndex++;

                        leftCurrentFigureFieldCellX = 3;
                        leftCurrentFigureFieldCellY = 0;

                        if (botAtLeft != null) {
                            botAtLeft.think(
                                    leftField.getData(),
                                    leftCurrentFigure.getFigureId(),
                                    leftCurrentFigure.getOrientationId(),
                                    leftCurrentFigureFieldCellX,
                                    leftNextFigure.getFigureId(),
                                    leftNextFigure.getOrientationId()
                            );
                        } else {
                            DataCollectionUtils.saveStartConditions(
                                    leftCurrentFigure.getFigureId(),
                                    leftCurrentFigure.getOrientationId(),
                                    leftNextFigure.getFigureId(),
                                    leftField.getData()
                            );
                        }
                    }
                }
            }

            // Right side figure drop down
            if (!isRightGameOver) {
                if (FieldUtils.canMoveDown(rightField.getData(), rightCurrentFigure.getData(), rightCurrentFigureFieldCellX, rightCurrentFigureFieldCellY)) {
                    rightCurrentFigureFieldCellY = rightCurrentFigureFieldCellY + 1;

                } else {
                    FieldUtils.mergeData(rightField.getData(), rightCurrentFigure.getData(), rightCurrentFigureFieldCellX, rightCurrentFigureFieldCellY);

                    updateStatisticsFigures(rightStatistics, rightCurrentFigure);

                    List<Integer> completedLinesIndexes = FieldUtils.getCompletedLinesIndexes(rightField.getData());
                    if (!completedLinesIndexes.isEmpty()) {
                        updateStatisticsScore(rightStatistics, completedLinesIndexes.size());
                        FieldUtils.removeLines(rightField.getData(), completedLinesIndexes);
                    }

                    if (FieldUtils.isFieldOverflow(rightField.getData())) {
                        System.out.println("Game Over (Right)");
                        isRightGameOver = true;

                    } else {
                        rightCurrentFigure = rightNextFigure;

                        rightNextFigure = getFigureToPlay(rightNextFigureIndex);
                        rightNextFigureIndex++;

                        rightCurrentFigureFieldCellX = 3;
                        rightCurrentFigureFieldCellY = 0;

                        if (botAtRight != null) {
                            botAtRight.think(
                                    rightField.getData(),
                                    rightCurrentFigure.getFigureId(),
                                    rightCurrentFigure.getOrientationId(),
                                    rightCurrentFigureFieldCellX,
                                    rightNextFigure.getFigureId(),
                                    rightNextFigure.getOrientationId()
                            );
                        }
                    }
                }
            }

        } else {

            if (botAtLeft == null && !isLeftGameOver) {
                // Apply controls from human
                if (keyHandler.isRightPressed() && isLeftControlsReady()) {
                    if (FieldUtils.canMoveRight(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                        leftCurrentFigureFieldCellX = leftCurrentFigureFieldCellX + 1;
                    }
                    resetLeftControlsTimer();

                } else if (keyHandler.isLeftPressed() && isLeftControlsReady()) {
                    if (FieldUtils.canMoveLeft(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                        leftCurrentFigureFieldCellX = leftCurrentFigureFieldCellX - 1;
                    }
                    resetLeftControlsTimer();

                } else if (keyHandler.isDownPressed()) {
                    if (FieldUtils.canMoveDown(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                        leftCurrentFigureFieldCellY = leftCurrentFigureFieldCellY + 1;
                    }
                    resetLeftControlsTimer();
                } else if (keyHandler.isSpacePressed() && isLeftControlsReady()) {
                    Figure rotatedFigure = FigureUtils.getFigureRotated(leftCurrentFigure);
                    if (FieldUtils.canPlaceFigure(leftField.getData(), rotatedFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                        leftCurrentFigure.setOrientationId(rotatedFigure.getOrientationId());
                        leftCurrentFigure.setData(rotatedFigure.getData());
                    }
                    resetLeftControlsTimer();
                }
            } else {
                // Apply controls from bot
                if (isLeftControlsReady() && !isLeftGameOver) {
                    BotAction botAction = botAtLeft.getNextAction();
                    if (BotAction.MOVE_LEFT.equals(botAction)) {
                        if (FieldUtils.canMoveLeft(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                            leftCurrentFigureFieldCellX = leftCurrentFigureFieldCellX - 1;
                        } else {
                            System.out.println("BotAtLeft: Failed to move left");
                        }

                    } else if (BotAction.MOVE_RIGHT.equals(botAction)) {
                        if (FieldUtils.canMoveRight(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                            leftCurrentFigureFieldCellX = leftCurrentFigureFieldCellX + 1;
                        } else {
                            System.out.println("BotAtLeft: Failed to move right");
                        }

                    } else if (BotAction.ROTATE_CLOCKWISE.equals(botAction)) {
                        Figure rotatedFigure = FigureUtils.getFigureRotated(leftCurrentFigure);
                        if (FieldUtils.canPlaceFigure(leftField.getData(), rotatedFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                            leftCurrentFigure.setOrientationId(rotatedFigure.getOrientationId());
                            leftCurrentFigure.setData(rotatedFigure.getData());
                        } else {
                            System.out.println("BotAtLeft: Failed to rotate");
                        }

                    } else {
                        // No actions - lets drop down
                        if (FieldUtils.canMoveDown(leftField.getData(), leftCurrentFigure.getData(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY)) {
                            leftCurrentFigureFieldCellY = leftCurrentFigureFieldCellY + 1;
                        }
                    }

                    resetLeftControlsTimer();
                }
            }

            if (botAtRight != null && !isRightGameOver) {
                // Apply controls from bot
                if (isRightControlsReady()) {
                    BotAction botAction = botAtRight.getNextAction();
                    if (BotAction.MOVE_LEFT.equals(botAction)) {
                        if (FieldUtils.canMoveLeft(rightField.getData(), rightCurrentFigure.getData(), rightCurrentFigureFieldCellX, rightCurrentFigureFieldCellY)) {
                            rightCurrentFigureFieldCellX = rightCurrentFigureFieldCellX - 1;
                        } else {
                            System.out.println("BotAtRight: Failed to move left");
                        }

                    } else if (BotAction.MOVE_RIGHT.equals(botAction)) {
                        if (FieldUtils.canMoveRight(rightField.getData(), rightCurrentFigure.getData(), rightCurrentFigureFieldCellX, rightCurrentFigureFieldCellY)) {
                            rightCurrentFigureFieldCellX = rightCurrentFigureFieldCellX + 1;
                        } else {
                            System.out.println("BotAtRight: Failed to move right");
                        }

                    } else if (BotAction.ROTATE_CLOCKWISE.equals(botAction)) {
                        Figure rotatedFigure = FigureUtils.getFigureRotated(rightCurrentFigure);
                        if (FieldUtils.canPlaceFigure(rightField.getData(), rotatedFigure.getData(), rightCurrentFigureFieldCellX, rightCurrentFigureFieldCellY)) {
                            rightCurrentFigure.setOrientationId(rotatedFigure.getOrientationId());
                            rightCurrentFigure.setData(rotatedFigure.getData());
                        } else {
                            System.out.println("BotAtRight: Failed to rotate");
                        }

                    } else {
                        // No actions - lets drop down
                        if (FieldUtils.canMoveDown(rightField.getData(), rightCurrentFigure.getData(), rightCurrentFigureFieldCellX, rightCurrentFigureFieldCellY)) {
                            rightCurrentFigureFieldCellY = rightCurrentFigureFieldCellY + 1;
                        }
                    }

                    resetRightControlsTimer();
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);

        // Left player render
        renderField(g, leftField);
        renderFigure(g, leftCurrentFigure, leftField.getScreenPositionX(), leftField.getScreenPositionY(), leftCurrentFigureFieldCellX, leftCurrentFigureFieldCellY);

        leftFigurePreview.render(g);
        leftFigurePreview.renderFigure(g, leftNextFigure, 0, 0);

        renderStatistics(g, leftStatistics, leftFieldPreviewScreenPositionX, 170, leftFieldPreviewScreenPositionX + 70, 15);

        // Right player render
        renderField(g, rightField);
        renderFigure(g, rightCurrentFigure, rightField.getScreenPositionX(), rightField.getScreenPositionY(), rightCurrentFigureFieldCellX, rightCurrentFigureFieldCellY);

        rightFigurePreview.render(g);
        rightFigurePreview.renderFigure(g, rightNextFigure, 0, 0);

        renderStatistics(g, rightStatistics, rightFieldPreviewScreenPositionX, 170, rightFieldPreviewScreenPositionX + 70, 15);

        if (isLeftGameOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", leftFieldPreviewScreenPositionX, 170 + 15 * 9);
        }
        if (isRightGameOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", rightFieldPreviewScreenPositionX, 170 + 15 * 9);
        }
    }

    @Override
    public void dispose() {

    }

    public void renderField(Graphics2D g, Field field) {
        // Draw grid
        for (int row = 0; row <= field.getData().length - Constants.FIELD_ROWS_HIDDEN; row++) {
            g.drawLine(
                    field.getScreenPositionX(),
                    field.getScreenPositionY() + row * Constants.BLOCK_SIZE,
                    field.getScreenPositionX() + Constants.FIELD_BLOCKS_HORIZONTALLY * Constants.BLOCK_SIZE,
                    field.getScreenPositionY() + row * Constants.BLOCK_SIZE
            );
        }
        for (int column = 0; column <= field.getData()[0].length; column++) {
            g.drawLine(
                    field.getScreenPositionX() + column * Constants.BLOCK_SIZE,
                    field.getScreenPositionY(),
                    field.getScreenPositionX() + column * Constants.BLOCK_SIZE,
                    field.getScreenPositionY() + Constants.FIELD_BLOCKS_VERTICALLY * Constants.BLOCK_SIZE
            );
        }

        // Draw field data (skip hidden space)
        for (int row = 0; row < field.getData().length; row++) {
            if (row < Constants.FIELD_ROWS_HIDDEN) {
                continue;
            }
            for (int column = 0; column < field.getData()[0].length; column++) {
                if (field.getData()[row][column] == 1) {
                    g.fillRect(
                            field.getScreenPositionX() + column * Constants.BLOCK_SIZE,
                            field.getScreenPositionY() + (row - Constants.FIELD_ROWS_HIDDEN) * Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE);
                }
            }
        }
    }

    public void renderFigure(Graphics2D g, Figure figure, int fieldScreenPositionX, int fieldScreenPositionY, int fieldCellPositionX, int fieldCellPositionY) {
        for (int figureRow = 0; figureRow < figure.getData().length; figureRow++) {
            for (int figureColumn = 0; figureColumn < figure.getData()[0].length; figureColumn++) {

                // If row is outside the hidden space
                if (fieldCellPositionY + figureRow >= Constants.FIELD_ROWS_HIDDEN) {
                    if (figure.getData()[figureRow][figureColumn] == 1) {
                        g.fillRect(
                                fieldScreenPositionX + (fieldCellPositionX + figureColumn) * Constants.BLOCK_SIZE,
                                fieldScreenPositionY + (fieldCellPositionY + figureRow - Constants.FIELD_ROWS_HIDDEN) * Constants.BLOCK_SIZE,
                                Constants.BLOCK_SIZE,
                                Constants.BLOCK_SIZE
                        );
                    }
                }
            }
        }
    }

    private void renderStatistics(Graphics2D g, Statistics statistics, int textX, int textY, int valueX, int dY) {
        g.drawString("Score", textX, textY);
        g.drawString(String.valueOf(statistics.getScore()), valueX, textY);

        g.drawString("Lines", textX, textY + dY);
        g.drawString(String.valueOf(statistics.getLines()), valueX, textY + dY);

        g.drawString("Figures", textX, textY + dY * 2);
        g.drawString(String.valueOf(statistics.getFigures()), valueX, textY + dY * 2);

        g.drawString("Figure 1", textX, textY + dY * 3);
        g.drawString(String.valueOf(statistics.getFiguresType1()), valueX, textY + dY * 3);

        g.drawString("Figure 2", textX, textY + dY * 4);
        g.drawString(String.valueOf(statistics.getFiguresType2()), valueX, textY + dY * 4);

        g.drawString("Figure 3", textX, textY + dY * 5);
        g.drawString(String.valueOf(statistics.getFiguresType3()), valueX, textY + dY * 5);

        g.drawString("Figure 4", textX, textY + dY * 6);
        g.drawString(String.valueOf(statistics.getFiguresType4()), valueX, textY + dY * 6);

        g.drawString("Figure 5", textX, textY + dY * 7);
        g.drawString(String.valueOf(statistics.getFiguresType5()), valueX, textY + dY * 7);

        g.drawString("Figure 6", textX, textY + dY * 8);
        g.drawString(String.valueOf(statistics.getFiguresType6()), valueX, textY + dY * 8);
    }

    private Figure getFigureToPlay(int figureToPlayIndex) {
        if (figureToPlayIndex >= figuresToPlay.size()) {
            figuresToPlay.add(FigureUtils.getNextFigure());
        }
        return figuresToPlay.get(figureToPlayIndex).cloneFigure();
    }

    private boolean isLeftControlsReady() {
        return System.currentTimeMillis() > leftControlsTimer;
    }

    private void resetLeftControlsTimer() {
        leftControlsTimer = System.currentTimeMillis() + 100;
    }

    private boolean isRightControlsReady() {
        return System.currentTimeMillis() > rightControlsTimer;
    }

    private void resetRightControlsTimer() {
        rightControlsTimer = System.currentTimeMillis() + 100;
    }

    private void updateStatisticsFigures(Statistics statistics, Figure figure) {
        if (figure.getFigureId() == 1) {
            statistics.addFiguresType1(1);
        } else if (figure.getFigureId() == 2) {
            statistics.addFiguresType2(1);
        } else if (figure.getFigureId() == 3) {
            statistics.addFiguresType3(1);
        } else if (figure.getFigureId() == 4) {
            statistics.addFiguresType4(1);
        } else if (figure.getFigureId() == 5) {
            statistics.addFiguresType5(1);
        } else if (figure.getFigureId() == 6) {
            statistics.addFiguresType6(1);
        }
    }

    private void updateStatisticsScore(Statistics statistics, int linesCount) {
        if (linesCount <= 0 || linesCount > 4) {
            throw new RuntimeException("Wrong lines count: " + linesCount);
        }
        statistics.addLines(linesCount);
        if (linesCount == 1) {
            statistics.addScore(100);
        } else if (linesCount == 2) {
            statistics.addScore(300);
        } else if (linesCount == 3) {
            statistics.addScore(600);
        } else if (linesCount == 4) {
            statistics.addScore(1000);
        }
    }

    private AbstractBot resolvePlayer(int playerId) {
        if (Constants.Players.NO_PLAYER == playerId) {
            // Applicable for right side only
            return null;
        } else if (Constants.Players.HUMAN == playerId) {
            // Applicable for left side only
            return null;
        } else if (Constants.Players.TEST_BOT == playerId) {
            return new TestBot();
        } else if (Constants.Players.SIMPLE_BOT == playerId) {
            return new SimpleBot();
        }
        throw new RuntimeException("Player not resolved " + playerId);
    }
}
