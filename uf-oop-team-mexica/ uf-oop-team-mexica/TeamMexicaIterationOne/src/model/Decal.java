package model;

/**
 * A Decal is EyeCandy with a location and a representation.
 * 
 * @author M. Shahalam
 */
public class Decal extends MapStuff {

	/**
	 * Save string LoaderSaver uses to save Decals.
	 */
	public String saveString(Vector v) {
		return ("Decal{\n\tlocation: " + v.x + " " + v.y + "\n\t" + "rep: "
				+ getRepresentation() + "\n}\n");
	}
}