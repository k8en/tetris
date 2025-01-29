package org.kdepo.games.tetris.desktop.screens;

import org.kdepo.games.tetris.desktop.Constants;
import org.kdepo.games.tetris.desktop.model.Field;
import org.kdepo.games.tetris.desktop.model.Figure;
import org.kdepo.games.tetris.desktop.model.FigurePreview;
import org.kdepo.games.tetris.desktop.utils.FigureUtils;
import org.kdepo.graphics.k2d.KeyHandler;
import org.kdepo.graphics.k2d.MouseHandler;
import org.kdepo.graphics.k2d.screens.AbstractScreen;

import java.awt.*;
import java.util.Map;

public class GameScreen extends AbstractScreen {

    private Figure nextFigure;
    private Figure currentFigure;

    private int figureCellX;
    private int figureCellY;

    private final int fieldX;
    private final int fieldY;
    private Field field;

    private final int fieldPreviewX;
    private final int fieldPreviewY;
    private FigurePreview figurePreview;

    public GameScreen() {
        this.name = Constants.Screens.GAME;

        fieldX = 10;
        fieldY = 10;

        fieldPreviewX = 350;
        fieldPreviewY = 10;
    }

    @Override
    public void initialize(Map<String, Object> parameters) {
        field = new Field(fieldX, fieldY);
        figurePreview = new FigurePreview(fieldPreviewX, fieldPreviewY, 4, 4);

        nextFigure = new Figure(fieldPreviewX, fieldPreviewY);
        nextFigure.setData(FigureUtils.getNextFigure());

        figureCellX = 3;
        figureCellY = 0;

        currentFigure = new Figure(fieldX + figureCellX * Constants.BLOCK_SIZE, fieldY + figureCellY * Constants.BLOCK_SIZE);
        currentFigure.setData(FigureUtils.getNextFigure());
    }

    @Override
    public void update(KeyHandler keyHandler, MouseHandler mouseHandler) {

    }

    @Override
    public void render(Graphics2D g) {
        field.render(g);
        currentFigure.render(g);

        figurePreview.render(g);
        nextFigure.render(g);
    }

    @Override
    public void dispose() {

    }
}
