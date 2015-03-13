package model.influence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.*;

/**
 *   Currently not really needed, same functionality inside LinearInfluece
 *   and Angular and Radial when created with numberOfLevels = 0.
 */
public class SingularInfluence extends ShapeOfInfluence implements Saveable {

    private boolean hasNext;
    private Vector origin;

    public SingularInfluence(Vector origin) {
        this.origin = origin;
        hasNext = true;
    }

    public SingularInfluence() {
    }

    public boolean hasNext() {
        return hasNext;
    }

    public List<Vector> next() {
        hasNext = false;

        List<Vector> ret = new ArrayList<Vector>();
        ret.add(origin);
        return ret;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.hasNext = s.pullBool();
        this.origin = s.pullSaveable();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("SingularInfluence");
        }

        s.push(this.hasNext);
        s.push(this.origin, true);

        if (notSuperClass) {
            s.close();
        }
    }
}
