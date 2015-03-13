package controller;

abstract class ControllerState {

    /**
     * the ControllerState to transition to
     * @param cs
     */
    protected void stateTransition(ControllerState cs) {
        Controller.getInstance().setCurrentState(cs);
    }

    /**
     * this method will attach appropriate listeners to views
     */
    abstract protected void init();
}
