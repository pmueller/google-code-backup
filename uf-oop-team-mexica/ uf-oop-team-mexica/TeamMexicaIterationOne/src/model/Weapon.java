package model;

/**
 * This class represents a weapon that can be equipped to an Entity
 */

public class Weapon extends Equipment {
	/* ************************************************************************
	 * Instance Variables
	 * ************************************************************************
	 */
	private int offenseBonus;

	/* ************************************************************************
	 * Constructors
	 * ************************************************************************
	 */
	/**
	 * Default constructor
	 * 
	 * @param bonus
	 *            the offensive bonus this item provides
	 */
	public Weapon(int bonus) {
		offenseBonus = bonus;
	}

	/**
	 * Secondary constructor
	 * 
	 * @param name
	 *            the name of the weapon
	 * @param rep
	 *            the representation filename
	 * @param bonus
	 *            the offensive bonus this item provides
	 */
	public Weapon(String name, String rep, int bonus) {
		offenseBonus = bonus;
		this.name = name;
		this.representation = rep;
	}

	/* ************************************************************************
	 * Getters & Setters
	 * ************************************************************************
	 */
	/**
	 * @return the offensive bonus (for calculations)
	 */
	public int offenseBonus() {
		return this.offenseBonus;
	}

	/* ************************************************************************
	 * Other Methods
	 * ************************************************************************
	 */
	/**
	 * There are three cases of this method when an Entity "uses" this item:
	 * 
	 * 1. The current equipped weapon doesn't exist, so equip this one 2. This
	 * weapon is currently equipped, so unequip it 3. There is a weapon
	 * currently equipped that is not this weapon, so switch it with this one
	 * 
	 * @param entity
	 *            the Entity whose equipped weapon we are manipulating
	 */
	public void useOnEntity(Entity entity) {
		Inventory inventory = entity.getInventory();
		Weapon currentWeapon = inventory.equippedWeapon();
		if (currentWeapon == null) {
			// there is no equipped weapon, equip this one
			inventory.setEquippedWeapon(this);
		} else if (currentWeapon == this) {
			// this weapon is currently equipped, unequip it
			inventory.unEquipWeapon();
		} else {
			// there is another weapon, switch it with this one
			inventory.unEquipWeapon();
			inventory.setEquippedWeapon(this);
		}
	}

	public String saveString() {
		StringBuffer out = new StringBuffer();
		out.append("InventoryItem{\n");
		out.append("\t\ttype: weapon\n");
		out.append("\t\trep: " + getRepresentation() + "\n");
		out.append("\t\tname: " + name + "\n");
		out.append("\t\tbonus: " + offenseBonus + "\n");
		out.append("\t}\n");
		return out.toString();
	}
}
