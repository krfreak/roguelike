package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import model.Constants;

/**
 * a canvas to draw things to the screen
 *
 * @author Tim
 *
 */
public class PanelCanvas extends JPanel {

    /**
     * blakeks
     */
    private static final long serialVersionUID = 1L;

    /**
     * parent frame for accessability
     */
    private View parent;

    /**
     * image array of the showable squares on the screen
     */
    private Image[][] screenImages;

    /**
     * Get the screenImages 2D-Array
     *
     * @return the screenImages
     */
    public Image[][] getScreenImages() {
	return screenImages;
    }

    /**
     * Set the screenImages 2D-Array
     *
     * @param screenImages
     *            the screenImages to set
     */
    public void setScreenImages(Image[][] screenImages) {
	this.screenImages = screenImages;
    }

    /**
     * Set an Image onto position [x,y] in the array
     *
     * @param x
     *            position
     * @param y
     *            position
     * @param img
     *            to draw
     */
    protected void setScreenImageAt(int x, int y, Image img) {
	screenImages[x][y] = img;
    }

    /**
     * should the grid be drawn?
     */
    private boolean gridDrawn = false;

    /**
     * size of the canvas as dimension
     */
    private Dimension dim;

    /**
     * basic constructor
     */
    public PanelCanvas(Dimension dim, View parent) {
	this.dim = dim;
	this.parent = parent;
	setSize(dim);
	screenImages = new Image[Constants.SQUARES_X][Constants.SQUARES_Y];
	for (int row = 0; row < screenImages.length; row++) {
	    for (int col = 0; col < screenImages[row].length; col++) {
		screenImages[row][col] = null;
	    }
	}
    }

    /**
     * draws the player
     */
    private void drawTiles(Graphics g) {
	for (int row = 0; row < screenImages.length; row++) {
	    for (int col = 0; col < screenImages[row].length; col++) {
		if (screenImages[row][col] != null) {
		    g.drawImage(screenImages[row][col], row * Constants.TILE_SIZE, col * Constants.TILE_SIZE, null);
		} else {
		    g.setColor(Color.BLACK);
		    g.fillRect(row * Constants.TILE_SIZE, col * Constants.TILE_SIZE, Constants.TILE_SIZE,
			    Constants.TILE_SIZE);
		    // g.setColor(Color.BLUE);
		    // g.drawString("empty", (row * Constants.TILE_SIZE) + 9,
		    // (col * Constants.TILE_SIZE) + 24);
		}
	    }
	}
    }

    /**
     * draws the grid
     */
    private void drawGrid(Graphics g) {
	g.setColor(Color.RED);
	int squareSize = Constants.TILE_SIZE;
	int maxX = (squareSize * Constants.SQUARES_Y);
	int maxY = (squareSize * Constants.SQUARES_X);
	// draw horizontal lines
	for (int i = squareSize; i <= maxX; i += squareSize) {
	    g.drawLine(0, i, maxY, i);
	}
	// draw vertical lines
	for (int i = squareSize; i <= maxY; i += squareSize) {
	    g.drawLine(i, 0, i, maxX);
	}
    }

    /**
     * @return the showGrid
     */
    public boolean isGridDrawn() {
	return gridDrawn;
    }

    /**
     * @param showGrid
     *            the showGrid to set
     */
    public void setGridDrawn(boolean showGrid) {
	gridDrawn = showGrid;
    }

    /**
     * paint all the things in the universe! paints every object it can find
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
	// draw the background black
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, dim.width, dim.height);

	drawTiles(g);

	if (gridDrawn) {
	    drawGrid(g);
	}

    }

}
