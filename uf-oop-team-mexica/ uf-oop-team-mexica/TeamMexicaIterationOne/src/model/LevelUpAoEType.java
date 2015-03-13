package model;

/**
 * AoEType that levels the Entity up one level. The AoE is then disabled (and
 * removed from the map).
 * 
 * @author M. Shahalam
 */
public class LevelUpAoEType extends AoEType {

	/**
	 * Levels the Entity up one level. Then disables the effect. The
	 * LevelUpAoEType can only be used once and is then disabled.
	 * 
	 * @param e
	 *            the entity to perform the effect on
	 */
	protected void doEffect(Entity e) {
		e.instantLevelUp();
		this.disable();
	}

	/**
	 * @return "level"
	 */
	public String toString() {
		return "level";
	}

	/**
	 * @return 0
	 */
	public int getNum() {
		return 0;
	}

}
