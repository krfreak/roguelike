package model.gameObjects;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * a basic tile
 *
 * @author Tim
 *
 */
public class Tile {

    /**
     * file
     */
    private File file;
    /**
     * image
     */
    private Image img;

    public Tile(String fileName) {
	file = new File(fileName);
	try {
	    img = ImageIO.read(file);
	} catch (IOException e) {
	    System.err.println("FileNotFoundException for " + fileName);
	}
    }

    /**
     * @return the file
     */
    public File getFile() {
	return file;
    }

    /**
     * @return the img
     */
    public Image getImg() {
	return img;
    }

    /**
     * @param tile
     *            the file to set
     */
    public void setFile(File tile) {
	file = tile;
    }

    /**
     * @param img
     *            the img to set
     */
    public void setImg(Image img) {
	this.img = img;
    }

}
