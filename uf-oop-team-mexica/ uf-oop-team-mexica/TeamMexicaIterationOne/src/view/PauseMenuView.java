/**
 * Team Mexica Iteration 1
 * 
 * The PauseMenuView class defines the JPanel displaying the pause menu screen.
 * It also provides an interface for its controller to add action listeners to
 * appropriate buttons.
 * 
 */

package view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenuView extends JPanel implements ViewPort {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8895814313839252598L;
	
	// component to add to this panel
	private JButton btnSaveGame;
	private JButton btnResumeGame;
	private JButton btnNewGame;
	private JButton btnLoadGame;
	private JButton btnExit;

	// width and height of all buttons on this panel
	private int buttonWidth = 200;
	private int buttonHeight = 35;

	/**
	 * Constructor. Sets up layout, creates and adds all buttons of the menu
	 */
	public PauseMenuView() {
		this.setLayout(null);
		this.setSize(View.MENU_WIDTH, View.MENU_HEIGHT);
		// create the buttons
		btnSaveGame = new JButton("Save Game");
		btnResumeGame = new JButton("Resume Game");
		btnNewGame = new JButton("New Game");
		btnLoadGame = new JButton("Load Game");
		btnExit = new JButton("Exit");
		// add all the buttons
		this.add(btnSaveGame);
		btnSaveGame.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 - 135, buttonWidth, buttonHeight);
		this.add(btnResumeGame);
		btnResumeGame.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 - 85, buttonWidth, buttonHeight);
		this.add(btnNewGame);
		btnNewGame.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 - 35, buttonWidth, buttonHeight);
		this.add(btnLoadGame);
		btnLoadGame.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 + 15, buttonWidth, buttonHeight);
		this.add(btnExit);
		btnExit.setBounds((View.MENU_WIDTH - buttonWidth) / 2,
				View.MENU_HEIGHT / 2 + 65, buttonWidth, buttonHeight);
	}

	/**
	 * Adds the given listener to the saveGame option in the menu
	 * 
	 * @param l
	 *            listener to add to save game
	 */
	public void addSaveGameListener(ActionListener l) {
		btnSaveGame.addActionListener(l);
	}

	/**
	 * Adds the given listener to the resumeGame option in the menu
	 * 
	 * @param l
	 *            listener to add to resume game
	 */
	public void addResumeGameListener(ActionListener l) {
		btnResumeGame.addActionListener(l);
	}

	/**
	 * Adds the given listener to the newGame option in the menu
	 * 
	 * @param l
	 *            listener to add to new game
	 */
	public void addNewGameListener(ActionListener l) {
		btnNewGame.addActionListener(l);
	}

	/**
	 * Adds the given listener to the loadGame option in the menu
	 * 
	 * @param l
	 *            listener to add to load game
	 */
	public void addLoadGameListener(ActionListener l) {
		btnLoadGame.addActionListener(l);
	}

	/**
	 * Adds the given listener to the exit option in the menu
	 * 
	 * @param l
	 *            listener to add to exit
	 */
	public void addExitListener(ActionListener l) {
		btnExit.addActionListener(l);
	}

	/**
	 * null implementation in this menu
	 */
	public void update() {
	};

}