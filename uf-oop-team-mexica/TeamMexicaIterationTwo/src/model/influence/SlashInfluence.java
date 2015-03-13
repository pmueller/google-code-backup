package model.influence;

import java.io.IOException;
import java.util.List;
import util.SaverLoader;
import util.Vector;

public class SlashInfluence extends ShapeOfInfluence {

    private Vector origin, direction;
    private AngularInfluence angular;
    private boolean hasNext;
    
    public SlashInfluence(Vector origin, Vector direction, boolean ho) {
        this.origin = origin;
        this.direction = direction;
        angular = new AngularInfluence(origin, direction, 1, ho);
        angular.next();
        hasNext = true;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public List<Vector> next() {
        List<Vector> ret = angular.next(); // the 2 corner Vectors (or all 3 for sides)
        if (!ret.contains(origin.add(direction))) {
            ret.add(origin.add(direction));
        }

        hasNext = false;
        return ret;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.origin = s.pullSaveable();
        this.direction = s.pullSaveable();
        this.angular = s.pullSaveable();
        this.hasNext = s.pullBool();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("SlashInfluence");
        }

        s.push(this.origin, true);
        s.push(this.direction, true);
        s.push(this.angular, true);
        s.push(this.hasNext);

        if (notSuperClass) {
            s.close();
        }
    }
}
