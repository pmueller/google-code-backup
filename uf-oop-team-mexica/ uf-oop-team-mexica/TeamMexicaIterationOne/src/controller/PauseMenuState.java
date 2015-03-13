package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

final class PauseMenuState extends ControllerState {

	/**
	 * Constructor
	 * 
	 * @param c
	 *            the controller
	 */
	public PauseMenuState(Controller c) {
		setOwner(c);
		init();
	}

	/**
	 * initializes the pause menu view, and sets up action listeners
	 */
	protected void init() {
		view.PauseMenuView pmv = getOwner().getView().initPauseMenu();
		getOwner().getModel().isPaused(true);

		pmv.addSaveGameListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				saveGame();
			}
		});
		pmv.addResumeGameListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				resumeGame();
			}
		});
		pmv.addNewGameListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				newGame();
			}
		});
		pmv.addLoadGameListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				loadGame();
			}
		});
		pmv.addExitListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				exitGame();
			}
		});
	}

	// Operations to handle the behavior of each button in the view
	/**
	 * resumes the game by transitioning the controller to the GamePlayState
	 */
	private void resumeGame() {
		getOwner().getModel().isPaused(false);
		stateTransition(new GamePlayState(getOwner()));
	}

	/**
	 * calls System.exit(0) to exit the game
	 */
	private void exitGame() {
		if (JOptionPane
				.showConfirmDialog(null,
						"Are you sure you want to exit the game? \n All unsaved progress will be lost.") == 0)
			System.exit(0);
	}

	/**
	 * creates a new game by transitioning to the CreateCharState state
	 */
	private void newGame() {
		if (JOptionPane
				.showConfirmDialog(null,
						"Are you sure you want to start a new game? \n All progress will be lost.") == 0) {
			getOwner().getModel().isPaused(false);
			stateTransition(new CreateCharState(getOwner()));
		}
	}

	/**
	 * saves the game to DefaultSaveGame.dat
	 */
	private void saveGame() {
		if (JOptionPane.showConfirmDialog(null,
				"Are you sure you want to overwrite your saved game?") == 0)
			getOwner().getModel().saveGame("Assets/DefaultSaveGame.dat");
	}

	/**
	 * loads the game from DefaultSaveGame.dat
	 */
	private void loadGame() {
		if (JOptionPane
				.showConfirmDialog(
						null,
						"Loading will overwrite all current progress... are you sure you want to continue?") == 0) {
			getOwner().getModel().loadGame("Assets/DefaultSaveGame.dat");
			getOwner().getModel().isPaused(false);
			stateTransition(new GamePlayState(getOwner()));
		}
	}

}
