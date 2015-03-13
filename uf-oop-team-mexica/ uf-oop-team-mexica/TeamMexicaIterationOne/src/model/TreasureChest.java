package model;

/**
 * A TreasureChest is an InteractiveItem that requires the Entity interacting
 * with it to have some item in its Inventory, and if that item is present,
 * awards the Entity with a prize and puts the prize in the Entity's Inventory.
 * 
 * @author M. Shahalam
 */
public class TreasureChest extends InteractiveItem {
	private String itemNameInInventory;
	private InventoryItem prizeToGive;

	/**
	 * Creates a new TreasureChest.
	 * 
	 * @param itemNameInInventory
	 *            Name of item to require in order to open chest.
	 * @param prizeToGive
	 *            IventoryItem to give to the Entity as a prize upon successful
	 *            open.
	 */
	public TreasureChest(String itemNameInInventory, InventoryItem prizeToGive) {
		this.itemNameInInventory = itemNameInInventory;
		this.prizeToGive = prizeToGive;
	}

	/**
	 * Creates a new TreasureChest.
	 * 
	 * @param itemNameInInventory
	 *            name of InventoryItem to require.
	 */
	public TreasureChest(String itemNameInInventory) {
		this(itemNameInInventory, new InventoryItem());
	}

	/**
	 * Searches Entity's Inventory of some item, and adds prize Item to its
	 * Inventory if the Entity has the required item.
	 */
	protected void doInteraction(Entity e) {
		if (e.getInventory().containsItem(itemNameInInventory)) {
			if (e.getInventory().addItem(prizeToGive)) {
				this.currentTile.add(new ObstacleItem("Assets/OpenChest"));
				this.currentTile.remove(this);
			}
		}
	}

	/**
	 * Save string LoaderSaver uses to save the TreasureChest.
	 */
	public String saveString(Vector v) {
		StringBuffer out = new StringBuffer();
		out.append("InteractiveItem{\n");
		out.append("\tlocation: " + v.x + " " + v.y + "\n");
		out.append("\ttype: treasureChest\n");
		out.append("\tinfo: " + itemNameInInventory + "\n");
		out.append("\titem: " + prizeToGive.saveString());
		out.append("\trep: " + getRepresentation() + "\n");
		out.append("}\n");
		return out.toString();
	}
}
