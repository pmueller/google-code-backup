package model.influence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.*;

public class AngularInfluence extends ShapeOfInfluence {

    private RadialInfluence radial;
    private Vector origin, direction;
    private int nextLevel;

    public AngularInfluence(Vector origin, Vector direction, int numberOfLevels, boolean ho) {
        this.origin = origin;
        this.direction = direction;

        radial = new RadialInfluence(origin.subtract(direction), numberOfLevels + 1, ho);
        if (radial.hasNext()) {
            radial.next();
        } else {
            throw new RuntimeException(
                    "Problem in AngularInfluence constructor, probably in RadialInfluence class");
        }

        nextLevel = 0;
    }

    public boolean hasNext() {
        return radial.hasNext();
    }

    public List<Vector> next() {
        List<Vector> radList = radial.next();

        List<Vector> ret = new ArrayList<Vector>();
        
        // restrict the radial vectors to the constraints of the angular shape
        for (Vector v : radList) {
            if (direction.x == 0) {
                if (v.y != origin.y + nextLevel * direction.y) {
                    continue;
                }
                if (Math.abs(v.x) > origin.x + nextLevel) {
                    continue;
                }
            } else if (direction.x > 0) {
                if (v.x < origin.x) {
                    continue;
                }
            } else {
                if (v.x > origin.x) {
                    continue;
                }
            }

            if (direction.y == 0) {
                if (v.x != origin.x + nextLevel * direction.x) {
                    continue;
                }
                if (Math.abs(v.y) > origin.y + nextLevel) {
                    continue;
                }
            } else if (direction.y > 0) {
                if (v.y < origin.y) {
                    continue;
                }
            } else {
                if (v.y > origin.y) {
                    continue;
                }
            }
            ret.add(v);
        }

        nextLevel++;

        // TODO: bug fix
        if (nextLevel == 1) {
            List<Vector> temp = new ArrayList<Vector>();
            temp.add(origin);
            return temp;
        }

        return ret;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.radial = s.pullSaveable();
        this.origin = s.pullSaveable();
        this.direction = s.pullSaveable();
        this.nextLevel = s.pullInt();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("AngularInfluence");
        }

        s.push(this.radial, true);
        s.push(this.origin, true);
        s.push(this.direction, true);
        s.push(this.nextLevel);

        if (notSuperClass) {
            s.close();
        }
    }
}
