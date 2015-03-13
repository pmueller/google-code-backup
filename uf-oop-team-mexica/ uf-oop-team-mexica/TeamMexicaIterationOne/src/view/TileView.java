/**
 * Team Mexica Iteration 1
 * 
 * This class is the representation of a tile in the view. Knows how to 
 * paint and represent itself.
 */

package view;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;

import model.*;

public class TileView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// width and height of the tile
	private int width = View.TILE_SIZE;
	private int height = View.TILE_SIZE;

	private Tile currentTile;
	private BufferedImage appearance;
	@SuppressWarnings("rawtypes")
	private Iterator msIter;
	private Image graphic;
	private Representation msToDraw;

	/**
	 * Constructor. create a new tileview with no representation or tile
	 */
	public TileView() {
		this.setSize(width, height);
		setOpaque(false);
		setVisible(true);
		setLayout(null);
	}

	/**
	 * Changes this TileView's Tile.
	 * 
	 * @param myTile
	 */
	public void setTile(Tile myTile) {
		currentTile = myTile;
	}

	protected void paintComponent(Graphics g) {
		appearance = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = appearance.createGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f)); // allows transparent graphics to layer
		if (currentTile != null) {
			// first draw the Tile's terrain:
			graphic = View.getImage(currentTile.getRepresentation());
			g2.drawImage(graphic, 0, 0, null);

			// Decals
			msIter = currentTile.getDecals().iterator();
			while (msIter.hasNext()) {
				msToDraw = (Representation) msIter.next();
				graphic = View.getImage(msToDraw.getRepresentation());
				g2.drawImage(graphic, 0, 0, null);
			}

			// Now draw all items in the order they're stored in the Tile's
			// itemList:
			msIter = currentTile.getItems().iterator();
			while (msIter.hasNext()) {
				msToDraw = (Representation) msIter.next();
				graphic = View.getImage(msToDraw.getRepresentation());
				g2.drawImage(graphic, 0, 0, null);
			}

			// entities
			msIter = currentTile.getEntities().iterator();
			while (msIter.hasNext()) {
				// need to know the entity's facing direction to get the right
				// image
				msToDraw = (Representation) msIter.next();
				graphic = View.getImage(msToDraw.getRepresentation()
						+ ((Entity) msToDraw).getFacing().toString());
				g2.drawImage(graphic, 0, 0, null);
			}

			// Aoe's
			msIter = currentTile.getAoEs().iterator();
			while (msIter.hasNext()) {
				msToDraw = (Representation) msIter.next();
				graphic = View.getImage(msToDraw.getRepresentation());
				g2.drawImage(graphic, 0, 0, null);
			}
		} else { // null tile for some inexcusable and unexplainable reason,
					// draw a black tile
			graphic = View.getImage(View.NULL_TILE_IMG);
			g2.drawImage(graphic, 0, 0, null);
		}
		// no leaks!
		g2.dispose();
		g.drawImage(appearance, 0, 0, this);
	}
}