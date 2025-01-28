package org.kdepo.games.tetris.desktop;

public class Constants {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    public interface Screens {
        String GAME = "game";
    }

    private Constants() {
        throw new RuntimeException("Instantiation is not allowed");
    }
}
