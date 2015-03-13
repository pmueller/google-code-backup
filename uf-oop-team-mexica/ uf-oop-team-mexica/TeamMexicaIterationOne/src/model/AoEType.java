package model;

/**
 * AoEType defines an effect to do on an Entity. It can be enabled or disabled.
 * 
 * @author M. Shahalam
 */
public abstract class AoEType {
	private boolean enabled = false;

	/**
	 * Returns if the AoEType is currently enabled.
	 * 
	 * @return <code>true</code> if enabled, <code>false</code> if disabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Disables the AoEType and prevents it from doing its effect.
	 */
	public void disable() {
		enabled = false;
	}

	/**
	 * Enables the AoEType and lets it do its effect (when active).
	 */
	public void enable() {
		enabled = true;
	}

	/**
	 * Does effect on Entity if AoEType is enabled.
	 * 
	 * @param e
	 *            Entity to affect.
	 */
	public void affect(Entity e) {
		if (enabled)
			doEffect(e);
	}

	/**
	 * The effect to perform on the Entity.
	 * 
	 * @param e
	 *            Entity to do effect on.
	 */
	protected abstract void doEffect(Entity e);

	public abstract String toString();

	public abstract int getNum();

}
