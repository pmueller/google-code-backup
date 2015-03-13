package model;

/**
 * Abstraction of something on the map (on a Tile) that can handle collisions,
 * and can remove themselves from the map.
 * 
 * @author M. Shahalam
 */
public abstract class MapStuff implements Representation {
	protected Tile currentTile;
	private boolean wantsToBeRemoved = false;
	private String rep = "";

	/**
	 * Sets the current Tile (location on the map) of the MapStuff.
	 * 
	 * @param tile
	 *            Tile to relocate to.
	 */
	public void setCurrentTile(Tile tile) {
		currentTile = tile;
	}

	/**
	 * Gets the current Tile (location on the map) of the MapStuff.
	 * 
	 * @return Tile of current residence.
	 */
	public Tile getCurrentTile() {
		return currentTile;
	}

	/**
	 * Gets the filepath of the representation (the awesome graphics) of the
	 * MapStuff.
	 * 
	 * @return String corresponding to a filepath for the graphical
	 *         representation of the MapStuff.
	 */
	public String getRepresentation() {
		return rep;
	}

	/**
	 * Sets the filepath for the graphical representation of the MapStuff.
	 * 
	 * @param rep
	 *            The filpath for the graphical representation.
	 */
	public void setRepresentation(String rep) {
		this.rep = rep;
	}

	/**
	 * A MapStuff calls this method when it wants to remove itself from the
	 * world.
	 */
	protected void removeSelf() {
		wantsToBeRemoved = true;
	}

	protected void setWantsToBeRemoved(boolean wantsToBeRemoved) {
		this.wantsToBeRemoved = wantsToBeRemoved;
	}

	/**
	 * Returns if the MapStuff wants to remove itself from the world.
	 * 
	 * @return <code>true</code> if the MapStuff doesn't want to live anymore.
	 */
	public boolean wantsToBeRemoved() {
		return wantsToBeRemoved;
	}

	abstract public String saveString(Vector v);
}
