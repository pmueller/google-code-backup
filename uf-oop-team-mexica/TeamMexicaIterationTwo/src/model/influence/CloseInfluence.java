package model.influence;

import java.io.IOException;
import java.util.ArrayList;
import util.SaverLoader;
import util.Vector;

public class CloseInfluence extends ShapeOfInfluence {

    private Vector origin, direction;
    private boolean hasNext;

    public CloseInfluence(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
        hasNext = true;
    }

    public CloseInfluence() {
    }

    public boolean hasNext() {
        return hasNext;
    }

    public ArrayList<Vector> next() {
        ArrayList<Vector> closeVectors = new ArrayList<Vector>();
        Vector v = origin.add(direction);
        closeVectors.add(v);

        if (direction == Vector.NORTH || direction == Vector.SOUTH) {
            closeVectors.add(v.add(Vector.EAST));
            closeVectors.add(v.add(Vector.WEST));
        } else if (direction == Vector.EAST || direction == Vector.WEST) {
            closeVectors.add(v.add(Vector.NORTH));
            closeVectors.add(v.add(Vector.SOUTH));
        } else if (direction == Vector.NORTHEAST) {
            closeVectors.add(origin.add(Vector.NORTH));
            closeVectors.add(origin.add(Vector.EAST));
        } else if (direction == Vector.NORTHWEST) {
            closeVectors.add(origin.add(Vector.NORTH));
            closeVectors.add(origin.add(Vector.WEST));
        } else if (direction == Vector.SOUTHEAST) {
            closeVectors.add(origin.add(Vector.SOUTH));
            closeVectors.add(origin.add(Vector.EAST));
        } else if (direction == Vector.SOUTHWEST) {
            closeVectors.add(origin.add(Vector.SOUTH));
            closeVectors.add(origin.add(Vector.WEST));
        }

        hasNext = false;
        return closeVectors;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        origin = s.pullSaveable();
        direction = s.pullSaveable();
        hasNext = s.pullBool();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("CloseInfluence");
        }
        s.push(origin, true);
        s.push(direction, true);
        s.push(hasNext);

        if (notSuperClass) {
            s.close();
        }

    }
}
