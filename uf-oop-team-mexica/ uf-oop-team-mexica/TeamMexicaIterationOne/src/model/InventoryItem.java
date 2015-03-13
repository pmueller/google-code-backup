package model;

/**
 * This class represents an item that can be added to an Inventory
 */

public class InventoryItem implements Representation {
	/* ************************************************************************
	 * Instance Variables
	 * ************************************************************************
	 */
	protected String name;
	protected String representation;

	/* ************************************************************************
	 * Constructors
	 * ************************************************************************
	 */
	/**
	 * Sets item name to "Unnamed Item"
	 */
	public InventoryItem() {
		this.name = "Unnamed Item";
	}

	/**
	 * @param filename
	 *            the filename of this item's representation
	 */
	public InventoryItem(String filename) {
		this.name = "Unnamed Item";
		this.representation = filename;
	}

	/**
	 * @param n
	 *            the name of the item
	 * @param filename
	 *            the filename of this item's representation
	 */
	public InventoryItem(String n, String filename) {
		this.name = n;
		this.representation = filename;
	}

	/* ************************************************************************
	 * Getters & Setters
	 * ************************************************************************
	 */
	/**
	 * @return the name of this item
	 */
	public String name() {
		return this.name;
	}

	/**
	 * Sets the name of the item
	 * 
	 * @param n
	 *            the name of the item
	 */
	public void setName(String n) {
		this.name = n;
	}

	/**
	 * Sets the filename for the representation of this item
	 * 
	 * @param name
	 *            the file name to set
	 */
	public void setRepresentation(String name) {
		this.representation = name;
	}

	/* ************************************************************************
	 * Representation Interface
	 * ************************************************************************
	 */
	public String getRepresentation() {
		return this.representation;
	}

	/* ************************************************************************
	 * Other Methods
	 * ************************************************************************
	 */
	/**
	 * This method is a hook for applying an item's effects to an entity
	 * 
	 * @param entity
	 *            the entity you want to apply this item's effects on
	 */
	public void useOnEntity(Entity entity) {
		// subclasses override
	}

	/**
	 * 
	 * @return the string representation of the item
	 */
	public String saveString() {
		StringBuffer out = new StringBuffer();
		out.append("InventoryItem{\n");
		out.append("\t\ttype: other\n");
		out.append("\t\tname: " + name + "\n");
		out.append("\t\trep: " + getRepresentation() + "\n");
		out.append("\t}\n");
		return out.toString();
	}
}
