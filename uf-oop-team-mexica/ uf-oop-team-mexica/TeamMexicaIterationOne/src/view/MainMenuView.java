/**
 * Team Mexica Iteration 1
 * The MainMenuView class defines the JPanel displaying the main menu
 * screen. It also provides an interface for its controller to add action
 * listeners to appropriate buttons.
 * 
 */

package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public class MainMenuView extends JPanel implements ViewPort {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6453845063790031893L;
	
	// components to be added
	private JButton btnNewGame;
	private JButton btnLoadGame;
	private JButton btnExit;

	// height and width of all buttons added
	private int buttonWidth = 200;
	private int buttonHeight = 35;

	/**
	 * Constructor. Set up the layout, add the components for this panel.
	 */
	public MainMenuView() {
		// set to null layout for exact control
		this.setLayout(null);
		// set to menu size
		this.setSize(View.MENU_WIDTH, View.MENU_HEIGHT);

		// Create and add the 3 menu buttons with respective titles and
		// locations
		btnNewGame = new JButton("New Game");
		btnLoadGame = new JButton("Load Game");
		btnExit = new JButton("Exit");
		this.add(btnNewGame);
		btnNewGame.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 - 85, buttonWidth, buttonHeight);
		this.add(btnLoadGame);
		btnLoadGame.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 - 35, buttonWidth, buttonHeight);
		this.add(btnExit);
		btnExit.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 + 15, buttonWidth, buttonHeight);

	}

	/**
	 * Adds the given listener to the New Game choice
	 * 
	 * @param l
	 *            Listener to be added to New Game choice
	 */
	public void addNewGameListener(ActionListener l) {
		btnNewGame.addActionListener(l);
	}

	/**
	 * Adds the given listener to the Load Game choice
	 * 
	 * @param l
	 *            Listener to be added to Load Game choice
	 */
	public void addLoadGameListener(ActionListener l) {
		btnLoadGame.addActionListener(l);
	}

	/**
	 * Adds the given listener to the Exit choice
	 * 
	 * @param l
	 *            Listener to be added to Exit choice
	 */
	public void addExitListener(ActionListener l) {
		btnExit.addActionListener(l);
	}

	/**
	 * null implementation in this class
	 */
	public void update() {
	};

}
