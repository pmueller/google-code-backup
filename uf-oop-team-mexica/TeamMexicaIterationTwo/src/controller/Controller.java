package controller;

/**
 * The Controller Class is a singleton which provides a single point of access to the controller subsystem.
 */
public class Controller {

    private static Controller instance = null;
    private ControllerState currentState;
    private KeyMap keymap;

    /**
     * Constructor
     */
    private Controller() {
        setCurrentState(new MainMenuState());
        keymap = new KeyMap();
    }

    /**
     *
     * @return the singleton instance
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Sets the current ControllerState
     * @param s
     */
    void setCurrentState(ControllerState s) {
        currentState = s;
    }

    /**
     *
     * @return the current KeyMap
     */
    public KeyMap getKeyMap() {
        return keymap;
    }
}
