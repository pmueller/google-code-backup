package controller;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Model;
import util.*;

public class MainMenuState extends ControllerState {

    /**
     * Constructor: calls init
     */
    public MainMenuState() {
        init();
    }

    /**
     * Attach Action Listeners to the appropriate buttons in the view
     */
    protected void init() {

        ActionListener newgame = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                newGame();
            }
        };

        ActionListener load = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                loadGame();
            }
        };

        ActionListener exit = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                exitGame();
            }
        };

        view.View.getInstance().initMainMenu(newgame, load, exit);
    }

// operations to handle the behavior of each button in the view
    /**
     * creates a new game by transitioning to the CreateCharState
     */
    private void newGame() {
        stateTransition(new CreateCharState());
    }

    /**
     * load a game from a save
     */
    private void loadGame() {
        //bring up a file browser to choose a save file, then load that
        //then switch to gameplaystate

        boolean loadSuccess = true;
        try {

            Scanner s = new Scanner(new File("saveFiles/save.dat"));
            SaverLoader sl = new SaverLoader(s);
            Model.getInstance().loadState(sl);

        } catch (IOException e) {
            loadSuccess = false;
            System.out.print(e);
            System.out.print(e.getMessage());
            JOptionPane.showMessageDialog(null, "Loading failed: " + e.getMessage(), "Loading Error", JOptionPane.ERROR_MESSAGE);

        }
        stateTransition(new GamePlayState());

    }

    /**
     * exits the game by calling System.exit(0)
     */
    private void exitGame() {
        if (javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?") == 0) {
            System.exit(0);
        }
    }
}
