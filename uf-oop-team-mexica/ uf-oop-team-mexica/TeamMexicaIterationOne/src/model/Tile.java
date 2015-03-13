package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * One unit of map space. Contains MapStuff.
 * 
 * @author Bobby
 * 
 */

public class Tile implements Representation {
	private Terrain terrain;
	private List<Entity> entityList;
	private List<AoE> aoeList;
	private List<Decal> decalList;
	private List<Item> itemList;

	/**
	 * Constructor
	 * 
	 * @param terr
	 *            the terrain
	 */
	public Tile(Terrain terr) {
		terrain = terr;
		entityList = new ArrayList<Entity>();
		aoeList = new ArrayList<AoE>();
		decalList = new ArrayList<Decal>();
		entityList = new ArrayList<Entity>();
		itemList = new ArrayList<Item>();
	}

	/**
	 * tests if the entity can move through this tile
	 * 
	 * @param collidingEntity
	 *            the entity to test with
	 * @return true if movement is allowed, false if movement is not allowed
	 */
	public Boolean handleCollisions(Entity collidingEntity) {
		Boolean allowMovement = true;
		allowMovement = (terrain.isTraversable() ? allowMovement : false);
		allowMovement = (collideWithList(entityList, collidingEntity) ? allowMovement
				: false);
		allowMovement = (collideWithList(itemList, collidingEntity) ? allowMovement
				: false);
		allowMovement = (collideWithList(aoeList, collidingEntity) ? allowMovement
				: false);
		return allowMovement;
	}

	/**
	 * collides the entity with every object in the provided targetList
	 * 
	 * @param targetList
	 *            the list of objects to collide with
	 * @param collidingEntity
	 *            the entity to collide
	 * @return true if the entity can move into the tile
	 */
	private Boolean collideWithList(
			List<? extends ICollidableWithEntity> targetList,
			Entity collidingEntity) {
		Boolean allowMovement = true;
		Iterator<? extends ICollidableWithEntity> iter = targetList.iterator();
		ICollidableWithEntity toCollide;
		while (iter.hasNext()) {
			toCollide = iter.next();
			allowMovement = (toCollide.collideWith(collidingEntity) ? allowMovement
					: false);
			if (toCollide.wantsToBeRemoved())
				iter.remove();
		}
		return allowMovement;
	}

	/**
	 * adds an AoE to the tile
	 * 
	 * @param newAoE
	 *            the AoE to add
	 */
	public void add(AoE newAoE) {
		aoeList.add(newAoE);
	}

	/**
	 * adds a decal to the tile
	 * 
	 * @param newDecal
	 *            the decal to add
	 */
	public void add(Decal newDecal) {
		decalList.add(newDecal);
	}

	/**
	 * adds an entity to the tile
	 * 
	 * @param newEntity
	 *            the entity to add
	 */
	public void add(Entity newEntity) {
		entityList.add(newEntity);
	}

	/**
	 * adds an item to the tile
	 * 
	 * @param newItem
	 *            the item to add
	 */
	public void add(Item newItem) {
		itemList.add(newItem);
		newItem.setCurrentTile(this);
	}

	/**
	 * removes the specified AoE
	 * 
	 * @param oldAoE
	 *            the AoE to remove
	 */
	public void remove(AoE oldAoE) {
		aoeList.remove(oldAoE);
	}

	/**
	 * removes the specified decal
	 * 
	 * @param oldDecal
	 *            the decal to remove
	 */
	public void remove(Decal oldDecal) {
		decalList.remove(oldDecal);
	}

	/**
	 * removes the specified entity
	 * 
	 * @param oldEntity
	 *            the entity to remove
	 */
	public void remove(Entity oldEntity) {
		entityList.remove(oldEntity);
	}

	/**
	 * removes the item specified
	 * 
	 * @param oldItem
	 *            the item to remove
	 */
	public void remove(Item oldItem) {
		itemList.remove(oldItem);
	}

	/**
	 * 
	 * @return the tile's terrain
	 */
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	 * sets the terrain
	 * 
	 * @param terr
	 *            the terrain to set
	 */
	public void setTerrain(Terrain terr) {
		this.terrain = terr;
	}

	/**
	 * @return the terrain's representation
	 */
	public String getRepresentation() {
		return terrain.getRepresentation();
	}

	/**
	 * 
	 * @return a list of the tile's decals
	 */
	public List<Decal> getDecals() {
		return decalList;
	}

	/**
	 * 
	 * @return a list of the tile's items
	 */
	public List<Item> getItems() {
		return itemList;
	}

	/**
	 * 
	 * @return a list of the tile's entities
	 */
	public List<Entity> getEntities() {
		return entityList;
	}

	/**
	 * 
	 * @return a list of the tile's AoE's
	 */
	public List<AoE> getAoEs() {
		return aoeList;
	}

	/**
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return
	 */
	public String saveString(int x, int y) {
		StringBuffer out = new StringBuffer();

		// write item info
		Iterator<Item> iterItem = itemList.iterator();
		Vector v = new Vector(x, y);
		while (iterItem.hasNext()) {
			out.append(iterItem.next().saveString(v));
		}

		// write AoE info
		Iterator<AoE> iterAoE = aoeList.iterator();
		while (iterAoE.hasNext()) {
			out.append(iterAoE.next().saveString(v));
		}

		// write entity info
		Iterator<Entity> iterEntity = entityList.iterator();
		while (iterEntity.hasNext()) {
			out.append(iterEntity.next().saveString(v));
		}

		// write decal info
		Iterator<Decal> iterDecal = decalList.iterator();
		while (iterDecal.hasNext()) {
			out.append(iterDecal.next().saveString(v));
		}

		return out.toString();
	}
}