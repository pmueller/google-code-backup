package model;

/**
 * An AoE (Area of Effect) is triggered when an Entity walks into a Tile with an
 * AoE. An AoE has a AoEType, which defines the specific interactoin with the
 * Entity. The AoEType can do its effect as long as the Entity stays on the
 * AoE's Tile.
 * 
 * @author M. Shahalam
 */
public class AoE extends MapStuff implements ICollidableWithEntity {
	private boolean triggered = false;
	private AoEType aoeType;
	private Entity victim;

	/**
	 * Creates a new AoE.
	 * 
	 * @param aoeType
	 *            The AoEType to be used by the AoE.
	 */
	public AoE(AoEType aoeType) {
		this.aoeType = aoeType;
		aoeType.enable();
	}

	/**
	 * Creates an AoE from the template of another AoE. Both AoE's will have the
	 * same AoEType and representation.
	 * 
	 * @param copy
	 *            The AoE to copy.
	 */
	public AoE(AoE copy) {
		triggered = false;
		this.aoeType = copy.aoeType;
		setRepresentation(copy.getRepresentation());
	}

	/**
	 * Collision with an AoE by an Entity. If the AoE is enabled, will trigger
	 * the AoE and the Entity will incur the effect of the AoE will it leaves or
	 * the AoE is disabled.
	 * 
	 * @return <code>true</code> if the AoE is passable by the Entity.
	 */
	public boolean collideWith(Entity e) {
		if (aoeType.isEnabled() && !triggered) {
			triggered = true;
			victim = e;
			System.out.println("AoE been triggered :)");
		}
		return true;
	}

	/**
	 * Ends the AoE's effect on the Entity.
	 */
	private void endEffect() {
		triggered = false;
		victim = null;
		System.out.println("no longer triggered :(");
	}

	/**
	 * The AoE executes its effect on the Entity which triggered it, if the
	 * victim is still on the Tile.
	 */
	public final void update() {

		// disabled AoETypes are removed from the map
		if (!aoeType.isEnabled()) {
			this.setWantsToBeRemoved(true);
			this.currentTile.remove(this);
		}

		if (!triggered)
			return;
		if (this.getCurrentTile().getEntities().contains(victim)) {
			aoeType.affect(victim);
		} else
			endEffect();
	}

	/**
	 * String used by LoaderSaver to save AoE's state.
	 */
	public String saveString(Vector v) {
		return "AoE{\n\ttype: " + aoeType.toString() + "\n\tlocation: " + v.x
				+ " " + v.y + "\n\tnum: " + aoeType.getNum() + "\n\trep: "
				+ getRepresentation() + "\n}\n";
	}
}
