package org.kdepo.games.tetris.desktop.screens;

import org.kdepo.games.tetris.desktop.Constants;
import org.kdepo.games.tetris.desktop.model.Field;
import org.kdepo.games.tetris.desktop.model.Figure;
import org.kdepo.games.tetris.desktop.model.FigurePreview;
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

    private int linesCount;
    private int score;

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

        nextFigure = FigureUtils.getNextFigure(0);
        currentFigure = FigureUtils.getNextFigure(0);

        currentFigureFieldCellX = 3;
        currentFigureFieldCellY = 0;

        linesCount = 0;
        score = 0;
    }

    @Override
    public void update(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if (System.currentTimeMillis() >= nextStepTimer) {
            nextStepTimer = System.currentTimeMillis() + nextStepDelay;

            if (FieldUtils.canMoveDown(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellY = currentFigureFieldCellY + 1;

            } else {
                FieldUtils.mergeData(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY);

                List<Integer> completedLinesIndexes = FieldUtils.getCompletedLinesIndexes(field.getData());
                if (!completedLinesIndexes.isEmpty()) {
                    updateStatistics(completedLinesIndexes.size());
                    FieldUtils.removeLines(field.getData(), completedLinesIndexes);
                }

                if (FieldUtils.isFieldOverflow(field.getData())) {
                    System.out.println("Game Over");

                } else {
                    System.out.println("New figure generated");
                    currentFigure = FigureUtils.getNextFigure(0);

                    currentFigureFieldCellX = 3;
                    currentFigureFieldCellY = 0;
                }
            }

        } else if (keyHandler.isRightPressed()) {
            if (FieldUtils.canMoveRight(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellX = currentFigureFieldCellX + 1;
            }

        } else if (keyHandler.isLeftPressed()) {
            if (FieldUtils.canMoveLeft(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellX = currentFigureFieldCellX - 1;
            }

        } else if (keyHandler.isDownPressed()) {
            if (FieldUtils.canMoveDown(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellY = currentFigureFieldCellY + 1;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        field.render(g);
        field.renderFigure(g, currentFigure, currentFigureFieldCellX, currentFigureFieldCellY);

        figurePreview.render(g);
        figurePreview.renderFigure(g, nextFigure, 0, 0);
    }

    @Override
    public void dispose() {

    }

    private void updateStatistics(int linesCount) {
        if (linesCount <= 0 || linesCount > 4) {
            throw new RuntimeException("Wrong lines count: " + linesCount);
        }
        this.linesCount = this.linesCount + linesCount;
        if (linesCount == 1) {
            score = score + 100;
        } else if (linesCount == 2) {
            score = score + 300;
        } else if (linesCount == 3) {
            score = score + 600;
        } else if (linesCount == 4) {
            score = score + 1000;
        }
    }
}
