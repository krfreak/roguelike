package model;

/**
 * a class with only static constants
 *
 * @author Tim
 *
 */
public class Constants {
    //
    // GAMEPLAY STUFF
    //
    /**
     * maximum room size for level generation
     */
    public static final int MAX_ROOM_SIZE = 15;

    // ###################
    // JFrame and PanelCanvas stuff
    // ###################
    /**
     * sqares per row to be drawn
     */
    public static int SQUARES_Y = 22;
    /**
     * squares per column to be drawn
     */
    public static int SQUARES_X = 30;
    /**
     * tile size
     */
    public static int TILE_SIZE = 25;
    /**
     * canvas width
     */
    public static int canvasWidth = 750;
    /**
     * canvas height
     */
    public static int canvasHeight = 550;
    /**
     * number of tiles
     */
    public static int tileCount = 3;
    // ###################
    // Graphic stuff
    // ###################
    /**
     * path to the player image
     */
    public static String PLAYER_IMAGE = "res/tiles/Tux_Gandalf.png";
    /**
     * path to the floor1 tile
     */
    public static String FLOOR1_IMAGE = "res/tiles/floor1.png";
    /**
     * path to the wall1 tile
     */
    public static String WALL1_IMAGE = "res/tiles/wall1.png";
}
