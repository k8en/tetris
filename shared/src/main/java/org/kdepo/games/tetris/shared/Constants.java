package org.kdepo.games.tetris.shared;

public class Constants {

    public static final int FIELD_ROWS_HIDDEN = 4;
    public static final int FIELD_BLOCKS_HORIZONTALLY = 10;
    public static final int FIELD_BLOCKS_VERTICALLY = 20;

    public static final int BLOCK_SIZE = 32;

    public static final int SCREEN_WIDTH = 980;
    public static final int SCREEN_HEIGHT = 680;

    public interface Players {
        int NO_PLAYER = 0;
        int HUMAN = 1;
        int TEST_BOT = 2;
        int SIMPLE_BOT = 3;
        int ADVANCED_BOT = 4;
    }

    public interface ScreenParameters {
        String LEFT_PLAYER = "left_player";
        String RIGHT_PLAYER = "right_player";
    }

    public interface Screens {
        String GAME = "game";
        String TITLE = "title";
    }

    private Constants() {
        throw new RuntimeException("Instantiation is not allowed");
    }
}
