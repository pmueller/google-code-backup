package controller;

public class CreateCharState extends ControllerState {

    private view.CreateCharView ccv;

    /**
     * Sets the create character view
     * @param v
     */
    public void setView(view.CreateCharView v) {
        ccv = v;
    }

    /**
     * CreateCharState Attaches and handles the action listeners for the create
     * character menu screen
     */
    protected CreateCharState() {
        init();
    }

    /**
     * Attach appropriate listener to view's buttons
     */
    protected void init() {
        java.awt.event.ActionListener create = new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent ae) {
                createChar(ccv.getCharName(), ccv.getSelectedOccupation());
            }
        };
        view.View.getInstance().initCreateCharMenu(create, this);

    }

    /**
     * Handle character creation
     * @param name
     * @param profession
     */
    private void createChar(String name, String profession) {
        model.Model.getInstance().newGame(name, profession);
        stateTransition(new GamePlayState());
    }
}
