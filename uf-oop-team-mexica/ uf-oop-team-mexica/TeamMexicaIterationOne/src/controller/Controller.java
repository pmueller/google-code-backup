package controller;

public class Controller {
	protected ControllerState currentState;
	private view.View view;
	private model.Model model;

	// CONSTRUCTOR
	/**
	 * @param m
	 *            the model
	 * @param v
	 *            the view
	 */
	public Controller(model.Model m, view.View v) {
		this.view = v;
		this.model = m;
		this.setCurrentState(new MainMenuState(this));
	}

	/**
	 * Sets the current ControllerState
	 * 
	 * @param s
	 *            the new ControllerState to set
	 */
	void setCurrentState(ControllerState s) {
		currentState = s;
	}

	/**
	 * passes this call to the current ControllerState
	 */
	public void update() {
		currentState.update();
	}

	/**
	 * 
	 * @return the view
	 */
	view.View getView() {
		return view;
	}

	/**
	 * 
	 * @return the model
	 */
	model.Model getModel() {
		return model;
	}
}