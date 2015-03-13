package model;

public class Terrain implements Representation {
	private Boolean canTraverse;
	private String strURL;

	/**
	 * Constructor
	 * 
	 * @param traversability
	 *            true if this terrain is traversable
	 * @param strURL
	 *            the filename of the terrain's image
	 */
	public Terrain(Boolean traversability, String strURL) {
		canTraverse = traversability;
		this.strURL = strURL;
	}

	/**
	 * 
	 * @return true if this terrain is traversable
	 */
	public Boolean isTraversable() {
		return canTraverse;
	}

	/**
	 * @return the url of the image file
	 */
	public String getRepresentation() {
		return strURL;
	}
}