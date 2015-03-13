/**
 * Team Mexica Iteration 1
 * 
 * The primary panel for the game. This panel will display the
 * actual tiles themselves in the rectangular playing area, including
 * the avatar sprite, the terrain, any items on the ground, obstacles, decals, &c.
 * Maintains a grid of TileViews. Major component of GameView.
 * 
 * @author Bobby
 */

package view;

import java.awt.GridLayout;
import javax.swing.JPanel;
import model.Model;
import model.Tile;
import model.Vector;

public class MapView extends JPanel implements ViewPort {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7358274046904760188L;
	
	// height and width of the panel
	private int width = View.GAME_HEIGHT;
	private int height = View.GAME_HEIGHT;

	// size of the tiles
	private int tilesHigh = height / View.TILE_SIZE;
	private int tilesWide = width / View.TILE_SIZE;

	@SuppressWarnings("unused")
	private View view;
	private Model model;

	/**
	 * The position of the avatar is used to define the ideal view area
	 * rectangle.
	 */
	private Vector avatarPos;
	@SuppressWarnings("unused")
	private Vector viewAreaTopLeft;
	@SuppressWarnings("unused")
	private Vector viewAreaBottomRight;

	/** The set of TileViews updated on every frame. */
	private TileView[][] tileViews = new TileView[tilesWide][tilesHigh];

	/** The set of currently displayed Tiles. */
	private Tile[][] tiles = new Tile[tilesWide][tilesHigh];

	/**
	 * Constructor. Sets the reference to view and model, sets up the layout
	 * 
	 * @param view
	 *            reference to the view
	 * @param model
	 *            reference to the model
	 */
	public MapView(View view, Model model) {
		this.view = view;
		this.model = model;
		// set to the correct size
		this.setSize(width, height);
		// set to a grid layout for the tiles
		this.setLayout(new GridLayout(tilesHigh, tilesWide));
		// add a tileview for each tile of the screen
		for (int y = 0; y < tilesHigh; y++) {
			for (int x = 0; x < tilesWide; x++) {
				tileViews[x][y] = new TileView();
				this.add(tileViews[x][y]);
			}
		}
		setOpaque(false);
		setVisible(true);
	}

	/**
	 * update for the map, get the new tiles if the avatar has moved and repaint
	 * all the graphics
	 */
	public void update() {
		if (model.getAvatarPosition() != avatarPos) { // avatar has moved, so
														// all TileViews need to
														// get new Tiles
			// get new position and tiles from model
			avatarPos = model.getAvatarPosition();
			tiles = model.getTiles(new Vector(avatarPos.x - tilesWide / 2,
					avatarPos.y - tilesHigh / 2), new Vector(avatarPos.x
					+ tilesWide / 2, avatarPos.y + tilesHigh / 2));
			// for each new tile, set them to the tileviews
			for (int x = 0; x < tiles.length; x++) {
				for (int y = 0; y < tiles[x].length; y++) {
					tileViews[x][y].setTile(tiles[x][y]);
				}
			}
		}
		// actually repaint everything
		repaint();
	}
}