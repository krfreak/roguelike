/**
 *
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Tim
 *
 */
public class RightSidePanel extends JPanel {

    /**
     * blakeks
     */
    private static final long serialVersionUID = 1L;

    private JLabel turnCount;

    /**
     * a label that shows the players position
     */
    private JLabel playerPosition = new JLabel("");

    /**
     * @return the playerPosition
     */
    public JLabel getPlayerPosition() {
	return playerPosition;
    }

    /**
     * @param playerPosition
     *            the playerPosition to set
     */
    protected void setPlayerPosition(JLabel playerPosition) {
	this.playerPosition = playerPosition;
    }

    /**
     * basic constructor
     */
    public RightSidePanel() {
	super();
	setLayout(null);
	playerPosition.setBounds(2, 2, 200, 25);
	playerPosition.setForeground(Color.WHITE);
	add(playerPosition);
	setVisible(true);
    }

    /**
     * paints all the components
     */
    @Override
    public void paintComponent(Graphics g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, getWidth(), getHeight());
    }

}
