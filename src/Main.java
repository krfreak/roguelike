import javax.swing.SwingUtilities;

import model.Game;
import presenter.Presenter;
import view.View;

/**
 * contains only the main method
 *
 * @author Tim
 *
 */
public class Main {

    /**
     * where it all begins~~~~~~
     * 
     * @param args
     */
    public static void main(String[] args) {
	SwingUtilities.invokeLater(() -> {
	    View view = new View();
	    view.setPresenter(new Presenter(view, new Game()));
	});
    }

}
