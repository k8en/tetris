package org.kdepo.games.tetris.desktop;

import org.kdepo.games.tetris.desktop.screens.GameScreen;
import org.kdepo.graphics.k2d.GameEngine;
import org.kdepo.graphics.k2d.GamePanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        gameEngine.setScreenWidth(Constants.SCREEN_WIDTH);
        gameEngine.setScreenHeight(Constants.SCREEN_HEIGHT);

        GameScreen gameScreen = new GameScreen();

        gameEngine.addScreen(gameScreen);

        // Parameters map to share between screens
        Map<String, Object> parameters = new HashMap<>();
        gameEngine.setActiveScreen(gameScreen.getName(), parameters);

        GamePanel gamePanel = new GamePanel(gameEngine.getScreenWidth(), gameEngine.getScreenHeight());
        gamePanel.setGameEngine(gameEngine);

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game");
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
