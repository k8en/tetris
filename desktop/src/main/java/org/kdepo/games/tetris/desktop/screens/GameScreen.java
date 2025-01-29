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

    public GameScreen() {
        this.name = Constants.Screens.GAME;

        fieldScreenPositionX = 10;
        fieldScreenPositionY = 10 + 100;

        fieldPreviewScreenPositionX = 350;
        fieldPreviewScreenPositionY = 10 + 100;

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
    }

    @Override
    public void update(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if (System.currentTimeMillis() >= nextStepTimer) {
            nextStepTimer = System.currentTimeMillis() + nextStepDelay;

            if (FieldUtils.canMoveDown(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY)) {
                currentFigureFieldCellY = currentFigureFieldCellY + 1;

            } else {
                FieldUtils.mergeData(field.getData(), currentFigure.getData(), currentFigureFieldCellX, currentFigureFieldCellY);

                if (FieldUtils.isFieldOverflow(field.getData())) {
                    System.out.println("Game Over");

                } else {
                    System.out.println("New figure generated");
                    currentFigure = FigureUtils.getNextFigure(0);

                    currentFigureFieldCellX = 3;
                    currentFigureFieldCellY = 0;
                }
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
}
