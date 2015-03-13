package model;

/**
 * This class represents an armor that can be equipped to an Entity
 */

public class Armor extends Equipment {
	/* ************************************************************************
	 * Instance Variables
	 * ************************************************************************
	 */
	private int armorBonus;

	/* ************************************************************************
	 * Constructors
	 * ************************************************************************
	 */
	/**
	 * @param bonus
	 *            the defensive bonus this item provides
	 */
	public Armor(int bonus) {
		armorBonus = bonus;
	}

	/**
	 * @param name
	 *            the name of the armor
	 * @param rep
	 *            the representation filename
	 * @param bonus
	 *            the defensive bonus this item provides
	 */
	public Armor(String name, String rep, int bonus) {
		armorBonus = bonus;
		this.name = name;
		this.representation = rep;
	}

	/* ************************************************************************
	 * Getters & Setters
	 * ************************************************************************
	 */
	/**
	 * @return the defensive bonus (for calculations)
	 */
	public int armorBonus() {
		return this.armorBonus;
	}

	/* ************************************************************************
	 * Other Methods
	 * ************************************************************************
	 */
	/**
	 * There are three cases of this method when an Entity "uses" this item:
	 * 
	 * 1. The current equipped armor doesn't exist, so equip this one 2. This
	 * armor is currently equipped, so unequip it 3. There is a armor currently
	 * equipped that is not this weapon, so switch it with this one
	 * 
	 * @param entity
	 *            the Entity whose equipped armor we are manipulating
	 */
	public void useOnEntity(Entity entity) {
		Inventory inventory = entity.getInventory();
		Armor currentArmor = inventory.equippedArmor();
		if (currentArmor == null) {
			// this weapon is not equipped, equip it
			inventory.setEquippedArmor(this);
		} else if (currentArmor == this) {
			// this weapon is equipped, unequip it
			inventory.unEquipArmor();
		} else {
			// there is another weapon, switch it with this one
			inventory.unEquipArmor();
			inventory.setEquippedArmor(this);
		}
	}

	public String saveString() {
		StringBuffer out = new StringBuffer();
		out.append("InventoryItem{\n");
		out.append("\t\ttype: armor\n");
		out.append("\t\trep: " + getRepresentation() + "\n");
		out.append("\t\tname: " + name + "\n");
		out.append("\t\tbonus: " + armorBonus + "\n");
		out.append("\t}\n");
		return out.toString();
	}

}
