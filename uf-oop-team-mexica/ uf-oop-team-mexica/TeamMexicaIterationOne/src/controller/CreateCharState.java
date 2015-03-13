package controller;

final class CreateCharState extends ControllerState {
	/*
	 * CreateCharState Attaches and handles the action listeners for the create
	 * character menu screen
	 */
	protected CreateCharState(Controller c) {
		setOwner(c);
		init();
	}

	/**
	 * Attach appropriate listener to view's buttons
	 */
	protected void init() {
		final view.CreateCharView ccv = getOwner().getView().initCreateChar();
		ccv.addCreateCharListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				createChar(ccv.getCharName(), ccv.getSelectedOccupation());
			}
		});
	}

	/**
	 * Handle character creation
	 * 
	 * @param name
	 *            the name of the character
	 * @param profession
	 *            the occupation
	 */
	private void createChar(String name, String profession) {
		getOwner().getModel().newGame(name, profession);
		stateTransition(new GamePlayState(getOwner()));
	}
}
