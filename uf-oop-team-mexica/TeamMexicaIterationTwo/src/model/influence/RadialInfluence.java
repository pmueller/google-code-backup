package model.influence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.*;

public class RadialInfluence extends ShapeOfInfluence implements Saveable {

    private int nextLevel, numberOfLevels;
    private Vector origin;
    private boolean hitsOrigin;
    // 'global' because it's easier to type it this way for now..
    private ArrayList<Vector> temp;
    private Vector current;

    /**
     * numberOfLevels should be more than 0, otherwise use a SingularInfluence.
     *
     * @param origin
     * @param numberOfLevels
     */
    public RadialInfluence(Vector origin, int numberOfLevels, boolean ho) {
        this.origin = origin;
        this.hitsOrigin = ho;
        this.numberOfLevels = numberOfLevels;
        this.nextLevel = 0;
    }

    public boolean hasNext() {
        return nextLevel <= numberOfLevels;
    }

    public List<Vector> next() {
        temp = new ArrayList<Vector>();
        current = new Vector(origin.x, origin.y);

        // find the relative vectors
        if (nextLevel == 0) {
            ArrayList<Vector> ret = new ArrayList<Vector>();
            if (hitsOrigin) ret.add(origin);
            nextLevel++;
            return ret;
        } else if (nextLevel == 1) {
            n(1);
            e(1);
            s(2);
            w(2);
            n(2);

        } else if (nextLevel == 2) {
            startN(2);
            e(1);
            se(1);
            s(2);
            sw(1);
            w(2);
            nw(1);
            n(2);
            ne(1);
        } else {
            int i = nextLevel;
            startN(i);
            e(i - 1);
            s(1);
            e(1);
            s(2 * (i - 1));
            w(1);
            s(1);
            w(2 * (i - 1));
            n(1);
            w(1);
            n(2 * (i - 1));
            e(1);
            n(1);
            e(i - 2);
        }

        ArrayList<Vector> ret = new ArrayList<Vector>();
        for (Vector v : temp) {
            ret.add(v);
        }
        temp = null;
        current = null;
        nextLevel++;
        return ret;
    }

    private void startN(int num) {
        for (int i = 0; i < num; i++) {
            current = current.add(Vector.NORTH);
        }
        temp.add(current);
    }

    private void dirAdd(Vector dir, int num) {
        for (int i = 0; i < num; i++) {
            current = current.add(dir);
            temp.add(current);
        }
    }

    private void n(int i) {
        dirAdd(Vector.NORTH, i);
    }

    private void ne(int i) {
        dirAdd(Vector.NORTHEAST, i);
    }

    private void e(int i) {
        dirAdd(Vector.EAST, i);
    }

    private void se(int i) {
        dirAdd(Vector.SOUTHEAST, i);
    }

    private void s(int i) {
        dirAdd(Vector.SOUTH, i);
    }

    private void sw(int i) {
        dirAdd(Vector.SOUTHWEST, i);
    }

    private void w(int i) {
        dirAdd(Vector.WEST, i);
    }

    private void nw(int i) {
        dirAdd(Vector.NORTHWEST, i);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.nextLevel = s.pullInt();
        this.numberOfLevels = s.pullInt();
        this.origin = s.pullSaveable();
        this.temp = s.pullList();
        this.current = s.pullSaveable();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("RadialInfluence");
        }

        s.push(this.nextLevel);
        s.push(this.numberOfLevels);
        s.push(this.origin, true);
        s.push(this.temp);
        s.push(this.current, true);

        if (notSuperClass) {
            s.close();
        }
    }
}
