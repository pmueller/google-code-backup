package model.ability;

import java.io.IOException;
import util.SaverLoader;
import util.Vector;

public class DetectAndRemoveTrap extends Ability {

    public DetectAndRemoveTrap() {
        super("DetectAndRemoveTrap");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {

    }

    public Ability clone() throws CloneNotSupportedException {
        return new DetectAndRemoveTrap();
    }

    public void initAbility() {
        setManaCost(50);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("DetectAndRemoveTrap");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
