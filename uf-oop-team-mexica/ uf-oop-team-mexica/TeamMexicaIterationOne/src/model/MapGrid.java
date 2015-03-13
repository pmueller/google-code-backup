/*
 * MapGrid stores a 2D array containing all the tiles in the current map.
 * Authors: Dan and Jason
 */

package model;

import java.lang.StringBuffer;

public class MapGrid {
	private Tile grid[][];
	private int maxX;
	private int maxY;

	// CONSTRUCTORS ---------------------------------------------------
	/**
	 * Constructor
	 */
	public MapGrid() {

	}

	/**
	 * Constructor
	 * 
	 * @param x
	 *            width of map
	 * @param y
	 *            height of map
	 */
	public MapGrid(int x, int y) {
		grid = new Tile[x][y];
		maxX = x;
		maxY = y;
	}

	/**
	 * sets the new dimensions of the map
	 * 
	 * @param x
	 *            the new width
	 * @param y
	 *            the new height
	 */
	public void setDimensions(int x, int y) {
		grid = new Tile[x][y];
		maxX = x;
		maxY = y;
	}

	/**
	 * sets the new map
	 * 
	 * @param newMap
	 *            the new map
	 */
	public MapGrid(Tile[][] newMap) {
		grid = newMap;
		maxX = grid.length;
		maxY = grid[0].length;
	}

	/**
	 * sets the tile with the specified terrain type at the specified
	 * coordiantes
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param terrainType
	 *            the terrain type
	 */
	public void setTile(int x, int y, Terrain terrainType) {
		if (x >= 0 && y >= 0 && x < maxX && y < maxY) // checks boundaries
			grid[x][y] = new Tile(terrainType);
	}

	/**
	 * Called by View, this method returns the set of tiles occupying the
	 * rectangular area closest to the area passed in. It prevents non-existent
	 * tiles from being displayed.
	 * 
	 * @param northWestCorner
	 *            The upper-left corner of the desired tile set.
	 * @param southEastCorner
	 *            The lower-right corner of the desired tile set.
	 * @return The actual tile set closest to the desired tile set.
	 */
	public Tile[][] getBestRect(Vector northWestCorner, Vector southEastCorner) {

		int width = southEastCorner.x - northWestCorner.x;
		int height = southEastCorner.y - northWestCorner.y;
		Tile[][] toReturn = new Tile[width + 1][height + 1];

		// int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		int x1, y1, x2, y2;
		x1 = Math.max(northWestCorner.x, 0);
		x1 = Math.min(maxX - 1 - width, x1);
		y1 = Math.max(northWestCorner.y, 0);
		y1 = Math.min(maxY - height, y1);
		x2 = Math.min(southEastCorner.x, maxX);
		x2 = Math.max(width, x2);
		y2 = Math.min(southEastCorner.y, maxY);
		y2 = Math.max(height, y2);

		// Note: variable initializations and increments extracted from
		// following loops for better readability
		int k = 0;
		int m;
		for (int i = x1; i <= x2; i++) {
			m = 0;
			for (int j = y1; j <= y2; j++) {
				try {
					toReturn[k][m++] = grid[i][j];
				} catch (ArrayIndexOutOfBoundsException e) {
					// System.out.println(e + " - MapGrid.getBestRect()");
				}
			}
			k++;
		}
		// System.out.println(toReturn[0][0]);
		return toReturn;
		// return grid;
	}

	/**
	 * 
	 * @param index
	 *            the coordinate of the requested tile
	 * @return the tile at the requested coordinate
	 */
	public Tile getTileAt(Vector index) {
		if (index.x <= maxX && index.y <= maxY && index.x >= 0 && index.y >= 0)
			return grid[index.x][index.y];
		return null;
	}

	/**
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the tile at the requested x and y coordinate
	 */
	public Tile getTileAt(int x, int y) {
		if (x >= 0)
			if (y >= 0)
				if (x <= maxX)
					if (y <= maxY)
						return grid[x][y];
		return null;
	}

	/**
	 * prints the map
	 */
	public void printAll() {
		if (grid == null) {
			System.out.println("Null grid!!");
		} else {
			for (int y = 0; y < grid.length; ++y) {
				for (int x = 0; x < grid[y].length; ++x) {
					System.out.print(grid[x][y].getTerrain()
							.getRepresentation());
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
			for (int y = 0; y < grid.length; ++y) {
				for (int x = 0; x < grid[y].length; ++x) {
					if (grid[x][y].getTerrain().isTraversable()) {
						System.out.print(".");
					} else {
						System.out.print("#");

					}
				}
				System.out.println();
			}

		}
	}

	/**
	 * 
	 * @param startLoc
	 *            the starting location
	 * @return the string representation
	 */
	public String saveString(Vector startLoc) {
		StringBuffer out = new StringBuffer();
		out.append("Map{\n");
		out.append("size:").append(" " + maxX).append(" " + maxY + "\n");
		out.append("startLoc:").append(" " + startLoc.x)
				.append(" " + startLoc.y + "\n");
		out.append("grass: Terrain{\n\tpassable: true\n\trep: Assets/GrassTile1\n}\n"
				+ "mountain: Terrain{\n\tpassable: false\n\trep: Assets/Mountains1\n"
				+ "}\nwater: Terrain{\n\tpassable: false\n\trep: Assets/Water1\n}\n");
		out.append("Grid{\n");
		for (int j = 0; j < maxY; j++) {
			for (int i = 0; i < maxX; i++) {
				String rep = grid[i][j].getTerrain().getRepresentation();

				if (rep.equals("Assets/GrassTile1")) {
					out.append(". ");
				} else if (rep.equals("Assets/Mountains1")) {
					out.append("m ");
				} else if (rep.equals("Assets/Water1")) {
					out.append("~ ");
				}
			}
			out.append("\n");
		}
		out.append("}\n}\n");
		return out.toString();
	}

	/**
	 * 
	 * @return the string representation of the map
	 */
	public String saveTiles() {
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < maxX; i++) {
			for (int j = 0; j < maxY; j++) {
				String str = grid[i][j].saveString(i, j);
				out.append(str);
			}
		}
		return out.toString();
	}
}