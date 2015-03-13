package model.mapstuff;

import java.io.IOException;
import util.Vector;
import util.Saveable;
import util.SaverLoader;

//========================================================\\
abstract public class MapStuff implements Saveable {

    private String representation;
    private String name;
    private Vector position;
    private Vector orientation;

    /**
     * Default constructor, creates a new MapStuff with a default name
     */
    public MapStuff() {
        this("Unnamed MapStuff");
    }

    /**
     * Constructor: sets the name, and initial orientation facing down
     * @param name
     */
    public MapStuff(String name) {
        orientation = new Vector(0, -1);
        this.name = name;
    }

    /**
     *
     * @return the position
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * Sets the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the position
     * @param position
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     *
     * @return the orientation
     */
    public Vector getOrientation() {
        return orientation;
    }

    /**
     * Sets the orientation
     * @param v
     */
    public void setOrientation(Vector v) {
        orientation = v;
    }

    /**
     *
     * @return the representation
     */
    public String getRepresentation() {
        return representation;
    }

    /**
     * Sets the representation
     * @param s
     */
    public void setRepresentation(String s) {
        representation = s;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.representation = s.pullString();
        this.name = s.pullString();
        this.position = s.pullSaveable();
        this.orientation = s.pullSaveable();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("MapStuff");
        }

        s.push(this.representation);
        s.push(this.name);
        s.push(this.position, true);
        s.push(this.orientation, true);

        if (notSuperClass) {
            s.close();
        }
    }
}

//========================================================\\

