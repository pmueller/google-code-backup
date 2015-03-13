package model;

/**
 * Item allows for the grouping of conceptual items, so that they may be
 * rendered in a specific order (on top of / under Entities, Decals, AoEs, etc.
 * All Items must respond to a collision with an Entity.
 * 
 * @author M. Shahalam
 */
public abstract class Item extends MapStuff implements ICollidableWithEntity {
	public abstract String saveString(Vector v);
}
