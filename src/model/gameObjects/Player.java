package model.gameObjects;

import model.Constants;

/**
 * Player object, for Entity Reference see {@link model.gameObjects.Entity}
 *
 * @author Tim
 *
 */
public class Player extends Entity {

    /**
     * the image to load
     */
    private Tile tile;

    /**
     * @return the tile
     */
    public Tile getTile() {
	return tile;
    }

    /**
     * @param tile
     *            the tile to set
     */
    public void setTile(Tile tile) {
	this.tile = tile;
    }

    /**
     * basic constructor
     */
    public Player(int x, int y) {
	tile = new Tile(Constants.PLAYER_IMAGE);
	setPosition(x, y);
    }

}
