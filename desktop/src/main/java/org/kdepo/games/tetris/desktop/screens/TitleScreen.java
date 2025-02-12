package org.kdepo.games.tetris.desktop.screens;

import org.kdepo.games.tetris.shared.Constants;
import org.kdepo.graphics.k2d.KeyHandler;
import org.kdepo.graphics.k2d.MouseHandler;
import org.kdepo.graphics.k2d.screens.AbstractScreen;

import java.awt.*;
import java.util.Map;

public class TitleScreen extends AbstractScreen {

    private Map<String, Object> parameters;

    private int menuItemFocusedIndex;
    private int menuItemLastIndex;

    private static final int MI_START_GAME = 0;

    private static final int MI_LEFT_VARIANTS = 1;
    private final String[] leftVariants = {
            "HUMAN",
            "TEST_BOT",
            "SIMPLE_BOT",
            "ADVANCED_BOT"
    };
    private int leftVariantSelectedIndex;

    private static final int MI_RIGHT_VARIANTS = 2;
    private final String[] rightVariants = {
            "NOT USED",
            "TEST_BOT",
            "SIMPLE_BOT",
            "ADVANCED_BOT"
    };
    private int rightVariantSelectedIndex;

    private long controlsTimer;

    public TitleScreen() {
        this.name = Constants.Screens.TITLE;
    }

    @Override
    public void initialize(Map<String, Object> parameters) {
        this.parameters = parameters;

        menuItemFocusedIndex = 0;
        menuItemLastIndex = 2;

        leftVariantSelectedIndex = 0;
        rightVariantSelectedIndex = 0;

        controlsTimer = System.currentTimeMillis();
    }

    @Override
    public void update(KeyHandler keyHandler, MouseHandler mouseHandler) {
        if (keyHandler.isLeftPressed() && isControlsReady()) {
            if (MI_START_GAME == menuItemFocusedIndex) {
                // do nothing
            } else if (MI_LEFT_VARIANTS == menuItemFocusedIndex) {
                leftVariantSelectedIndex--;
                if (leftVariantSelectedIndex < 0) {
                    leftVariantSelectedIndex = leftVariants.length - 1;
                }
            } else if (MI_RIGHT_VARIANTS == menuItemFocusedIndex) {
                rightVariantSelectedIndex--;
                if (rightVariantSelectedIndex < 0) {
                    rightVariantSelectedIndex = rightVariants.length - 1;
                }
            } else {
                throw new RuntimeException("Unknown menuItemFocusedIndex " + menuItemFocusedIndex);
            }
            resetControlsTimer();

        } else if (keyHandler.isRightPressed() && isControlsReady()) {
            if (MI_START_GAME == menuItemFocusedIndex) {
                // do nothing
            } else if (MI_LEFT_VARIANTS == menuItemFocusedIndex) {
                leftVariantSelectedIndex++;
                if (leftVariantSelectedIndex >= leftVariants.length) {
                    leftVariantSelectedIndex = 0;
                }
            } else if (MI_RIGHT_VARIANTS == menuItemFocusedIndex) {
                rightVariantSelectedIndex++;
                if (rightVariantSelectedIndex >= rightVariants.length) {
                    rightVariantSelectedIndex = 0;
                }
            } else {
                throw new RuntimeException("Unknown menuItemFocusedIndex " + menuItemFocusedIndex);
            }
            resetControlsTimer();

        } else if (keyHandler.isUpPressed() && isControlsReady()) {
            menuItemFocusedIndex--;
            if (menuItemFocusedIndex < 0) {
                menuItemFocusedIndex = menuItemLastIndex;
            }
            resetControlsTimer();

        } else if (keyHandler.isDownPressed() && isControlsReady()) {
            menuItemFocusedIndex++;
            if (menuItemFocusedIndex > menuItemLastIndex) {
                menuItemFocusedIndex = 0;
            }
            resetControlsTimer();

        } else if (keyHandler.isEnterPressed() && isControlsReady()) {
            if (MI_START_GAME == menuItemFocusedIndex) {
                // Save parameters
                parameters.put(Constants.ScreenParameters.LEFT_PLAYER, resolvePlayerId(leftVariants[leftVariantSelectedIndex]));
                parameters.put(Constants.ScreenParameters.RIGHT_PLAYER, resolvePlayerId(rightVariants[rightVariantSelectedIndex]));

                screenController.setActiveScreen(Constants.Screens.GAME, parameters);
            }
            resetControlsTimer();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);

        g.drawString(">", 12, 20 + menuItemFocusedIndex * 15);

        g.drawString("START GAME", 20, 20);

        g.drawString("LEFT", 20, 35);
        g.drawString(leftVariants[leftVariantSelectedIndex], 120, 35);

        g.drawString("RIGHT", 20, 50);
        g.drawString(rightVariants[rightVariantSelectedIndex], 120, 50);
    }

    @Override
    public void dispose() {

    }

    private boolean isControlsReady() {
        return System.currentTimeMillis() > controlsTimer;
    }

    private void resetControlsTimer() {
        controlsTimer = System.currentTimeMillis() + 100;
    }

    private int resolvePlayerId(String playerName) {
        if ("NOT USED".equals(playerName)) {
            return Constants.Players.NO_PLAYER;
        } else if ("HUMAN".equals(playerName)) {
            return Constants.Players.HUMAN;
        } else if ("TEST_BOT".equals(playerName)) {
            return Constants.Players.TEST_BOT;
        } else if ("SIMPLE_BOT".equals(playerName)) {
            return Constants.Players.SIMPLE_BOT;
        } else if ("ADVANCED_BOT".equals(playerName)) {
            return Constants.Players.ADVANCED_BOT;
        } else {
            throw new RuntimeException("Cannot resolve player id for " + playerName);
        }
    }
}
