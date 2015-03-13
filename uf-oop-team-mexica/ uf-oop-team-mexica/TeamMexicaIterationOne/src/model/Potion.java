package model;

/**
 * Potions are OneShotItems that increase your health by 300 points.
 * 
 * @author M. Shahalam
 */
public class Potion extends OneshotItem {

	/**
	 * Creates a new Potion with the given representation.
	 * 
	 * @param rep
	 *            Representation for the potion.
	 */
	public Potion(String rep) {
		setRepresentation(rep);
	}

	/**
	 * Increases the health of the Entity by 300.
	 */
	protected void doInteraction(Entity e) {
		e.setCurrentLife(e.getCurrentLife() + 300);
	}

	/**
	 * @return string LoaderSaver uses to save Potions.
	 */
	public String saveString(Vector v) {
		StringBuffer out = new StringBuffer();
		out.append("OneShotItem{\n");
		out.append("\tlocation: " + v.x + " " + v.y + "\n");
		out.append("\ttype: potion\n");
		out.append("\trep: " + getRepresentation() + "\n");
		out.append("}\n");
		return out.toString();
	}

}
