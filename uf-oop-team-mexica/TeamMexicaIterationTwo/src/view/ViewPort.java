package view;

import javax.swing.JPanel;

public abstract class ViewPort extends JPanel {

    /**
     * defines how the component updates with
     * respect to the game
     */
    public abstract void update();
    
}
