package model.gameObjects;

/**
 * basic entity, needs coordinates
 *
 * @author Tim
 *
 */
public abstract class Entity {

    /**
     * x coordinate on the field
     */
    private int xPos;
    /**
     * y coordinate on the field
     */
    private int yPos;

    /**
     * string to draw
     */
    private char entityChar;

    /**
     * empty constructor
     */
    public Entity() {
    }

    /**
     * set the position of the entity
     *
     * @param x
     *            xpos
     * @param y
     *            ypos
     */
    public void setPosition(int x, int y) {
	if ((x >= 0) && (y >= 0)) {
	    setxPos(x);
	    setyPos(y);
	} else {
		System.err.println("setPostion(): invalid position");
	    }
    }

    /**
     * get the entityChar
     *
     * @return the entityChar
     */
    public char getEntityChar() {
	return entityChar;
    }

    /**
     * get the x coordinate of the entity
     *
     * @return int x position
     */
    public int getxPos() {
	return xPos;
    }

    /**
     * get the y coordinate of the entity
     *
     * @return int y position
     */
    public int getyPos() {
	return yPos;
    }

    /**
     * set the entityChar
     *
     * @param entityChar
     *            the entityChar to set
     */
    public void setEntityChar(char entityChar) {
	this.entityChar = entityChar;
    }

    /**
     * set the x coordinate of the entity
     *
     * @param xPos
     *            integer of x coordinate of the entity
     */
    public void setxPos(int xPos) {
	this.xPos = xPos;
    }

    /**
     * set the y coordinate of the entity
     *
     * @param yPos
     *            integer of y coordinate of the entity
     */
    public void setyPos(int yPos) {
	this.yPos = yPos;
    }

}
