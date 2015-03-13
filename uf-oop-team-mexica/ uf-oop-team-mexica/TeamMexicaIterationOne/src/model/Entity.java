package model;

/*
 Entity:
 An Entity is an object in the game that conceptually could represent a player, 
 an NPC, or an enemy. Entity's have stats, an inventory, and an occupation.
 All Entity's have the ability to move and to know and change their current position.
 Author: Jason
 Tester: Zach
 */
public class Entity extends MapStuff implements ICollidableWithEntity {
	// Constructors
	// ----------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public Entity() // default
	{
		myName = "Joe";
		livesLeft = 3;
		experience = 0;
		movement = 10;

		myInventory = new Inventory();

		myOccupation = new Smasher();
		myOccupation.setInitialStats(this);

		currentLife = maxLife();
		currentMana = maxMana();

		facing = Vector.SOUTH;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            the name of the entity
	 * @param occupation
	 *            the entity's occupation
	 */
	public Entity(String name, Occupation occupation) // without a start tile
	{
		myName = name;
		livesLeft = 3;
		experience = 0;
		movement = 10;

		myInventory = new Inventory();

		myOccupation = occupation;
		myOccupation.setInitialStats(this);

		currentLife = maxLife();
		currentMana = maxMana();

		facing = Vector.SOUTH;
	}

	// operations

	/**
	 * addExp will take an int and add it to the Entity's experience. If this
	 * causes the Entity to go up in level, levelUp() is called.
	 * 
	 * @param newExp
	 *            the amount of experience to be added
	 */
	public void addExp(int newExp) {
		// levels the Entity for each increment of EXP_PER_LEVEL
		while (newExp > EXP_PER_LEVEL) {
			experience += EXP_PER_LEVEL;
			newExp -= EXP_PER_LEVEL;
			levelUp();
		}
		// adds the remainder of the exp to the Entity and checks to see if
		// it needs to level up one more time
		int currentLevel = level();
		experience += newExp;
		if (level() > currentLevel)
			levelUp();
	}

	/**
	 * instantly levels up the entity by adding the necessary amount of exp
	 * 
	 */
	public void instantLevelUp() {
		addExp(EXP_PER_LEVEL);
	}

	/**
	 * internal method that levels up the entity by updating its stats
	 */
	private void levelUp() // in the UML this returns a boolean for some reason
	{
		myOccupation.handleLevelUp(this);
		takeDamage(-HP_PER_LEVEL);
	}

	/**
	 * takeDamage will be called by other objects when they do damage to this
	 * Entity take damage can also be used to heal an entity if a negative
	 * amount is entered
	 * 
	 * @param damage
	 *            the amount of damage taken by the entity
	 */
	public void takeDamage(int damage) {
		currentLife -= damage;

		if (currentLife > maxLife())
			currentLife = maxLife();

		if (currentLife <= 0) {
			currentLife = 0;
			handleDeath();
		}
	}

	/**
	 * resets the entity's Life and Mana sets wantsToBeRemoved to false
	 */
	public void respawn() {
		currentLife = maxLife();
		currentMana = maxMana();
		this.setWantsToBeRemoved(false);
	}

	/**
	 * instantly kills the entity by setting their Life to 0
	 */
	public void instantDeath() {
		currentLife = 0;
		handleDeath();
	}

	/**
	 * private method used to handle entity death decrements the amount of
	 * livesLeft if no more lives are left, the entity's inventory is dropped
	 */
	private void handleDeath() {
		if (--livesLeft < 0) // decrement livesLeft, then if no more livesLeft
		{
			// drop everything from inventory (keep equipment)
			int invSize = myInventory.size();
			for (int i = invSize - 1; i >= 0; --i) {
				dropFromInventory(i);
			}
		}
		// set its flag for being removed
		this.removeSelf(); // set wantsToBeRemoved to true
	}

	// inventory related stuff
	// -------------------------------------------------------------
	/*
	 * entity just forwards commands to its inventory
	 */

	/**
	 * equip(Armor armor) will unequip the current armor and equip the given
	 * armor
	 * 
	 * @param armor
	 *            the armor to be equipped
	 */
	public boolean equip(Armor armor) {
		unequip(myInventory.equippedArmor());
		myInventory.setEquippedArmor(armor);

		return true; // what is this for?
	}

	/**
	 * unequips the current weapon and equips the given weapon
	 * 
	 * @param weapon
	 *            the weapon to be equipped
	 */
	public boolean equip(Weapon weapon) {
		unequip(myInventory.equippedWeapon());
		myInventory.setEquippedWeapon(weapon);

		return true; // what is this for?
	}

	/**
	 * unequips the currently equipped armor
	 * 
	 * @param armor
	 *            not used
	 */
	public boolean unequip(Armor armor) {
		// armor is not even used
		myInventory.unEquipArmor();
		return true; // what is this for?
	}

	/**
	 * unequips the currently equipped weapon
	 * 
	 * @param weapon
	 *            not used
	 */
	public boolean unequip(Weapon weapon) {
		// weapon is not even used
		myInventory.unEquipWeapon();
		return true; // what is this for?
	}

	/**
	 * uses the inventory item at the specified index
	 * 
	 * @param invIndex
	 *            the index of the item to be used
	 */
	public void use(int invIndex) {
		if (invIndex < myInventory.size() && invIndex >= 0) {
			myInventory.itemAtIndex(invIndex).useOnEntity(this);
		}
	}

	/**
	 * adds an item to the entity's inventory
	 * 
	 * @param itemToAdd
	 *            the item that is being added to the inventory
	 */
	public boolean addToInventory(InventoryItem itemToAdd) {
		return myInventory.addItem(itemToAdd);
	}

	/**
	 * removes the specified item from the entity's inventory
	 * 
	 * @param itemToRemove
	 *            the item that is going to be removed
	 */
	public InventoryItem removeFromInventory(InventoryItem itemToRemove) {
		return myInventory.removeItem(itemToRemove);
	}

	/**
	 * drops an item from the inventory pointed to by the given index
	 * 
	 * @param invIndex
	 *            the index of the item to be dropped
	 */
	public void dropFromInventory(int invIndex) {
		myInventory.dropItemAtIndexOntoTile(invIndex, this.getCurrentTile());
	}

	// ----------------------------------------------------------------------------------------

	/**
	 * move will handle movement and collisions it will also update facing and
	 * collisions
	 */
	public void moveAndInteract(Tile destination, Vector direction,
			Vector newCoordinate) {
		facing = direction;

		if (destination != null) {
			if (destination.handleCollisions(this)) { // handleCollisions
														// returns true if the
														// entity can still move
				myCoordinate = newCoordinate;

				this.getCurrentTile().remove(this);
				this.setCurrentTile(destination);
				destination.add(this);
			}
		}
	}

	/**
	 * canMoveToTile checks to see if the tile is passable or not rturns true if
	 * the tile is passable
	 */
	public boolean canMoveToTile(Tile destination) {
		return destination.getTerrain().isTraversable();
	}

	/**
	 * returns an array of strings containing the names of each stat
	 */
	public String[] getStatNames() {
		String[] statNames = new String[15];

		statNames[0] = "Lives Left";
		statNames[1] = "Strength";
		statNames[2] = "Agility";
		statNames[3] = "Intellect";
		statNames[4] = "Hardiness";
		statNames[5] = "Movement";
		statNames[6] = "Offensive Rating";
		statNames[7] = "Defensive Rating";
		statNames[8] = "Armor Rating";
		statNames[9] = "Current Life";
		statNames[10] = "Max Life";
		statNames[11] = "Current Mana";
		statNames[12] = "Max Mana";
		statNames[13] = "Experience";
		statNames[14] = "Level";

		return statNames;
	}

	/**
	 * returns an array of ints corresponding to the array of strings returned
	 * by getStatNames. the ints are the values of each stat.
	 */
	public int[] getStatValues() {
		int[] statValues = new int[15];

		statValues[0] = livesLeft;
		statValues[1] = strength;
		statValues[2] = agility;
		statValues[3] = intellect;
		statValues[4] = hardiness;
		statValues[5] = movement;
		statValues[6] = offense();
		statValues[7] = defense();
		statValues[8] = armor();
		statValues[9] = currentLife;
		statValues[10] = maxLife();
		statValues[11] = currentMana;
		statValues[12] = maxMana();
		statValues[13] = experience;
		statValues[14] = level();

		return statValues;
	}

	// inherited operations
	// --------------------------------------------------------------

	/**
	 * colliding with another entity will block movement
	 */
	public boolean collideWith(Entity movingEntity) {
		return false; // stop movement
	}

	/**
	 * defers getting the representation to the entity's occupation
	 */
	public String getRepresentation() {
		return myOccupation.getRepresentation();
	}

	// ------------------------------------------------------------------------------------------

	// gets
	// ----------------------------------------------------------------------------
	/**
	 * @return the name
	 */
	public String getName() {
		return myName;
	}

	/**
	 * 
	 * @return the lives left
	 */
	public int getLivesLeft() {
		return livesLeft;
	}

	/**
	 * 
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * 
	 * @return the agility
	 */
	public int getAgility() {
		return agility;
	}

	/**
	 * 
	 * @return the intellect
	 */
	public int getIntellect() {
		return intellect;
	}

	/**
	 * 
	 * @return the hardiness
	 */
	public int getHardiness() {
		return hardiness;
	}

	/**
	 * 
	 * @return the experience
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * 
	 * @return the movement
	 */
	public int getMovement() {
		return movement;
	}

	/**
	 * 
	 * @return the current life
	 */
	public int getCurrentLife() {
		return currentLife;
	}

	/**
	 * 
	 * @return the current mana
	 */
	public int getCurrentMana() {
		return currentMana;
	}

	/**
	 * 
	 * @return a reference the entity's inventory
	 */
	public Inventory getInventory() {
		return myInventory;
	}

	/**
	 * 
	 * @return the entity's occupation
	 */
	public Occupation getOccupation() {
		return myOccupation;
	}

	/**
	 * 
	 * @return the entity's facing
	 */
	public Vector getFacing() {
		return facing;
	}

	/**
	 * 
	 * @return the entity's coordinate
	 */
	public Vector getCoordinate() {
		return myCoordinate;
	}

	// sets
	// ------------------------------------------------------------------------------

	public void setName(String value) {
		myName = value;
	}

	public void setLivesLeft(int value) {
		livesLeft = value;
	}

	public void setStrength(int value) {
		strength = value;
	}

	public void setAgility(int value) {
		agility = value;
	}

	public void setIntellect(int value) {
		intellect = value;
	}

	public void setHardiness(int value) {
		hardiness = value;
	}

	public void setExperience(int value) {
		experience = value;
	}

	public void setMovement(int value) {
		movement = value;
	}

	public void setCurrentLife(int value) {
		if (value < 0)
			currentLife = 0;
		else if (value > maxLife())
			currentLife = maxLife();
		else
			currentLife = value;
	}

	public void setCurrentMana(int value) {
		if (value < 0)
			currentMana = 0;
		else if (value > maxMana())
			currentMana = maxMana();
		else
			currentMana = value;
	}

	public void setFacing(Vector direction) {
		facing = direction;
	}

	public void setCoordinate(Vector coord) {
		myCoordinate = coord;
	}

	// derived attributes
	// --------------------------------------------------------------
	public int maxLife() {
		return ((level() * HP_PER_LEVEL) + (hardiness * HP_PER_HARDINESS) + START_HP);
	}

	public int maxMana() {
		return ((level() * MP_PER_LEVEL) + (intellect * MP_PER_INTELLECT) + START_MP);
	}

	public int level() {
		return (experience / EXP_PER_LEVEL) + 1;
	}

	public int offense() {
		if (myInventory.equippedWeapon() != null)
			return myInventory.equippedWeapon().offenseBonus() + strength
					+ (level() * 5);
		else
			return strength + (level() * 5);
	}

	public int defense() {
		return (agility + (level() * 5));
	}

	public int armor() {
		if (myInventory.equippedArmor() != null)
			return (myInventory.equippedArmor().armorBonus() + hardiness);
		else
			return hardiness;
	}

	public boolean isDead() {
		if (currentLife <= 0)
			return true;
		else
			return false;
	}

	public String saveString(Vector v) {
		return "";
	}

	// private properties
	// ---------------------------------------------------------------
	private String myName; // the Entity's name, as seen in the game
	private int livesLeft; // the number of times an Entity can be respawned
	private int strength; // used to derive offense
	private int agility; // used to derive defense
	private int intellect; // used to derive maxMana
	private int hardiness; // used to derive maxLife and armor
	private int experience; // used to derive level
	private int movement; // the rate at which an Entity moves unimpeded

	private int currentLife; // how close the player is to death. Cannot exceed
								// maxLife;
	private int currentMana; // spent when casting spells or using abilities

	private Inventory myInventory; // a reference to this Entity's Inventory
	private Occupation myOccupation; // a reference to this Entity's Occupation

	private Vector facing;
	private Vector myCoordinate; // the coordinate of the Entity

	// constants
	// --------------------------------------------------------------------------
	static final int EXP_PER_LEVEL = 1000;
	static final int HP_PER_LEVEL = 100;
	static final int MP_PER_LEVEL = 50;
	static final int HP_PER_HARDINESS = 10;
	static final int MP_PER_INTELLECT = 20;
	static final int START_HP = 500;
	static final int START_MP = 300;
}