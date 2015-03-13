package controller;

abstract class ControllerState {
	private Controller owner;

	/**
	 * sets the owner
	 * 
	 * @param c
	 *            the owner
	 */
	public void setOwner(Controller c) {
		owner = c;
	}

	/**
	 * 
	 * @return the owner
	 */
	public Controller getOwner() {
		return owner;
	}

	/**
	 * defines state change behavior
	 * 
	 * @param cs
	 *            the ControllerState to transition to
	 */
	protected void stateTransition(ControllerState cs) {
		owner.setCurrentState(cs);
	}

	/**
	 * does nothing in this class
	 */
	void update() {
	}

	/**
	 * this method will attach appropriate listeners to views
	 */
	abstract protected void init();
}