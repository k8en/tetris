package org.kdepo.games.tetris.desktop.screens;

import org.kdepo.games.tetris.desktop.Constants;
import org.kdepo.games.tetris.desktop.model.Field;
import org.kdepo.games.tetris.desktop.model.FigurePreview;
import org.kdepo.graphics.k2d.KeyHandler;
import org.kdepo.graphics.k2d.MouseHandler;
import org.kdepo.graphics.k2d.screens.AbstractScreen;

import java.awt.*;
import java.util.Map;

public class GameScreen extends AbstractScreen {

    private Field field;
    private FigurePreview figurePreview;

    public GameScreen() {
        this.name = Constants.Screens.GAME;

        field = new Field(10, 10);
        figurePreview = new FigurePreview(350, 10, 4, 4);
    }

    @Override
    public void initialize(Map<String, Object> parameters) {

    }

    @Override
    public void update(KeyHandler keyHandler, MouseHandler mouseHandler) {

    }

    @Override
    public void render(Graphics2D g) {
        field.render(g);
        figurePreview.render(g);
    }

    @Override
    public void dispose() {

    }
}
