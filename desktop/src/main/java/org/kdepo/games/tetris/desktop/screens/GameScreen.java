package org.kdepo.games.tetris.desktop.screens;

import org.kdepo.games.tetris.desktop.Constants;
import org.kdepo.games.tetris.desktop.model.Field;
import org.kdepo.games.tetris.desktop.model.Figure;
import org.kdepo.games.tetris.desktop.model.FigurePreview;
import org.kdepo.games.tetris.desktop.model.Statistics;
import org.kdepo.games.tetris.desktop.utils.FieldUtils;
import org.kdepo.games.tetris.desktop.utils.FigureUtils;
import org.kdepo.graphics.k2d.KeyHandler;
import org.kdepo.graphics.k2d.MouseHandler;
import org.kdepo.graphics.k2d.screens.AbstractScreen;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GameScreen extends AbstractScreen {

    private Figure nextFigure;
    private Figure currentFigure;

    private int currentFigureFieldCellX;
    private int currentFigureFieldCellY;

    private final int fieldScreenPositionX;
    private final int fieldScreenPositionY;
    private Field field;

    private final int fieldPreviewScreenPositionX;
    private final int fieldPreviewScreenPositionY;
    private FigurePreview figurePreview;

    private long nextStepTimer;
    private final int nextStepDelay;

    private Statistics statistics;

    private long controlsTimer;

    public GameScreen() {
        this.name = Constants.Screens.GAME;

        fieldScreenPositionX = 10;
        fieldScreenPositionY = 10;

        fieldPreviewScreenPositionX = 350;
        fieldPreviewScreenPositionY = 10;

        nextStepDelay = 1000;
        nextStepTimer = System.currentTimeMillis() + nextStepDelay;
    }

    @Override
    public void initialize(Map<String, Object> parameters) {
        field = new Field();
        field.setScreenPositionX(fieldScreenPositionX);
        field.setScreenPositionY(fieldScreenPositionY);

        figurePreview = new FigurePreview(fieldPreviewScreenPositionX, fieldPreviewScreenPositionY, 4, 4);

        nextFigure = FigureUtils.getNextFigure();
        currentFigure = FigureUtils.getNextFigure();

        currentFigureFieldCellX = 3;
        currentFigureFieldCellY = 0;

        statistics = new Statistics();

        controlsTimer = System.currentTimeMillis();
    }

    @Override
    public void update(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if (System.currentTimeMillis() >= nextStepTimer) {
            nextStepTimer = System.currentTimeMillis() + nextStepDelay;

            if (FieldUtils.canMoveDown(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellY = currentFigureFieldCellY + 1;

            } else {
                FieldUtils.mergeData(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY);

                // Collect figure statistics
                if (currentFigure.getFigureId() == 1) {
                    statistics.addFiguresType1(1);
                } else if (currentFigure.getFigureId() == 2) {
                    statistics.addFiguresType2(1);
                } else if (currentFigure.getFigureId() == 3) {
                    statistics.addFiguresType3(1);
                } else if (currentFigure.getFigureId() == 4) {
                    statistics.addFiguresType4(1);
                } else if (currentFigure.getFigureId() == 5) {
                    statistics.addFiguresType5(1);
                } else if (currentFigure.getFigureId() == 6) {
                    statistics.addFiguresType6(1);
                }

                List<Integer> completedLinesIndexes = FieldUtils.getCompletedLinesIndexes(field.getData());
                if (!completedLinesIndexes.isEmpty()) {
                    updateStatistics(completedLinesIndexes.size());
                    FieldUtils.removeLines(field.getData(), completedLinesIndexes);
                }

                if (FieldUtils.isFieldOverflow(field.getData())) {
                    System.out.println("Game Over");

                } else {
                    System.out.println("New figure generated");
                    currentFigure = nextFigure;
                    nextFigure = FigureUtils.getNextFigure();

                    currentFigureFieldCellX = 3;
                    currentFigureFieldCellY = 0;
                }
            }

        } else if (keyHandler.isRightPressed() && isControlsReady()) {
            if (FieldUtils.canMoveRight(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellX = currentFigureFieldCellX + 1;
            }
            resetControlsTimer();

        } else if (keyHandler.isLeftPressed() && isControlsReady()) {
            if (FieldUtils.canMoveLeft(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellX = currentFigureFieldCellX - 1;
            }
            resetControlsTimer();

        } else if (keyHandler.isDownPressed()) {
            if (FieldUtils.canMoveDown(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellY = currentFigureFieldCellY + 1;
            }
            resetControlsTimer();
        } else if (keyHandler.isSpacePressed() && isControlsReady()) {
            Figure rotatedFigure = FigureUtils.getRotatedFigure(currentFigure);
            if (FieldUtils.canPlaceFigure(field.getData(), rotatedFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigure.setOrientationId(rotatedFigure.getOrientationId());
                currentFigure.setData(rotatedFigure.getData());
            }
            resetControlsTimer();
        }

    }

    @Override
    public void render(Graphics2D g) {
        field.render(g);
        field.renderFigure(g, currentFigure, currentFigureFieldCellX, currentFigureFieldCellY);

        figurePreview.render(g);
        figurePreview.renderFigure(g, nextFigure, 0, 0);

        renderStatistics(g, 350, 170, 420, 15);
    }

    @Override
    public void dispose() {

    }

    private void renderStatistics(Graphics2D g, int textX, int textY, int valueX, int dY) {
        g.drawString("Score", textX, textY);
        g.drawString(String.valueOf(statistics.getScore()), valueX, textY);

        g.drawString("Lines", textX, textY + dY);
        g.drawString(String.valueOf(statistics.getLines()), valueX, textY + dY);

        g.drawString("Figure 1", textX, textY + dY * 2);
        g.drawString(String.valueOf(statistics.getFiguresType1()), valueX, textY + dY * 2);

        g.drawString("Figure 2", textX, textY + dY * 3);
        g.drawString(String.valueOf(statistics.getFiguresType2()), valueX, textY + dY * 3);

        g.drawString("Figure 3", textX, textY + dY * 4);
        g.drawString(String.valueOf(statistics.getFiguresType3()), valueX, textY + dY * 4);

        g.drawString("Figure 4", textX, textY + dY * 5);
        g.drawString(String.valueOf(statistics.getFiguresType4()), valueX, textY + dY * 5);

        g.drawString("Figure 5", textX, textY + dY * 6);
        g.drawString(String.valueOf(statistics.getFiguresType5()), valueX, textY + dY * 6);

        g.drawString("Figure 6", textX, textY + dY * 7);
        g.drawString(String.valueOf(statistics.getFiguresType6()), valueX, textY + dY * 7);
    }

    private boolean isControlsReady() {
        return System.currentTimeMillis() > controlsTimer;
    }

    private void resetControlsTimer() {
        controlsTimer = System.currentTimeMillis() + 100;
    }

    private void updateStatistics(int linesCount) {
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
}
