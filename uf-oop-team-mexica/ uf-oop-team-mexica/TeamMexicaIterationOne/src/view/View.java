/**
 * Team Mexica Iteration 1
 * 
 * The main View class provides a well-defined interface that is 
 * the main access point into the view subsystem. Through this interface,
 *  the controller can send commands to the view subsystem corresponding to 
 *  external input.
 * 
 */
package view;

import model.Model;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View {

	// view game constants
	public static final String NULL_TILE_IMG = "Assets/NullTile";
	public static final String FACE_N = "n";
	public static final String FACE_NE = "ne";
	public static final String FACE_E = "e";
	public static final String FACE_SE = "se";
	public static final String FACE_S = "s";
	public static final String FACE_SW = "sw";
	public static final String FACE_W = "w";
	public static final String FACE_NW = "nw";

	// currently shown view
	private ViewPort currentView;
	private JFrame gameWindow;
	private Model model;
	// map containing name, image pairs
	private static HashMap<String, BufferedImage> imgs;

	// height and width for menus
	public static int MENU_WIDTH = 400;
	public static int MENU_HEIGHT = 400;

	// height and width for the game
	public static int GAME_WIDTH = 1024;
	public static int GAME_HEIGHT = 750;

	// size of tiles (both height and width)
	public static int TILE_SIZE = 50;

	// extension for all gfx files
	private static String fileExtension = ".png";

	/**
	 * Constructor. Sets up layout, initalizes fields
	 * 
	 * @param model
	 *            , reference to Model
	 */
	public View(Model model) {
		this.model = model;
		// make the actual window, set it to the right size, layout type
		// and make it so when someone hits X, the game will exit
		gameWindow = new JFrame("Mexica Iteration 1");
		gameWindow.setSize(MENU_WIDTH, MENU_HEIGHT);
		gameWindow.setLayout(null);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// show the window
		this.getGameWindow().setVisible(true);
		// empty image map
		imgs = new HashMap<String, BufferedImage>();
	}

	/**
	 * Will return the image associated with a given name, or create the image
	 * and store it/return it if it doesn't already exist
	 * 
	 * @param name
	 *            , name of the image file you want
	 * @return Image, the image corresponding to the name supplied
	 */
	public static Image getImage(String name) {
		if (!imgs.containsKey(name)) {
			try {
				imgs.put(name, ImageIO.read(new File(name + fileExtension)));
			} catch (Exception e) {
				// ffffffuuuuuuuuuuuuu
				System.out.println(e + ", " + name
						+ " - View.java, getImage method, tried to open: "
						+ name + fileExtension);
			}
		}
		return imgs.get(name);
	}

	/**
	 * getter for model
	 * 
	 * @return Model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * getter for the currentView
	 * 
	 * @return ViewPort
	 */
	public ViewPort getCurrentView() {
		return currentView;
	}

	/**
	 * setter for currentView
	 * 
	 * @param toSet
	 *            , the ViewPort you want to be the current
	 */
	public void setCurrentView(ViewPort toSet) {
		this.getGameWindow().setContentPane((JPanel) toSet);
		currentView = toSet;
	}

	/**
	 * getter for the gameWindow
	 * 
	 * @return JFrame
	 */
	public JFrame getGameWindow() {
		return gameWindow;
	}

	/**
	 * make a new MainMenuView, set it to current view and return it
	 * 
	 * @return MainMenuView
	 */
	public MainMenuView initMainMenu() {
		this.getGameWindow().setSize(MENU_WIDTH, MENU_HEIGHT);
		MainMenuView mmv = new MainMenuView();
		this.setCurrentView(mmv);
		return mmv;
	}

	/**
	 * make a new PauseMenuView, set it to current view and return it
	 * 
	 * @return PauseMenuView
	 */
	public PauseMenuView initPauseMenu() {
		this.getGameWindow().setSize(MENU_WIDTH, MENU_HEIGHT);
		PauseMenuView pmv = new PauseMenuView();
		this.setCurrentView(pmv);
		return pmv;
	}

	/**
	 * make a new CreateCharView, set it to current view and return it
	 * 
	 * @return CreateCharView
	 */
	public CreateCharView initCreateChar() {
		this.getGameWindow().setSize(MENU_WIDTH, MENU_HEIGHT);
		CreateCharView ccv = new CreateCharView();
		this.setCurrentView(ccv);
		return ccv;
	}

	/**
	 * make a new GameView, with a new mapview and inventorystatsview, set it to
	 * current view and return it
	 * 
	 * @return GameView
	 */
	public GameView initGameplay() {
		this.getGameWindow().setSize(GAME_WIDTH, GAME_HEIGHT);
		GameView gv = new GameView(new MapView(this, model),
				new InventoryStatsView(this));
		this.setCurrentView(gv);
		gv.requestFocus();
		return gv;
	}

	/**
	 * call update on the currentView
	 */
	public void update() {
		currentView.update();
	}
}
