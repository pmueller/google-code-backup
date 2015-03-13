package model;

/**
 * AoEType that kills the Entity instantly.
 * 
 * @author M. Shahalam
 */
public class InstantDeathAoEType extends AoEType {

	/**
	 * Kills the Entity
	 * 
	 * @param e
	 *            Entity to be killed.
	 */
	protected void doEffect(Entity e) {
		// Entity is responsible for taking entity out of aoe's tile.
		e.instantDeath();
	}

	/**
	 * @return "death"
	 */
	public String toString() {
		return "death";
	}

	/**
	 * @return 8
	 */
	public int getNum() {
		return 8;
	}
}
