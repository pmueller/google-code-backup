package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;

import util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Model;

public class View {

    //fields for the View
    private JFrame window;
    private Model model;
    private ViewPort currentView;
    private static View instance;
    private final int windowHeight = 750;
    private final int windowWidth = 1024;
    public static final int TILE_SIZE = 50;

    /**
     * method to get the instance of view
     * since it's a singleton
     * @return the only view instance
     */
    public static View getInstance() {
        if(instance == null) {
            instance = new View( Model.getInstance() );
        }
        return instance;
    }

    //private constructor, can only be called form getinstance
    private View( Model m ) {
        model = m;
        window = new JFrame("Mexica Iteration 2");
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * sets p to be the main panel of the frame
     * @param p the panel to set to the screen
     */
    public void setCurrentPane( JPanel p ) {
        window.setContentPane(p);
        window.setVisible(true);
    }

    /**
     * used for menu panels because they cant update
     * @param p sets the given p to the screen
     */
    private void showMenu( JPanel p ) {
        currentView = null;
        setCurrentPane( p );
    }

    /**
     * sets the vp to the screen because it can update
     * @param vp set the viewport to the screen
     */
    private void showGame( ViewPort vp ) {
        currentView = vp;
        setCurrentPane( vp );
    }

    /**
     * sends the map visitor to the model
     * @param v
     */
    public void sendMapVisitorToModel( MapVisitor v ) {
        model.forwardMapVisitor(v);
    }

    /**
     * sends the avatar visitor to the model
     * @param v
     */
    public void sendAvatarVisitorToModel( AvatarVisitor v ) {
        model.forwardAvatarVisitor(v);
    }

    /**
     * sends the log visitor to the model
     * @param v
     */
    public void sendLogVisitorToModel( LogVisitor v ) {
        model.forwardLogVisitor(v);
    }

    /**
     * Makes the main menu and adds the given listeners to the buttons
     * @param forNewGame ActionListener
     * @param forLoadGame ActionListener
     * @param forExit ActionListener
     */
    public void initMainMenu( ActionListener forNewGame, ActionListener forLoadGame, ActionListener forExit) {
        showMenu( new MainMenuView(forNewGame, forLoadGame, forExit) );
    }

    /**
     * makes the pause menu and adds the given listeners to the buttons
     * @param forSaveGame ActionListener
     * @param forResumeGame ActionListener
     * @param forNewGame ActionListener
     * @param forLoadGame ActionListener
     * @param forOptionMenu ActionListener
     * @param forExit ActionListener
     */
    public void initPauseMenu( ActionListener forSaveGame, ActionListener forResumeGame, ActionListener forNewGame, ActionListener forLoadGame, ActionListener forOptionMenu, ActionListener forExit) {
        showMenu( new PauseMenuView(forSaveGame, forResumeGame, forNewGame, forLoadGame, forOptionMenu, forExit) );       
    }

    /**
     * makes the createcharmenu and shows it
     * @param forCreateChar ActionListener
     * @param ccs controller.CreateCharState
     */
    public void initCreateCharMenu ( ActionListener forCreateChar, controller.CreateCharState ccs ) {
        showMenu( new CreateCharView( forCreateChar, ccs ) );
    }

    /**
     * makes the option menu and shows it
     * @param returnButton ActionListener
     * @param listeners List<ActionListener> listeners for the remap buttons
     * @param keys Integer[] keycodes of current mapping
     * @param oms controller.OptionMenuState
     */
    public void initOptionMenu ( ActionListener returnButton, List<ActionListener> listeners, Integer[] keys, controller.OptionMenuState oms) {
        showMenu( new OptionMenuView(returnButton, listeners, keys, oms) );
    }

    /**
     * make gameplay view and set it as main view
     * @param kl KeyListener - for main user input
     * @param gps controller.GamePlayState
     */
    public void initGameplay( KeyListener kl, controller.GamePlayState gps) {
        showGame( new GameView( windowWidth, windowHeight, kl , new MapView(windowHeight, windowHeight-(4*TILE_SIZE)), new StatsView(windowWidth-windowHeight, windowHeight/2, gps), new InventoryView(windowWidth-windowHeight, windowWidth/2, gps), new LogView(windowHeight, 4*TILE_SIZE) ) );
    }
    
    /**
     * update the current view if it exists
     */
    public void update() {
        if(currentView != null) {
            currentView.update();
        }
    }
    
}