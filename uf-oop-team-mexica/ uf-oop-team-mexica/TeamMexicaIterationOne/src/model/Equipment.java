package model;

/**
 * This class represents an item that can be equipped to an Entity
 */

public class Equipment extends InventoryItem {
	/* ************************************************************************
	 * Instance Variables
	 * ************************************************************************
	 */
	private boolean isEquipped = false;

	/* ************************************************************************
	 * Constructors
	 * ************************************************************************
	 */
	/**
	 * This should not be called, use a subclass' constructor instead
	 */
	public Equipment() {

	}

	/* ************************************************************************
	 * Getters & Setters
	 * ************************************************************************
	 */
	/**
	 * @return true if this Equipment is currently equipped
	 */
	public boolean equipped() {
		return this.isEquipped;
	}

	/**
	 * Sets the equipped status of this item
	 * 
	 * @param equipped
	 *            the boolean status you want to set
	 */
	public void setEquipped(boolean equipped) {
		this.isEquipped = equipped;
	}

}
