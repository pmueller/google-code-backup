package model;

/**
 * @author M. Shahalam
 * 
 */
public class TakeDamageAoEType extends AoEType {
	private int damageAmount;

	/**
	 * Constructor
	 * 
	 * @param damageAmount
	 *            the amount of damage to inflict
	 */
	public TakeDamageAoEType(int damageAmount) {
		this.damageAmount = damageAmount;
	}

	/**
	 * does damage to the entity
	 */
	protected void doEffect(Entity e) {
		// needs to conform to Entity's interface. Entity is
		// responsible for checking min life, is dead, etc.
		e.takeDamage(damageAmount);
		System.out.println("takin' damage");
	}

	/**
	 * @return "damage"
	 */
	public String toString() {
		return "damage";
	}

	/**
	 * @return the damage amount
	 */
	public int getNum() {
		return this.damageAmount;
	}
}
