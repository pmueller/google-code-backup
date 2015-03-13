/**
 * Team Mexica Iteration 1
 * 
 * A panel that will display the Avatar’s stats (health, strength,
 * offense, etc), inventory, and weapon/armor slots (with equipped
 * items, if any).
 * 
 */

package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InventoryStatsView extends JPanel implements ViewPort {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1301782155677433195L;

	// vertical padding between each item in the view
	private int y_spacing = 0;

	// height and width for the components added to the panel
	private int componentHeight = 20;
	private int componentWidth = 200;

	private View view;

	// components to be added
	private JLabel[] stats;
	private JLabel equippedWeapon;
	private JLabel equippedArmor;
	private JLabel[] inventory;
	private JLabel invLabel;
	private JLabel equipLabel;
	private JLabel statsLabel;

	/**
	 * Constructor. Sets up the layout, adds the components with current values
	 * from the model.
	 * 
	 * @param view
	 *            a reference to the View
	 */
	public InventoryStatsView(View view) {
		this.view = view;

		// set layout to null, for absolute powerrrrrr
		this.setLayout(null);
		this.setSize(View.GAME_WIDTH - View.GAME_HEIGHT, View.GAME_HEIGHT);

		// initialize all the stats
		// get the names and respective values for all the stats from the model
		String[] statNames = view.getModel().getAvatarStatNames();
		int[] statValues = view.getModel().getAvatarStats();

		stats = new JLabel[statNames.length];

		// current space from the top of panel
		int y_offset = 5;
		// current space from the left-most of the panel
		int x_offset = 10;

		// create and add generic label so the user knows what they're looking
		// at.
		statsLabel = new JLabel("Stats:");
		this.add(statsLabel);
		statsLabel.setBounds(x_offset, y_offset, componentWidth,
				componentHeight);
		// increase the vertical offset by the height + padding
		y_offset += (componentHeight + y_spacing);

		// for each stat, make a new label that displays the name followed by
		// the value
		// add to the panel, set it in the right place and then increase the
		// veritcal offset
		for (int i = 0; i < statNames.length; ++i) {
			stats[i] = new JLabel(statNames[i] + ": "
					+ String.valueOf(statValues[i]));
			this.add(stats[i]);
			stats[i].setBounds(x_offset, y_offset, componentWidth,
					componentHeight);
			y_offset += (componentHeight + y_spacing);
		}

		// spacing to separate the stats and equipment
		y_offset += 25;

		// initialize the equipment slots
		// create and add generic label so the user knows what this section is
		equipLabel = new JLabel("Equipped Items:");
		this.add(equipLabel);
		equipLabel.setBounds(x_offset, y_offset, componentWidth,
				componentHeight);
		y_offset += (componentHeight + y_spacing);

		// get the current equipped weapon from the model
		// create and add a label with Weapon: <current equipped>, then offset
		// vertically.
		equippedWeapon = new JLabel("Weapon: "
				+ view.getModel().getAvatarEquippedWeapon());
		this.add(equippedWeapon);
		equippedWeapon.setBounds(x_offset, y_offset, componentWidth,
				componentHeight);
		y_offset += (componentHeight + y_spacing);

		// get the current equipped armor from the model
		// create and add a label with Armor: <current equipped>, then offset
		// vertically.
		equippedArmor = new JLabel("Armor: "
				+ view.getModel().getAvatarEquippedArmor());
		this.add(equippedArmor);
		equippedArmor.setBounds(x_offset, y_offset, componentWidth,
				componentHeight);
		y_offset += (componentHeight + y_spacing);

		// spacing to separate equipped from the Inventory
		y_offset += 25;

		// initialize the inventory
		// get the list of inventory from the model
		String[] inv = view.getModel().getAvatarInventory();
		inventory = new JLabel[inv.length];
		// add a generic label so the user knows what this is
		invLabel = new JLabel("Inventory:");
		this.add(invLabel);
		invLabel.setBounds(x_offset, y_offset, componentWidth, componentHeight);
		y_offset += (componentHeight + y_spacing);

		// for each item in the inventory, create and add a label with
		// [<inventoryNumber>]: <item in slot>
		for (int i = 0; i < inv.length; ++i) {
			inventory[i] = new JLabel("[" + i + "]" + ": " + inv[i]);
			this.add(inventory[i]);
			inventory[i].setBounds(x_offset, y_offset, componentWidth,
					componentHeight);
			y_offset += (componentHeight + y_spacing);
		}

	}

	/**
	 * updates all the values of the display labels to the current values in the
	 * model
	 */
	public void update() {
		// update the stats
		// get 'em
		String[] statNames = view.getModel().getAvatarStatNames();
		int[] statValues = view.getModel().getAvatarStats();
		// set 'em
		for (int i = 0; i < statNames.length; ++i) {
			stats[i].setText(statNames[i] + ": "
					+ String.valueOf(statValues[i]));
		}

		// update equipped items
		// get 'em and set 'em
		equippedWeapon.setText("Weapon: "
				+ view.getModel().getAvatarEquippedWeapon());
		equippedArmor.setText("Armor: "
				+ view.getModel().getAvatarEquippedArmor());

		// update the inventory
		// get 'em
		String[] inv = view.getModel().getAvatarInventory();
		// set 'em
		for (int i = 0; i < inv.length; ++i) {
			inventory[i].setText("[" + i + "]" + ": " + inv[i]);
		}
	}
}