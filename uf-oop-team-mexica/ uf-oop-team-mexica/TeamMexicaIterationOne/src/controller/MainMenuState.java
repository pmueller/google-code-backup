package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class MainMenuState extends ControllerState {
	/**
	 * Constructor
	 * 
	 * @param c
	 *            the controller
	 */
	public MainMenuState(Controller c) {
		setOwner(c);
		init();
	}

	/**
	 * Attach Action Listeners to the appropriate buttons in the view
	 */
	protected void init() {

		view.MainMenuView m = getOwner().getView().initMainMenu();
		m.addNewGameListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				newGame();
			}
		});
		m.addLoadGameListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				loadGame();
			}
		});
		m.addExitListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				exitGame();
			}
		});
	}

	// operation to handle the behavior of each button in the view
	/**
	 * creates a new game by transitioning to the CreateCharState
	 */
	private void newGame() {
		stateTransition(new CreateCharState(getOwner()));
	}

	/**
	 * loads the game from DefaultSaveGame.dat
	 */
	private void loadGame() {
		getOwner().getModel().loadGame("Assets/DefaultSaveGame.dat");
		stateTransition(new GamePlayState(getOwner()));
	}

	/**
	 * exits the game by calling System.exit(0)
	 */
	private void exitGame() {
		if (javax.swing.JOptionPane.showConfirmDialog(null,
				"Are you sure you want to exit the game?") == 0)
			System.exit(0);
	}
}
