package model;

import java.util.*;

/**
 * Handles MapStuff ( Avatar and AoE's ).
 * 
 * @author M. Shahalam
 */
class MapStuffManager {
	private Entity avatar;
	private Tile avatarSpawnTile;
	private Vector avatarSpawnLocation;
	private List<AoE> aoeList;

	/**
	 * Create a new MapStuffManager.
	 */
	public MapStuffManager() {
		avatar = null;
		avatarSpawnTile = null;
		aoeList = new ArrayList<AoE>();
	}

	/**
	 * Adds Avatar to MapStuffManager, must add Avatar after setting it's
	 * location first.
	 * 
	 * @param avatar
	 *            The Avatar for the game.
	 */
	public void addAvatar(Entity avatar) {
		this.avatar = avatar;
		this.avatar.setCurrentTile(avatarSpawnTile);

		avatarSpawnTile.remove(avatar);
		avatarSpawnTile.add(avatar);

		this.avatar.setFacing(Vector.SOUTH);
		this.avatar.setCoordinate(avatarSpawnLocation);
		// spawnAvatar();
	}

	/**
	 * replaces the avatar on the map with the specified avatar
	 * 
	 * @param avatar
	 *            the avatar to insert into the map
	 */
	public void replaceAvatar(Entity avatar) {
		// removing current avatar
		if (avatar != null)
			if (avatar.getCurrentTile() != null) {
				Tile tile = this.avatar.getCurrentTile();
				tile.remove(avatar);
			}
		this.avatar = avatar;
		this.avatar.setFacing(Vector.SOUTH);
	}

	/**
	 * Gets a reference to the Avatar.
	 * 
	 * @return reference to the Avatar.
	 */
	public Entity getAvatar() {
		return avatar;
	}

	/**
	 * Returns the Avatar's starting location (where it will respawn) as a
	 * Vector [x,y]
	 * 
	 * @return Vector representing Avatar's starting (respawn) position.
	 */
	public Vector getAvatarSpawnLocation() {
		return avatarSpawnLocation;
	}

	/**
	 * Sets the Avatar's starting (respawn) Tile.
	 * 
	 * @param startingTile
	 *            Starting (respawn) Tile for Avatar.
	 */
	public void setAvatarSpawnTile(Tile spawnTile) {
		avatarSpawnTile = spawnTile;
	}

	/**
	 * Sets the Avatar's starting (respawn) coordinates, as a Vector [x,y].
	 * 
	 * @param startingLocation
	 *            Vector representing the Avatar's starting location.
	 */
	public void setAvatarSpawnLocation(Vector spawnLocation) {
		avatarSpawnLocation = spawnLocation;
	}

	/**
	 * Adds an AoE to the list, must do this for AoE's to work properly.
	 * 
	 * @param aoe
	 *            AoE to add.
	 */
	public void addAoE(AoE aoe) {
		aoeList.add(aoe);
	}

	/**
	 * Removes an AoE from the list.
	 * 
	 * @param aoe
	 *            AoE to remove.
	 */
	public void removeAoE(AoE aoe) {
		aoeList.remove(aoe);
	}

	/**
	 * Checks to see if an AoE object is currently on the map.
	 * 
	 * @param aoe
	 *            The AoE object that is to be searched for.
	 * @return <code>true</code> if the AoE exists on the map.
	 */
	public boolean containsAoE(AoE aoe) {
		return aoeList.contains(aoe);
	}

	/**
	 * (Re)spawns the Avatar on its starting location.
	 */
	private void spawnAvatar() {
		avatar.respawn();

		avatar.setCurrentTile(avatarSpawnTile);
		avatarSpawnTile.remove(avatar);
		avatarSpawnTile.add(avatar);
		avatar.setCoordinate(avatarSpawnLocation);
		this.avatar.setFacing(Vector.SOUTH);
	}

	/**
	 * Move an entity in a direction, the direction should be one of the eight
	 * constant Vector directions (Vector.NORTH, Vector.NORTHEAST, etc).
	 * 
	 * @param entity
	 *            Entity to move.
	 * @param vectorDirection
	 *            Direction in which to move.
	 */
	public void moveEntity(MapGrid mg, Entity entity, Vector vectorDirection) {
		Vector position = vectorDirection.add(entity.getCoordinate());
		Tile t = mg.getTileAt(position);
		entity.moveAndInteract(t, vectorDirection, position);
	}

	/**
	 * Makes the AoE's tick tick tick, and respawns Avatar if it is dead.
	 */
	public void update() {
		Iterator<AoE> iter = aoeList.iterator();

		while (iter.hasNext()) {
			AoE aoe = iter.next();
			if (aoe.wantsToBeRemoved())
				iter.remove();
			else
				aoe.update();
		}

		if (avatar != null) {
			if (avatar.wantsToBeRemoved() || avatar.isDead()) {
				avatar.getCurrentTile().remove(avatar);
				avatarSpawnTile.add(avatar);
				spawnAvatar();
			}
		}
	}

}
