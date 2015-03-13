package model;

/**
 * An ObstacleItem prevents an Entity from moving onto the Tile of the
 * ObstacleItem.
 * 
 * @author M. Shahalam
 */
public class ObstacleItem extends Item {

	/**
	 * Creates a new ObstacleItem with a representation.
	 * 
	 * @param rep
	 *            The file path / name (representation) of the visual
	 *            corresponding with this ObstacleItem.
	 */
	public ObstacleItem(String rep) {
		setRepresentation(rep);
	}

	/**
	 * Responds to collsion with Entity by doing nothing and saying the Tile it
	 * is on is impassable
	 * 
	 * @return <code>false</code> indicating the Tile the ObstacleItem is on is
	 *         impassable.
	 */
	public boolean collideWith(Entity e) {
		return false;
	}

	/**
	 * Save string used by the LoaderSaver to save the ObstacleItem.
	 */
	public String saveString(Vector v) {
		StringBuffer out = new StringBuffer();
		out.append("ObstacleItem{\n");
		out.append("\tlocation: " + v.x + " " + v.y + "\n");
		out.append("\trep: " + getRepresentation() + "\n");
		out.append("}\n");
		return out.toString();
	}
}
