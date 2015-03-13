package model;

public class Vector extends java.awt.Point {
	/**
	 * Added this to get rid of the warning
	 */
	private static final long serialVersionUID = 3047375105528794538L;

	public static final Vector NORTH = new Vector(0, -1);
	public static final Vector NORTHEAST = new Vector(1, -1);
	public static final Vector EAST = new Vector(1, 0);
	public static final Vector SOUTHEAST = new Vector(1, 1);
	public static final Vector SOUTH = new Vector(0, 1);
	public static final Vector SOUTHWEST = new Vector(-1, 1);
	public static final Vector WEST = new Vector(-1, 0);
	public static final Vector NORTHWEST = new Vector(-1, -1);

	/**
	 * Constructor
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public Vector(int x, int y) {
		super(x, y);
	}

	/**
	 * Adds one vector to another
	 * 
	 * @param v
	 *            the vector to add
	 * @return the result of adding this + v
	 */
	public Vector add(Vector v) {
		return new Vector(this.x + v.x, this.y + v.y);
	}

	/**
	 * Compares another Vector to this one, determining if they have the same
	 * magnitude and direction.
	 * 
	 * @param toCompare
	 *            The Vector to compare this Vector to.
	 * @return <code>True</code> if toCompare is equivalent to this Vector,
	 *         <code>False</code> otherwise.
	 */
	public Boolean equals(Vector toCompare) {
		return (toCompare.x == this.x && toCompare.y == this.y);
	}

	/**
	 * returns the string representation of the vector
	 */
	public String toString() {
		if (this.equals(NORTH))
			return "N";
		if (this.equals(NORTHEAST))
			return "NE";
		if (this.equals(EAST))
			return "E";
		if (this.equals(SOUTHEAST))
			return "SE";
		if (this.equals(SOUTH))
			return "S";
		if (this.equals(SOUTHWEST))
			return "SW";
		if (this.equals(WEST))
			return "W";
		if (this.equals(NORTHWEST))
			return "NW";
		return "(" + x + "," + y + ")";
	}
}
