package model;

/**
 * @author M. Shahalam
 * 
 */
public class TakeableItem extends Item {
	private InventoryItem inventoryItem;

	/**
	 * Constructor
	 * 
	 * @param inventoryItem
	 *            the inventory item to reference
	 */
	public TakeableItem(InventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}

	/**
	 * Constructor
	 * 
	 * @param inventoryItem
	 *            the inventory item to reference
	 * @param rep
	 *            the filename of the representation
	 */
	public TakeableItem(InventoryItem inventoryItem, String rep) {
		this.inventoryItem = inventoryItem;
		setRepresentation(rep);

	}

	/**
	 * adds the item to the entity's inventory and removes it from the map
	 * 
	 * @return true
	 */
	public boolean collideWith(Entity e) {
		if (e.addToInventory(this.inventoryItem))
			removeSelf();
		return true;
	}

	/**
	 * @return the string representation of this object
	 */
	public String saveString(Vector v) {
		StringBuffer out = new StringBuffer();
		out.append("TakeableItem{\n");
		out.append("\tlocation: " + v.x + " " + v.y + "\n");
		out.append("\trep: " + getRepresentation() + "\n");
		out.append("\titem: " + inventoryItem.saveString());
		out.append("}\n");
		return out.toString();
	}
}
