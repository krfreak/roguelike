package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import model.Constants;
import presenter.Presenter;

/**
 * basic window that works as a controller to its child panes
 *
 * @author Tim
 *
 */
public class View extends JFrame {

    /**
     * a panel canvas
     */
    private PanelCanvas canvas;

    /**
     * a presenter to fulfill the mvp pattern
     */
    private Presenter presenter;

    /**
     * a panel on the right side to show some information about the player
     */
    private RightSidePanel rightSidePanel;

    /**
     * a JTextArea to show the turn log
     */
    private JTextArea textLogArea;

    /**
     * scroll pane to make the textArea scrollable
     */
    private JScrollPane textLogAreaScrollPane;

    /**
     * blakeks
     */
    private static final long serialVersionUID = 1L;

    /**
     * screen size
     */
    Dimension screenSize = new Dimension(1000, 800);

    /**
     * basic constructor
     */
    public View() {
	super("The epic roguelike");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(screenSize);
	setPreferredSize(screenSize);
	setLayout(null);
	setResizable(false);
	setLocationRelativeTo(null);

	canvas = new PanelCanvas(screenSize, this);
	canvas.setBounds(0, -1, Constants.canvasWidth, Constants.canvasHeight);
	canvas.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	add(canvas);

	textLogArea = new JTextArea();
	textLogArea.setBounds(1, 550, 750, 250);
	textLogArea.setBackground(Color.BLACK);
	textLogArea.setForeground(Color.WHITE);
	textLogArea.setLineWrap(true);
	textLogArea.setWrapStyleWord(true);
	textLogArea.setEditable(false);
	textLogArea.setFocusable(false);

	textLogAreaScrollPane = new JScrollPane(textLogArea);
	textLogAreaScrollPane.setBounds(1, 550, 750, 222);
	textLogAreaScrollPane.setAutoscrolls(true);
	textLogAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	add(textLogAreaScrollPane);

	rightSidePanel = new RightSidePanel();
	rightSidePanel.setBounds(751, 0, 249, 1000);
	add(rightSidePanel);

	addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent arg0) {
		try {
		    presenter.getKeyDispatcher().get(arg0.getKeyCode()).doAction();
		} catch (NullPointerException e) {
		    // do nothing
		}
	    }
	});
	setBackground(Color.BLACK);
	setVisible(true);
    }

    /**
     * returns the panel canvas
     *
     * @return
     */
    public PanelCanvas getPanelCanvas() {
	return canvas;
    }

    /**
     * set the presenter object
     *
     * @param pres
     *            the presenter
     */
    public void setPresenter(Presenter pres) {
	presenter = pres;
    }

    /**
     * appends text to the textLogArea
     *
     * @param some
     *            text
     */
    public void appendTextToTextArea(String text) {
	textLogArea.append(text);
    }

    /**
     * updates the text on the ülayerPosition label
     *
     * @param s
     */
    public void updateRightSidePanelPlayerPositionLabel(String s) {
	rightSidePanel.getPlayerPosition().setText(s);
    }

    /**
     * repaints the PanelCanvas
     */
    public void updateCanvas() {
	canvas.repaint();
    }

}
