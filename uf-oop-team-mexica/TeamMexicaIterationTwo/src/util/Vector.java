package util;
import java.io.*;

//========================================================//
public class Vector extends java.awt.Point implements Saveable {

    /**
     * Added this to get rid of the warning
     */
    private static final long serialVersionUID = 3047375105528794538L;
    public static final Vector NORTH = new Vector(0, -1);
    public static final Vector NORTHEAST = new Vector(1, -1);
    public static final Vector EAST = new Vector(1, 0);
    public static final Vector SOUTHEAST = new Vector(1, 1);
    public static final Vector SOUTH = new Vector(0, 1);
    public static final Vector SOUTHWEST = new Vector(-1, 1);
    public static final Vector WEST = new Vector(-1, 0);
    public static final Vector NORTHWEST = new Vector(-1, -1);

    public Vector(int x, int y) {
        super(x, y);
    }

    public Vector() {
        this(0, 0);
    }

    /**
     * Adds two vectors together
     * @param v
     * @return a new vector that is the sum of this and the specified vector
     */
    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y);
    }

    /**
     * Subtracts the specified vector from this
     * @param v
     * @return a new vector that is the difference of this and the specified vector
     */
    public Vector subtract(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y);
    }

    /**
     * Tests the specified vector and this for equality
     * @param toCompare
     * @return true if the x & y coordinates are both equal, false otherwise
     */
    public Boolean equals(Vector toCompare) {
        return (toCompare.x == this.x && toCompare.y == this.y);
    }

    public String toString() {
        if (this.equals(NORTH)) {
            return "N";
        }
        if (this.equals(NORTHEAST)) {
            return "NE";
        }
        if (this.equals(EAST)) {
            return "E";
        }
        if (this.equals(SOUTHEAST)) {
            return "SE";
        }
        if (this.equals(SOUTH)) {
            return "S";
        }
        if (this.equals(SOUTHWEST)) {
            return "SW";
        }
        if (this.equals(WEST)) {
            return "W";
        }
        if (this.equals(NORTHWEST)) {
            return "NW";
        }
        return "(" + x + "," + y + ")";
    }

    public Vector clone( Vector v ) {
        return new Vector( v.x, v.y );
    }

    public void load(SaverLoader s, boolean nSC) throws IOException {
        x = s.pullInt();
        y = s.pullInt();
        if(nSC)
            s.assertEnd();
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("Vector");
        }

        s.push(this.x);
        s.push(this.y);

        if (notSuperClass) {
            s.close();
        }
    }

}

//========================================================//
