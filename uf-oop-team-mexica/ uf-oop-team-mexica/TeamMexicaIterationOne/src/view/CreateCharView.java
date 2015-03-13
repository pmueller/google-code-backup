/**
 * Team Mexica Iteration 1
 * 
 * The CreateCharView class defines the JPanel displaying the character
 * creation menu screen. It also provides an interface for its controller
 * to add action listeners to appropriate buttons, radio buttons and text entry fields.
 * 
 */

package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;

public class CreateCharView extends JPanel implements ViewPort {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8145152948717099553L;
	
	// components that will be added to this panel
	private JTextField txtCharName;
	private ButtonGroup bgOccupation;
	private JRadioButton rdoSmasher;
	private JRadioButton rdoSneak;
	private JRadioButton rdoSummoner;
	private JButton btnCreateChar;
	private JLabel lblCharName;

	// height of all components in this panel
	private int componentHeight = 35;
	// width of all components in this panel except the charName label
	private int componentWidth = 200;
	// width of the charName label
	private int labelWidth = 100;

	/**
	 * Constructor. Sets up the layout, adds all the necessary components. Sets
	 * Smasher to the default selected Occupation
	 */
	public CreateCharView() {
		// set layout to null for exact control
		this.setLayout(null);
		// set the panel to menu size
		this.setSize(View.MENU_WIDTH, View.MENU_HEIGHT);

		// create and add the label "Character Name"
		lblCharName = new JLabel("Character Name");
		this.add(lblCharName);
		lblCharName.setBounds(
				(View.MENU_WIDTH - (labelWidth + componentWidth)) / 2,
				View.MENU_HEIGHT / 2 - 135, labelWidth, componentHeight);

		// create and add the textfield for the character name
		txtCharName = new JTextField();
		this.add(txtCharName);
		txtCharName.setBounds((View.MENU_WIDTH - (labelWidth + componentWidth))
				/ 2 + labelWidth, View.MENU_HEIGHT / 2 - 135, componentWidth,
				componentHeight);

		// create a buttongroup to add out radio buttons to
		bgOccupation = new ButtonGroup();

		// smasher radio button
		// create and add to the button group
		rdoSmasher = new JRadioButton();
		rdoSmasher.setSelected(true); // start smasher (top button) as selected
		rdoSmasher.setText("Smasher");
		rdoSmasher.setToolTipText("Smashers hit things");
		bgOccupation.add(rdoSmasher);
		this.add(rdoSmasher);
		rdoSmasher.setBounds((View.MENU_WIDTH - componentWidth) / 2,
				View.MENU_HEIGHT / 2 - 85, componentWidth, componentHeight);

		// summoner radio button
		// create and add to the button group
		rdoSummoner = new JRadioButton();
		bgOccupation.add(rdoSummoner);
		rdoSummoner.setText("Summoner");
		rdoSummoner.setToolTipText("Sumoners magic things to death");
		this.add(rdoSummoner);
		rdoSummoner.setBounds((View.MENU_WIDTH - componentWidth) / 2,
				View.MENU_HEIGHT / 2 - 35, componentWidth, componentHeight);

		// sneak radio button
		// create and add to the button group
		rdoSneak = new JRadioButton();
		bgOccupation.add(rdoSneak);
		rdoSneak.setText("Sneak");
		rdoSneak.setToolTipText("Sneaks shoot things, and are very very sneaky");
		this.add(rdoSneak);
		rdoSneak.setBounds((View.MENU_WIDTH - componentWidth) / 2,
				View.MENU_HEIGHT / 2 + 15, componentWidth, componentHeight);

		// create and add the button to start the game
		btnCreateChar = new JButton("Begin Game");
		this.add(btnCreateChar);
		btnCreateChar.setBounds((View.MENU_WIDTH - componentWidth) / 2,
				View.MENU_HEIGHT / 2 + 65, componentWidth, componentHeight);
	}

	/**
	 * returns name of the character input to the text area
	 * 
	 * @return name of the character
	 */
	public String getCharName() {
		return txtCharName.getText();
	}

	/**
	 * Returns the selected occupation name
	 * 
	 * @return the name of the selected Occupation
	 */
	public String getSelectedOccupation() {
		// return the text of the selected radio button (i.e., the occupation
		// name)
		if (rdoSmasher.isSelected()) {
			return rdoSmasher.getText();
		} else if (rdoSneak.isSelected()) {
			return rdoSneak.getText();
		} else if (rdoSummoner.isSelected()) {
			return rdoSummoner.getText();
		} else {
			throw new RuntimeException(
					"No Occupation Selected on CreateCharView");
		}
	}

	/**
	 * Adds the given actionlistener onto the button that starts the game
	 * 
	 * @param l
	 *            ActionListener to add
	 */
	public void addCreateCharListener(ActionListener l) {
		btnCreateChar.addActionListener(l);
	}

	/**
	 * no implementation in this class
	 */
	public void update() {
	};
}
