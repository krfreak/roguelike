package model;

import java.util.HashMap;

import model.gameObjects.Level;
import model.gameObjects.Player;

/**
 * contains every object loaded into the game
 *
 * @author Tim
 *
 */
public class Game {

    /**
     * turn count
     */
    private double turnCount;

    /**
     * @return the turnCount
     */
    public double getTurnCount() {
	return turnCount;
    }

    /**
     * @param turnCount
     *            the turnCount to set
     */
    public void setTurnCount(double turnCount) {
	this.turnCount = turnCount;
    }

    /**
     * a Player object
     */
    Player player;

    /**
     * @return the player
     */
    public Player getPlayer() {
	return player;
    }

    /**
     * @param player
     *            the player to set
     */
    public void setPlayer(Player player) {
	this.player = player;
    }

    /**
     * basic constructor
     */
    public Game() {
	addLevel("standard", Level.standardLevel());
	addLevel("random", Level.randomLevel());
	loadLevel("random");
	player = new Player((int) currentLevel.getRooms().get(0).getCenter().getX(),
		(int) currentLevel.getRooms().get(0).getCenter().getY());
    }

    /**
     * The currently loaded level
     */
    private Level currentLevel;

    /**
     * a list that contains every possible level in the game
     */
    private HashMap<String, Level> levelList = new HashMap<String, Level>();

    /**
     * @return the currentLevel
     */
    public Level getCurrentLevel() {
	return currentLevel;
    }

    /**
     * @param currentLevel
     *            the currentLevel to set
     */
    public void setCurrentLevel(Level currentLevel) {
	this.currentLevel = currentLevel;
    }

    /**
     * loads a level by its' designated name
     *
     * @param name
     *            the levels' name
     */
    public void loadLevel(String name) {
	currentLevel = levelList.get(name);
    }

    /**
     * checks whether the level to be loaded is an instance of model.Level and
     * adds it accordingly to the levelList list
     *
     * @param level
     *            an instance of Level to be added to the list
     * @return true if instance fits model.Level, else false
     */
    public boolean addLevel(String name, Level level) {
	if (level.getClass().equals(model.gameObjects.Level.class)) {
	    levelList.put(name, level);
	    return true;
	}
	return false;
    }

}
