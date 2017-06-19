package model.gameObjects;

import java.awt.Point;

public class Room {

    /**
     * first x position
     */
    private int x1;
    /**
     * first y position
     */
    private int y1;

    /**
     * second x position
     */
    private int x2;

    /**
     * second y position
     */
    private int y2;

    /**
     * center of the room
     */
    private Point center;

    @Override
    public String toString() {
	return "x1=" + x1 + " -> x2=" + x2 + ", y1=" + y1 + " -> y2=" + y2 + ", middle:" + center.toString();
    }

    /**
     * @return the center
     */
    public Point getCenter() {
	return center;
    }

    /**
     * basic constructor
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Room(int x, int y, int width, int height) {
	x1 = x;
	y1 = y;
	x2 = x + width;
	y2 = y + height;
	center = new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    /**
     * Checks whether this room collides with another room
     *
     * @param room
     * @return true if intersection occurs
     */
    public boolean intersects(Room room) {
	return ((x1 <= room.x2) && (x2 >= room.x1) && (y1 <= room.y2) && (room.y2 >= room.y1));
    }

    /**
     * @return the height
     */
    public int getHeight() {
	return y2;
    }

    /**
     * @return the width
     */
    public int getWidth() {
	return x2;
    }

    /**
     * @return the x
     */
    public int getX() {
	return x1;
    }

    /**
     * @return the y
     */
    public int getY() {
	return y1;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(int height) {
	y2 = height;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(int width) {
	x2 = width;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX(int x) {
	x1 = x;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY(int y) {
	y1 = y;
    }

}
