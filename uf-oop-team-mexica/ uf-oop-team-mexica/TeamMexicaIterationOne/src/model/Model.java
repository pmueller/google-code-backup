package model;

/**
 * Model provides a facade for the View and Controller.
 * 
 * @author M. Shahalam
 */
public class Model {
	private LoaderSaver ls;
	private MapStuffManager msm;
	private MapGrid mg;

	private final String defaultFileName = "Assets/LoadMap.dat";

	private boolean pause;

	/**
	 * Creates and initiates a new Model object.
	 * 
	 */
	public Model() {
		pause = false;
		msm = new MapStuffManager();
		mg = new MapGrid();
		ls = new LoaderSaver(mg, msm);
	}

	/**
	 * new game is going to call loadersaver with a load file of the default map
	 * (just a map without any entities defined on it). Then, when the
	 * controller tells the Model the avatarName and avatarOccupation... Model
	 * will tell MSM to create a new avatar
	 * 
	 * @param avatarName
	 *            the name of the avatar
	 * @param avatarOccupation
	 *            the avatar's occupation
	 */
	public void newGame(String avatarName, String avatarOccupation) {
		Occupation o;
		if (avatarOccupation.equals("Smasher"))
			o = new Smasher();
		else if (avatarOccupation.equals("Sneak"))
			o = new Sneak();
		else
			o = new Summoner();
		ls.load(defaultFileName);

		// msm.setAvatarStartingTile( mg.getTileAt( avatarStartingPosition ));

		// ls.load("Assets/LoadEntity.dat");
		initAvatar(avatarName, o);

		// remove later, change init pos to what loadersaver reads
		// msm.getAvatar().setCurrentTile( mg.getTileAt( avatarStartingPosition
		// ) );
		// mg.getTileAt( avatarStartingPosition ).add( msm.getAvatar() );

		// msm.getAvatar().setRepresentation( "Assets/Avatar.png" );

		// mg.printAll();//remove later
	}

	/**
	 * Loads a new game from map file(s).
	 * 
	 * @param filepath
	 *            String of the file path with map data.
	 */
	public void loadGame(String filepath) {
		ls.load(filepath);
	}

	/**
	 * Saves the current game to map file(s).
	 * 
	 * @param filepath
	 *            String of the file path with map data.
	 */
	public void saveGame(String filepath) {
		ls.save(filepath);
	}

	/**
	 * Initializes the Avatar with the given name and Occupation. This is the
	 * only (legal) way to create an Avatar.
	 * 
	 * @param name
	 *            Name of the Avatar.
	 * @param occupation
	 *            Occupation of the Avatar.
	 */
	public void initAvatar(String name, Occupation occupation) {
		System.out.println(" Model.initAvatar() called ");
		msm.addAvatar(new Entity(name, occupation));
	}

	/**
	 * Attempts to move the Avatar in the given Direction.
	 * 
	 * @param direction
	 *            Direction to attempt to move the Avatar.
	 */
	public void moveAvatar(Vector dir) {
		if (msm.getAvatar() == null)
			System.out.println(" oh no no avatar.. :( ");
		if (msm.getAvatar().getCurrentTile() == null)
			System.out.println(" Oh noes no tile for de avatar ");
		msm.moveEntity(mg, msm.getAvatar(), dir);
	}

	/**
	 * unequips the avatar's current weapon
	 */
	public void unequipAvatarWeapon() {
		Weapon i = msm.getAvatar().getInventory().equippedWeapon();
		if (i != null) {
			msm.getAvatar().unequip(i);
		}
	}

	/**
	 * unequips the avatar's current armor
	 */
	public void unequipAvatarArmor() {
		Armor i = msm.getAvatar().getInventory().equippedArmor();
		if (i != null) {
			msm.getAvatar().unequip(i);
		}
	}

	/**
	 * 
	 * @return true if the avatar has lives left, false otherwise
	 */
	public boolean avatarHasLivesLeft() {
		return msm.getAvatar().getLivesLeft() > -1;
	}

	/**
	 * Uses the Avatar's item from the specified inventory index in the Avatar's
	 * inventory. Using an item is the same as equipping/ unequipping an
	 * equipment.
	 * 
	 * @param inventoryIndex
	 *            The index position in Avatar's Inventory of the desired item.
	 */
	public void useAvatarItem(int inventoryIndex) {
		msm.getAvatar().use(inventoryIndex);
	}

	/**
	 * Drops the Avatar's item from the specified inventory index in the
	 * Avatar's inventory.
	 * 
	 * @param inventoryIndex
	 *            The index position in Avatar's Inventory of the desired item.
	 */
	public void dropAvatarItem(int inventoryIndex) {
		msm.getAvatar().dropFromInventory(inventoryIndex);
	}

	/**
	 * Returns a list of InventoryItems in the Avatar's inventory.
	 * 
	 * @param inventoryIndex
	 *            The index position in Avatar's Inventory of the desired item.
	 * @return List<InventoryItems> in Avatar's Inventory.
	 */
	public String[] getAvatarInventory() {
		// return msm.getAvatar().getInventory().items();
		String items[] = new String[10];

		for (int i = 0; i < items.length; i++) {
			InventoryItem ii = msm.getAvatar().getInventory().itemAtIndex(i);
			if (ii == null)
				items[i] = "---N/A---";
			else
				items[i] = ii.name();
		}

		return items;
	}

	/**
	 * 
	 * @return a String representation of the equipped weapon
	 */
	public String getAvatarEquippedWeapon() {
		if (msm.getAvatar().getInventory().equippedWeapon() != null)
			return msm.getAvatar().getInventory().equippedWeapon().name;

		return "---N/A---";
	}

	/**
	 * 
	 * @return a String representation of the equipped armor
	 */
	public String getAvatarEquippedArmor() {
		if (msm.getAvatar().getInventory().equippedArmor() != null)
			return msm.getAvatar().getInventory().equippedArmor().name;

		return "---N/A---";
	}

	/**
	 * Returns the Avatar's Stats as an array of integers. They correlate with
	 * the index positions of the array returned from getAvatarStatNames().
	 * 
	 * @return int[] of Stat values.
	 */
	public int[] getAvatarStats() {
		return msm.getAvatar().getStatValues();
	}

	/**
	 * Returns the Avatar's Stat names as an array of Strings. They correlate
	 * with the index postions of the array returned from getAvatarStats().
	 * 
	 * @return String[] of Stat names.
	 */
	public String[] getAvatarStatNames() {
		return msm.getAvatar().getStatNames();
	}

	/**
	 * Returns a 2D array of the best set of Tiles that fit the size of the
	 * "rectangle" passed in.
	 * 
	 * @param northWestCorner
	 *            Vector [x,y] of the top left corner of the rectangle.
	 * @param southEastCorner
	 *            Vector [x,y] of the bottom right corner of the rectangle.
	 * 
	 * @return 2D Array of Tiles of the given size.
	 */
	public Tile[][] getTiles(Vector northWestCorner, Vector southEastCorner) {
		return mg.getBestRect(northWestCorner, southEastCorner);
	}

	/**
	 * 
	 * @return the avatar's current position
	 */
	public Vector getAvatarPosition() {
		return msm.getAvatar().getCoordinate();
	}

	/**
	 * Call this to make the model tick. Used for components that depend on the
	 * passing of time.
	 */
	public void update() {
		if (!pause)
			msm.update();
		// msm.update();
	}

	public void isPaused(boolean b) {
		pause = b;
	}
}
