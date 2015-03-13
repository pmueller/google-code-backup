package model;

/**
 * OneShotItems are activated once, when an Entity collides with it, and does
 * something to the Entity.
 * 
 * @author M. Shahalam
 */
public abstract class OneshotItem extends Item {

	/**
	 * Responds to collision with an Entity and does something, then removes
	 * itself from the map.
	 */
	public boolean collideWith(Entity e) {
		doInteraction(e);
		removeSelf();
		return true;
	}

	/**
	 * Defines the interaction of the OneShotItem with an Entity
	 * 
	 * @param e
	 *            Entity to do the interaction with / on.
	 */
	protected abstract void doInteraction(Entity e);
}
