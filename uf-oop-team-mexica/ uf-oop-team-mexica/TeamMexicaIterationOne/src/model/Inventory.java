package model;

import java.util.*;

/**
 * This class is responsible for storing an Entity's items in an inventory, and
 * providing an interface for accessing those items.
 * 
 * Note: I hate using the word "get" in a getter, because it should be implicit
 * that you are "getting" that variable, so I won't be using them.
 */
public class Inventory {
	/* ************************************************************************
	 * Instance Variables
	 * ************************************************************************
	 */
	private ArrayList<InventoryItem> items;
	private Weapon equippedWeapon;
	private Armor equippedArmor;

	/* ************************************************************************
	 * Constructors
	 * ************************************************************************
	 */
	/**
	 * Default constructor
	 */
	public Inventory() {
		items = new ArrayList<InventoryItem>();
		equippedWeapon = null;
		equippedArmor = null;
	}

	/* ************************************************************************
	 * Getters & Setters
	 * ************************************************************************
	 */
	/**
	 * Returns the item at the specified index
	 * 
	 * @param index
	 *            the index of the item to return; If the index is out of
	 *            bounds, returns null
	 */
	public InventoryItem itemAtIndex(int index) {
		try {
			InventoryItem item = this.items.get(index);
			return item;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 
	 * 
	 * @return returns a reference to the currently equipped weapon, null if no
	 *         weapon is equipped
	 */
	public Weapon equippedWeapon() {
		return this.equippedWeapon;
	}

	/**
	 * Sets the weapon, and removes it from the inventory (as per Dave's specs)
	 * 
	 * @param weapon
	 *            the weapon to equip (should not be null)
	 */
	public void setEquippedWeapon(Weapon weapon) {
		if (weapon == null)
			return;

		this.equippedWeapon = weapon;

		// remove the equipment from the inventory
		this.items.remove(weapon);
	}

	/**
	 * 
	 * 
	 * @return returns a reference to the currently equipped armor, null if no
	 *         armor is equipped
	 */
	public Armor equippedArmor() {
		return this.equippedArmor;
	}

	/**
	 * Sets the armor, and removes it from the inventory (as per Dave's specs)
	 * 
	 * @param armor
	 *            the armor to equip (should not be null)
	 */
	public void setEquippedArmor(Armor armor) {
		if (armor == null)
			return;

		this.equippedArmor = armor;

		// remove the equipment from the inventory
		this.items.remove(armor);
	}

	/**
	 * @return list of InventoryItems
	 */
	public List<InventoryItem> items() {
		return items;
	}

	/* ************************************************************************
	 * Inventory Manipulation
	 * ************************************************************************
	 */
	/**
	 * Adds the item to the end of the items list
	 * 
	 * @param item
	 *            the item to add (should not be null)
	 * @return true if the item was successfully added, false if the item is
	 *         null
	 */
	public boolean addItem(InventoryItem item) {
		if (item == null)
			return false;

		this.items.add(item);
		return true;
	}

	/**
	 * Removes the item at the index specified, and returns a reference to the
	 * item removed
	 * 
	 * @param index
	 *            the index of the item to remove
	 * @return returns null if the index is out of bounds
	 */
	public InventoryItem removeItemAtIndex(int index) {
		try {
			return this.items.remove(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Removes the item specified, and returns a reference to the item removed
	 * 
	 * @param item
	 *            the item to remove
	 * @return returns null if the index is out of bounds
	 */
	public InventoryItem removeItem(InventoryItem item) {
		try {
			int index = this.items.indexOf(item);
			return this.removeItemAtIndex(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Unequips the currently equipped weapon
	 * 
	 * Does nothing if there is no weapon equipped
	 */
	public void unEquipWeapon() {
		if (this.equippedWeapon == null)
			return;

		// re-add the weapon to the inventory
		this.addItem(this.equippedWeapon);

		// "unequip" the weapon
		this.equippedWeapon = null;
	}

	/**
	 * Unequips the currently equipped armor
	 * 
	 * Does nothing if there is no armor equipped
	 */
	public void unEquipArmor() {
		if (this.equippedArmor == null)
			return;

		// re-add the armor to the inventory
		this.addItem(this.equippedArmor);

		// "unequip" the armor
		this.equippedArmor = null;
	}

	/**
	 * @return the size of the inventory list
	 */
	public int size() {
		return this.items.size();
	}

	/**
	 * @return a new iterator to iterate over the items
	 */
	public Iterator<InventoryItem> iterator() {
		return this.items.iterator();
	}

	/**
	 * Drops the item at the index specified onto the Entity's Tile. In doing
	 * so, it creates a new TakeableItem and adds it to the Entity's Tile.
	 * 
	 * @param index
	 *            the index of the item to be dropped
	 * @param tile
	 *            the Tile to drop the item onto
	 * @return false if the index specified is out of bounds, true otherwise.
	 */
	public boolean dropItemAtIndexOntoTile(int index, Tile tile) {
		InventoryItem removedItem = this.removeItemAtIndex(index);

		// if the index is out of bounds, drop action failed, return false
		if (removedItem == null)
			return false;

		TakeableItem ti = new TakeableItem(removedItem,
				removedItem.getRepresentation());

		tile.add(ti);
		return true;
	}

	/**
	 * Tests if the item exists in the inventory
	 * 
	 * @param itemName
	 *            the name of the item to find
	 * @return true if the item is in the inventory
	 */
	public boolean containsItem(String itemName) {
		// too lazy to do it correctly..
		for (InventoryItem ii : items) {
			if (ii.name().equals(itemName))
				return true;
		}
		return false;
	}

}
