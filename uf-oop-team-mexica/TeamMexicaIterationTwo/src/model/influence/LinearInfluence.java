package model.influence;

import java.util.ArrayList;
import java.util.List;
import util.*;
import java.io.*;

public class LinearInfluence extends ShapeOfInfluence {

    private Vector origin, direction;
    private int numberOfLevels, nextLevel;

    /**
     * numberOfLevels maybe should be more than 0, otherwise use a SingularInfluence?
     * Iteration does not include the origin.
     *
     * @param origin
     * @param direction
     * @param numberOfLevels '0' is origin+direction, '1' is origin+2*direction, etc.
     */
    public LinearInfluence(Vector origin, Vector direction, int numberOfLevels) {
        this.origin = origin;
        this.direction = direction;
        this.numberOfLevels = numberOfLevels;

        nextLevel = 0;
    }

    public boolean hasNext() {
        return nextLevel <= numberOfLevels;
    }

    public List<Vector> next() {
        Vector current = (Vector) origin.clone();
        for (int i = 0; i <= nextLevel; i++) {
            current = current.add(direction);
            // System.out.println( " linear adding shit ");
            //System.out.println( current.toString() );
        }
        nextLevel++;

        List<Vector> ret = new ArrayList<Vector>();
        ret.add((Vector) current.clone());
        return ret;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.origin = s.pullSaveable();
        this.direction = s.pullSaveable();
        this.numberOfLevels = s.pullInt();
        this.nextLevel = s.pullInt();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("LinearInfluence");
        }

        s.push(this.origin, true);
        s.push(this.direction, true);
        s.push(this.numberOfLevels);
        s.push(this.nextLevel);

        if (notSuperClass) {
            s.close();
        }
    }
}
