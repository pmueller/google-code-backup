package model;

/**
 * InteractiveItems respond to collision with Entitys and do something kinky.
 * They can be enabled or disabled.
 * 
 * @author M. Shahalam
 */
public abstract class InteractiveItem extends Item {
	private boolean enabled = true;

	/**
	 * Disables the InteractiveItem, so it won't do anything kinky when the
	 * Entity collides with it.
	 */
	protected void disable() {
		enabled = false;
	}

	/**
	 * Responds to collision with Entity. If the InteractiveItem is enabled, it
	 * will do something.
	 * 
	 * @return <code>false</code> indicating InteractiveItem is not passable.
	 */
	public boolean collideWith(Entity e) {
		if (enabled) {
			doInteraction(e);
		}
		return false;
	}

	/**
	 * Defines the interaction with an Entity. Anything can happen. Brace
	 * yourself.
	 * 
	 * @param e
	 *            Entity involved in the interaction.
	 */
	protected abstract void doInteraction(Entity e);
}
