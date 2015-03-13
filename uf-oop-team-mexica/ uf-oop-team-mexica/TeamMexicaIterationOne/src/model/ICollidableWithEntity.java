package model;

/**
 * Interface for being able to collide with Entitys
 * 
 * @author M. Shahalam
 */
public interface ICollidableWithEntity {
	/**
	 * Determines a reaction to an Entity attempting to walk into yo Tile.
	 * 
	 * @param e
	 *            The Entity that has collided with this you.
	 * 
	 * @return <code>true</code> if passable, <code>false</code> if impassable.
	 */
	public boolean collideWith(Entity e);

	/**
	 * Checks if the thing wants to be removed.
	 * 
	 * @return <code>true</code> if the thing wants to be removed. Should be
	 *         already defined inside MapStuff for all MapStuff
	 */
	public boolean wantsToBeRemoved();
}
