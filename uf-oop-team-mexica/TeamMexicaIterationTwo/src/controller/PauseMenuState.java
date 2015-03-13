package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import model.Model;
import util.SaverLoader;

public class PauseMenuState extends ControllerState {

    /**
     * init method for the PauseMenuState
     */
    PauseMenuState() {
        Model.getInstance().setPaused(true);
        init();
    }

    protected void init() {
        ActionListener forSaveGame = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                saveGame();
            }
        };

        ActionListener forResumeGame = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                resumeGame();
            }
        };

        ActionListener forNewGame = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                newGame();
            }
        };

        ActionListener forLoadGame = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                loadGame();
            }
        };

        ActionListener forOptionMenu = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                getOptions();
            }
        };

        ActionListener forExit = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                exitGame();
            }
        };

        view.View.getInstance().initPauseMenu(forSaveGame, forResumeGame, forNewGame, forLoadGame, forOptionMenu, forExit);
    }

    /**
     * opens a file browser to select where to save the game
     */
    private void saveGame() {
        try {
            SaverLoader sl = new SaverLoader(new BufferedWriter(new FileWriter("saveFiles/save.dat")));

            Model.getInstance().saveState(sl);
            sl.closeBufferedWriter();
            JOptionPane.showMessageDialog(null, "Saving Sucessful.", "Saving Success", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Saving failed: " + e.getMessage(), "Saving Error", JOptionPane.ERROR_MESSAGE);
        }
        //methods for opening a file browser and calling the save operation from the
        //util package
    }

    /**
     * resumes the game by switching to the gameplaystate
     */
    private void resumeGame() {
        model.Model.getInstance().setPaused(false);
        stateTransition(new GamePlayState());
    }

    /**
     * make a new game and end the current one
     */
    private void newGame() {
        if (JOptionPane.showConfirmDialog(null,
                "Are you sure you want to start a new game? \n All progress will be lost.") == 0) {
            model.Model.getInstance().setPaused(true);
            stateTransition(new CreateCharState());
        }
    }

    /**
     * load a game from a save
     */
    private void loadGame() {
        //bring up a file browser to choose a save file, then load that
        //then switch to gameplaystate
        if (JOptionPane.showConfirmDialog(null,
                "Loading will overwrite all current progress... are you sure you want to continue?") == 0) {
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
    }

    /**
     * bring up the options menu to change options
     */
    private void getOptions() {
        stateTransition(new OptionMenuState());
    }

    /**
     * exit the game and end the program
     */
    private void exitGame() {
        if (JOptionPane.showConfirmDialog(null,
                "Exiting will lose all unsaved progress... are you sure you want to continue?") == 0) {
            stateTransition(new MainMenuState());
            //bring up a dialogue asking the user to confirm quitting the game
            //then exit on positive response
        }

    }
}
