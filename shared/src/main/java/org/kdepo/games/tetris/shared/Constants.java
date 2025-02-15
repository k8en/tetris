package org.kdepo.games.tetris.shared;

public class Constants {

    /**
     * Versioning as X.Y.Z
     * X - releases
     * Y - improvements / refactoring
     * Z - bugfix counter
     */
    public static String VERSION = "1.0.0";

    public static final int FIELD_ROWS_HIDDEN = 4;
    public static final int FIELD_BLOCKS_HORIZONTALLY = 10;
    public static final int FIELD_BLOCKS_VERTICALLY = 20;

    /**
     * Figure width and height block size in pixels
     */
    public static final int BLOCK_SIZE = 32;

    /**
     * Screen width in pixels
     */
    public static final int SCREEN_WIDTH = 980;

    /**
     * Screen height in pixels
     */
    public static final int SCREEN_HEIGHT = 680;

    public interface Players {
        /**
         * No inputs are used. Figures drop down without any controls applied
         */
        int NO_PLAYER = 0;

        /**
         * User inputs are used to control figures. Available for the left field only
         */
        int HUMAN = 1;

        /**
         * Inputs from TestBot are used to control figures
         */
        int TEST_BOT = 2;

        /**
         * Inputs from SimpleBot are used to control figures
         */
        int SIMPLE_BOT = 3;

        /**
         * Input from HeuristicBot are used to control figures
         */
        int HEURISTIC_BOT = 4;
    }

    /**
     * Parameters to share between screens
     */
    public interface ScreenParameters {
        /**
         * Player to play on the left field
         */
        String LEFT_PLAYER = "left_player";

        /**
         * Player to play on the right field
         */
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
