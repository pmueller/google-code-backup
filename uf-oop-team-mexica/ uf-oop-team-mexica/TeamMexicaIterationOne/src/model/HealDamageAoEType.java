package model;

/**
 * AoEType that heals an Entity.
 * 
 * @author M. Shahalam
 */
public class HealDamageAoEType extends AoEType {
	private int healAmount;

	/**
	 * Creates a new Healing AoEType with the heal amount to be added to
	 * Entity's health each update.
	 * 
	 * @param healAmount
	 *            Amount to increase Entity's health.
	 */
	public HealDamageAoEType(int healAmount) {
		super();
		this.healAmount = healAmount;
	}

	/**
	 * Increases Entity's health by some amount. Entity is responsible for
	 * checking max health allowed, etc.
	 */
	protected void doEffect(Entity e) {
		e.setCurrentLife(e.getCurrentLife() + healAmount);
	}

	/**
	 * @return "heal"
	 */
	public String toString() {
		return "heal";
	}

	/**
	 * @return the healing amount
	 */
	public int getNum() {
		return this.healAmount;
	}
}
