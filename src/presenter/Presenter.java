package presenter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import model.Constants;
import model.Game;
import model.gameObjects.Tile;
import view.View;

public class Presenter {

    /**
     * The view
     */
    private View view;

    /**
     * the model
     */
    private Game game;

    /**
     * a nice and tidy key dispatcher
     */
    private HashMap<Integer, KeyAction> keyDispatcher = new HashMap<Integer, KeyAction>();

    /**
     * holds every tile
     */
    private HashMap<String, Tile> tiles = new HashMap<String, Tile>();

    /**
     * mvp pattern passive presenter
     *
     * @param view
     * @param game
     */
    public Presenter(View view, Game game) {
	initKeyDispatcher();
	initTiles();
	this.view = view;
	this.game = game;

	sendLevelTiles();
	view.appendTextToTextArea("Welcome to the dungeon!\n");
    }

    /**
     * sends an image array with the tiles from the level to the view
     */
    public void sendLevelTiles() {
	int x = game.getPlayer().getxPos();
	int y = game.getPlayer().getyPos();

	view.getPanelCanvas().setScreenImages(createImageArray(game.getCurrentLevel().getTiles()));
	view.updateRightSidePanelPlayerPositionLabel("Player Position: [" + x + ":" + y + "]");
	view.updateCanvas();
    }

    /**
     * creates an Image array with boundaries specified in Constants and fills
     * it with images from every layer of the level
     *
     * @param numbers
     * @return
     */
    public Image[][] createImageArray(int[][] numbers) {
	int x = Constants.SQUARES_X;
	int y = Constants.SQUARES_Y;
	Image[][] img = new Image[x][y];
	int px = game.getPlayer().getxPos();
	int py = game.getPlayer().getyPos();
	int middleX = x / 2;
	int middleY = y / 2;
	// determine whether the x and y values are odd/even so the middle
	// doesn't get tilted
	int modX = x % 2;
	int modY = y % 2;
	int[][] source = numbers.clone();
	int[][] dest = new int[x][y];
	int i = 0;
	int j = 0;
	// only get the visible things out of the level into the Image array
	for (int row = py - middleY; row < (py + modY + middleY); row++) {
	    for (int col = px - middleX; col < (px + modX + middleX); col++) {
		// check the boundaries
		if ((col < 0) || (col >= numbers[0].length) || (row >= numbers.length) || (row < 0)) {
		    dest[i][j] = -1;
		} else {
		    dest[i][j] = source[row][col];
		}
		i++;
		if (i >= x) {
		    i = 0;
		}
	    }
	    j++;
	    if (j >= y) {
		break;
	    }
	}
	// fill with images
	for (int row = 0; row < dest.length; row++) {
	    for (int col = 0; col < dest[row].length; col++) {
		// tileset layer
		switch (dest[row][col]) {
		case -1:
		    img[row][col] = null;
		    break;
		case 0:
		    img[row][col] = tiles.get("floor1").getImg();
		    break;
		case 1:
		    img[row][col] = tiles.get("wall1").getImg();
		    break;
		}
		// item and non moving things layer

		// living things layer
		if ((row == middleX) && (col == middleY)) {
		    img[row][col] = overlayImages(img[row][col], tiles.get("player").getImg());
		}
		// fog of war layer
	    }
	}
	return img;
    }

    public Image[][] createScreen() {
	int x = Constants.SQUARES_X;
	int y = Constants.SQUARES_Y;
	Image[][] img = new Image[x][y];
	return img;
    }

    /**
     * Returns a 2D Image array based off the levelData
     *
     * @param levelData
     *            2D-Array of the level
     * @return The 2D-Array of tiles
     */
    private Image[][] createTileSetLayer(int[][] levelData) {
	Image[][] layer = new Image[levelData[0].length][levelData.length];
	for (int row = 0; row < levelData.length; row++) {
	    for (int col = 0; col < levelData[row].length; col++) {
		layer[row][col] = getTileById(levelData[row][col]);
	    }
	}
	return layer;
    }

    /**
     * Assigns an Image to a given tile ID
     *
     * @param tileId
     *            Integer ID of tile
     * @return The tile image
     */
    private Image getTileById(int tileId) {
	switch (tileId) {
	case -1:
	    break;
	case 0:
	    return tiles.get("floor1").getImg();
	case 1:
	    return tiles.get("wall1").getImg();
	}
	return null;
    }

    /**
     * Draws img2 over img1
     *
     * @param img1
     *            background image
     * @param img2
     *            overlay image
     * @return combined image
     */
    private Image overlayImages(Image img1, Image img2) {
	// create the new image, canvas size is the max. of both
	// image sizes
	int w = Math.max(img1.getWidth(null), img2.getWidth(null));
	int h = Math.max(img1.getHeight(null), img2.getHeight(null));
	BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	// paint both images, preserving the alpha channels
	Graphics g = combined.getGraphics();
	g.drawImage(img1, 0, 0, null);
	g.drawImage(img2, 0, 0, null);
	g.dispose();
	return combined;
    }

    /**
     * get the key dispatcher
     *
     * @return HashMap<Integer, KeyAction>
     */
    public HashMap<Integer, KeyAction> getKeyDispatcher() {
	return keyDispatcher;
    }

    /**
     * adds all the key handling mathods to the key dispatcher hash map
     */
    private void initKeyDispatcher() {
	// to the north it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD8, () -> moveNorth());
	keyDispatcher.put(KeyEvent.VK_UP, () -> moveNorth());
	// to the south it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD2, () -> moveSouth());
	keyDispatcher.put(KeyEvent.VK_KP_DOWN, () -> moveSouth());
	keyDispatcher.put(KeyEvent.VK_DOWN, () -> moveSouth());
	// to the west it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD4, () -> moveWest());
	keyDispatcher.put(KeyEvent.VK_KP_LEFT, () -> moveWest());
	keyDispatcher.put(KeyEvent.VK_LEFT, () -> moveWest());
	// to the east it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD6, () -> moveEast());
	keyDispatcher.put(KeyEvent.VK_KP_RIGHT, () -> moveEast());
	keyDispatcher.put(KeyEvent.VK_RIGHT, () -> moveEast());
	// to the north west it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD7, () -> moveNorthWest());
	keyDispatcher.put(KeyEvent.VK_HOME, () -> moveNorth());
	// to the north east it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD9, () -> moveNorthEast());
	keyDispatcher.put(KeyEvent.VK_PAGE_UP, () -> moveNorth());
	// to the south west it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD1, () -> moveSouthWest());
	keyDispatcher.put(KeyEvent.VK_END, () -> moveNorth());
	// to the south east it goes
	keyDispatcher.put(KeyEvent.VK_NUMPAD3, () -> moveSouthEast());
	keyDispatcher.put(KeyEvent.VK_PAGE_DOWN, () -> moveNorth());
	// draw the grid
	keyDispatcher.put(KeyEvent.VK_F12, () -> toggleGrid());
    }

    /**
     * loads and sets up the tiles
     */
    public void initTiles() {
	tiles.put("floor1", new Tile(Constants.FLOOR1_IMAGE));
	tiles.put("wall1", new Tile(Constants.WALL1_IMAGE));
	tiles.put("player", new Tile(Constants.PLAYER_IMAGE));
    }

    /**
     * moves the player east one tile
     */
    private void moveEast() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos() + 1), game.getPlayer().getyPos())) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos() + 1, game.getPlayer().getyPos());
	    sendLevelTiles();
	}
    }

    /**
     * moves the player north one tile
     */
    private void moveNorth() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos()), game.getPlayer().getyPos() - 1)) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos(), game.getPlayer().getyPos() - 1);
	    sendLevelTiles();
	}
    }

    /**
     * moves the player north one tile and east one tile
     */
    private void moveNorthEast() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos() + 1), game.getPlayer().getyPos() - 1)) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos() + 1, game.getPlayer().getyPos() - 1);
	    sendLevelTiles();
	}
    }

    /**
     * moves the player north one tile and west one tile
     */
    private void moveNorthWest() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos() - 1), game.getPlayer().getyPos() - 1)) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos() - 1, game.getPlayer().getyPos() - 1);
	    sendLevelTiles();
	}
    }

    /**
     * moves the player down one tile
     */
    private void moveSouth() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos()), game.getPlayer().getyPos() + 1)) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos(), game.getPlayer().getyPos() + 1);
	    sendLevelTiles();
	}
    }

    /**
     * moves the player down one tile and east one tile
     */
    private void moveSouthEast() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos() + 1), game.getPlayer().getyPos() + 1)) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos() + 1, game.getPlayer().getyPos() + 1);
	    sendLevelTiles();
	}
    }

    /**
     * moves the player down one tile and west one tile
     */
    private void moveSouthWest() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos() - 1), game.getPlayer().getyPos() + 1)) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos() - 1, game.getPlayer().getyPos() + 1);
	    sendLevelTiles();
	}
    }

    /**
     * moves the player west one tile
     */
    private void moveWest() {
	if (!game.getCurrentLevel().isWall((game.getPlayer().getxPos() - 1), game.getPlayer().getyPos())) {
	    game.getPlayer().setPosition(game.getPlayer().getxPos() - 1, game.getPlayer().getyPos());
	    sendLevelTiles();
	}
    }

    /**
     * redirects the boolean to the canvas
     *
     * @param b
     */
    public void toggleGrid() {
	if (view.getPanelCanvas().isGridDrawn()) {
	    view.getPanelCanvas().setGridDrawn(false);
	} else {
	    view.getPanelCanvas().setGridDrawn(true);
	}
	view.updateCanvas();
    }
}
