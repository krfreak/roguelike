package model.gameObjects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Constants;

/**
 * a level that consists of some values
 *
 * @author Tim
 *
 */
public class Level {
    /**
     * basic level with 100x100 fields
     *
     * @return
     */
    public static Level randomLevel() {
	Level tmpLevel = Level.standardLevel();
	for (int row = 0; row < tmpLevel.collisionLayer.length; row++) {
	    for (int col = 0; col < tmpLevel.collisionLayer[row].length; col++) {
		tmpLevel.collisionLayer[row][col] = true;
	    }
	}

	tmpLevel.generateRooms(50, false);
	tmpLevel.connectRooms();

	tmpLevel.generateOuterWalls();
	tmpLevel.generateTileSet();
	tmpLevel.visited = tmpLevel.collisionLayer.clone();
	return tmpLevel;
    }

    /**
     * basic level with 100x100 fields
     *
     * @return
     */
    public static Level standardLevel() {
	Level tmpLevel = new Level(100, 100);

	for (int i = 0; i < tmpLevel.collisionLayer.length; i++) {
	    tmpLevel.collisionLayer[0][i] = true;
	    tmpLevel.collisionLayer[99][i] = true;
	}
	for (int i = 0; i < tmpLevel.collisionLayer.length; i++) {
	    tmpLevel.collisionLayer[i][0] = true;
	    tmpLevel.collisionLayer[i][99] = true;
	}

	// System.out.println(Arrays.deepToString(tmpLevel.tiles));
	return tmpLevel;
    }

    /**
     * a list of all the generated rooms
     */
    private ArrayList<Room> rooms = new ArrayList<Room>();

    /**
     * checks position for wall
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isWall(int x, int y) {
	if ((x < 0) || (x > (tiles[0].length - 1)) || (y < 0) || (y > (tiles.length - 1))) {
	    return true;
	}
	// String wall = collisionLayer[y][x] ? "Wall" : "Floor";
	// System.out.println("[" + x + "," + y + "] is " + wall);
	return collisionLayer[y][x];
    }

    /**
     * @return the rooms
     */
    public ArrayList<Room> getRooms() {
	return rooms;
    }

    /**
     * level width
     */
    private int xSize;

    /**
     * height of the level
     */
    private int ySize;

    /**
     * collision layer
     */
    private boolean[][] collisionLayer;

    /**
     * contains every seen block
     */
    private boolean[][] visited;

    /**
     * the tiles that can be drawn
     */
    private int[][] tiles;

    private ArrayList<Object> layers;

    /**
     * basic constructor with size input
     *
     * @param xSize
     * @param ySize
     */
    public Level(int xSize, int ySize) {
	this.xSize = xSize;
	this.ySize = ySize;
	tiles = new int[xSize][ySize];
	collisionLayer = new boolean[xSize][ySize];
	visited = new boolean[xSize][ySize];
    }

    /**
     * connect the rooms with each other
     */
    private void connectRooms() {
	Point prevCenter = null;
	Random rand = new Random();
	for (Room r : rooms) {
	    if (prevCenter == null) {
		prevCenter = r.getCenter();
		continue;
	    } else {
		Point newCenter = r.getCenter();
		if (rand.nextBoolean()) {
		    drawHorizontalCorridor((int) prevCenter.getX(), (int) newCenter.getX(), (int) prevCenter.getY());
		    drawVerticalCorridor((int) prevCenter.getY(), (int) newCenter.getY(), (int) newCenter.getX());
		} else {
		    drawVerticalCorridor((int) prevCenter.getY(), (int) newCenter.getY(), (int) newCenter.getX());
		    drawHorizontalCorridor((int) prevCenter.getX(), (int) newCenter.getX(), (int) prevCenter.getY());
		}
	    }
	}
	generateTileSet();
    }

    /**
     * Draw a single room r into the level
     *
     * @param r
     *            Room to draw into the level
     */
    private void drawRoom(Room r) {
	if (((r.getX() + r.getWidth()) < collisionLayer.length)
		|| ((r.getY() + r.getHeight()) < collisionLayer[0].length)) {
	    for (int row = r.getY(); row < (r.getY() + (r.getHeight() - r.getY())); row++) {
		for (int col = r.getX(); col < (r.getX() + (r.getWidth() - r.getX())); col++) {
		    collisionLayer[row][col] = false;
		}
	    }
	}
	generateTileSet();
    }

    /**
     *
     */
    private void drawRooms() {
	for (Room r : rooms) {
	    drawRoom(r);
	}
    }

    /**
     * Draws a wall into the level
     *
     * @param x
     * @param y
     * @param direction
     * @param length
     * @param isFloor
     *            shall the wall be a floor
     */
    public void drawWall(int x, int y, int direction, int length, boolean isFloor) {
	for (int i = 0; i < length; i++) {
	    try {
		switch (direction) {
		// north
		case 0:
		    collisionLayer[x][y - i] = isFloor;
		    break;

		// south
		case 1:
		    collisionLayer[x][y + i] = isFloor;
		    break;

		// west
		case 2:
		    collisionLayer[x - i][y] = isFloor;
		    break;

		// east
		case 3:
		    collisionLayer[x + i][y] = isFloor;
		    break;

		}
	    } catch (ArrayIndexOutOfBoundsException e) {
		continue;
	    }
	}
	generateTileSet();
    }

    /**
     * draws a horizontal corridor into the level
     *
     * @param x1
     * @param x2
     * @param y
     */
    public void drawHorizontalCorridor(int x1, int x2, int y) {
	for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
	    try {
		collisionLayer[y][i] = false;
	    } catch (ArrayIndexOutOfBoundsException e) {
		continue;
	    }
	}
	generateTileSet();
    }

    /**
     * draws a horizontal corridor into the level
     *
     * @param y1
     * @param y2
     * @param y
     */
    public void drawVerticalCorridor(int y1, int y2, int x) {
	for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
	    try {
		collisionLayer[i][x] = false;
	    } catch (ArrayIndexOutOfBoundsException e) {
		continue;
	    }
	}
	generateTileSet();
    }

    /**
     * Fills the with randomly generated walls
     *
     * @param minLength
     * @param maxLength
     * @param granularity
     * @param numWalls
     * @param isFloor
     */
    public void fillWithWalls(int minLength, int maxLength, double granularity, int numWalls, boolean isFloor) {
	Random rand = new Random();
	for (int i = 0; i < numWalls; i++) {

	    // rand.nextInt((max - min) + 1) + min;
	    // starting position
	    int x = (int) ((granularity * rand.nextInt(((int) ((xSize - 1) / granularity)) - 0)) + 1);
	    int y = (int) ((granularity * rand.nextInt(((int) ((ySize - 1) / granularity)) - 0)) + 1);
	    // random direction
	    int dir = rand.nextInt(3 - 0) + 1;
	    // random length
	    int length = (int) ((granularity * rand.nextInt((maxLength - minLength) + 1 + minLength)) + 1);
	    drawWall(x, y, dir, length, isFloor);
	}
    }

    /**
     * Surrounds the level with walls
     *
     * @param tmpLevel
     */
    public void generateOuterWalls() {
	for (int i = 0; i < collisionLayer.length; i++) {
	    collisionLayer[0][i] = true;
	    collisionLayer[collisionLayer.length - 1][i] = true;
	}
	for (int i = 0; i < collisionLayer.length; i++) {
	    collisionLayer[i][0] = true;
	    collisionLayer[i][collisionLayer.length - 1] = true;
	}
    }

    /**
     * generate tile set
     */
    private void generateTileSet() {
	for (int i = 0; i < tiles.length; i++) {
	    for (int j = 0; j < tiles[0].length; j++) {
		// @TODO: add randomness
		if (collisionLayer[i][j]) {
		    tiles[i][j] = 1;
		} else {
		    tiles[i][j] = 0;
		}
	    }
	}
    }

    /**
     *
     * @param num
     * @param intersecting
     */
    public void generateRooms(int num, boolean intersecting) {
	Room tmp;
	Random rand = new Random();
	int maxSize = Constants.MAX_ROOM_SIZE;
	for (int i = 0; i < num; i++) {
	    if (!intersecting) {
		tmp = new Room(rand.nextInt((collisionLayer.length - maxSize) - 0) + 1,
			rand.nextInt((collisionLayer[0].length - maxSize) - 0) + 1, rand.nextInt(maxSize - 1) + 1,
			rand.nextInt(maxSize - 1) + 1);
		try {
		    rooms.add(tmp);
		    for (Room r : rooms) {
			tmp.intersects(r);
			while (tmp.intersects(r)) {
			    tmp = new Room(rand.nextInt((collisionLayer.length - maxSize) - 0) + 1,
				    rand.nextInt((collisionLayer[0].length - maxSize) - 0) + 1,
				    rand.nextInt(maxSize - 1) + 1, rand.nextInt(maxSize - 1) + 1);
			}
		    }
		} catch (NullPointerException e) {
		    continue;
		}
	    } else {
		tmp = new Room(rand.nextInt((collisionLayer.length - maxSize) - 0) + 1,
			rand.nextInt((collisionLayer[0].length - maxSize) - 0) + 1, rand.nextInt(maxSize - 1) + 1,
			rand.nextInt(maxSize - 1) + 1);
		try {
		    rooms.add(tmp);
		    for (Room r : rooms) {
			tmp.intersects(r);
			while (tmp.intersects(r)) {
			    tmp = new Room(rand.nextInt((collisionLayer.length - maxSize) - 0) + 1,
				    rand.nextInt((collisionLayer[0].length - maxSize) - 0) + 1,
				    rand.nextInt(maxSize - 1) + 1, rand.nextInt(maxSize - 1) + 1);
			}
		    }
		} catch (NullPointerException e) {
		    continue;
		}
		rooms.add(tmp);
	    }
	}
	drawRooms();
    }

    /**
     * @return the tiles
     */
    public int[][] getTiles() {
	return tiles;
    }

    /**
     * get the width of the level
     *
     * @return int x size
     */
    int getxSize() {
	return xSize;
    }

    /**
     * get the height of the level
     *
     * @return int y size
     */
    public int getySize() {
	return ySize;
    }

    /**
     * @param tiles
     *            the tiles to set
     */
    public void setTiles(int[][] tiles) {
	this.tiles = tiles;
    }

}
