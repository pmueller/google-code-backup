package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OptionMenuState extends ControllerState {

    private view.OptionMenuView myView;

    /**
     * Constructor: calls init
     */
    public OptionMenuState() {
        init();
    }

    /**
     * Initializes the action listeners
     */
    protected void init() {

        ActionListener backButton = new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                backButton();
            }
        };

        List<ActionListener> buttonListeners = new ArrayList<ActionListener>();
        for (int i = 0; i != 20; ++i) {
            final int j = i;
            buttonListeners.add(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    KeyMap k = Controller.getInstance().getKeyMap();
                    k.replaceKeyCode(j, k.getKeyCodes()[j], myView.getKeyCodeAt(j));
                }
            });
        }
        Integer[] codes = Controller.getInstance().getKeyMap().getKeyCodes();

        view.View.getInstance().initOptionMenu(backButton, buttonListeners, codes, this);
    }

    /**
     * Sets the view
     * @param v
     */
    public void setView(view.OptionMenuView v) {
        myView = v;
    }

    /**
     * Transitions to the pause menu state
     */
    private void backButton() {
        stateTransition(new PauseMenuState());
    }
}
