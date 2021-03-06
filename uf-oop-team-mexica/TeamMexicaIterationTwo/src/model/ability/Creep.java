package model.ability;

import java.io.IOException;
import util.SaverLoader;
import util.Vector;

public class Creep extends Ability {

    public Creep() {
        super("Creep");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {

    }

    public Ability clone() throws CloneNotSupportedException {
        return new Creep();
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
            s.start("Creep");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
