package model.ability;

import java.io.IOException;
import util.SaverLoader;
import util.Vector;

public class Observation extends Ability {

    public Observation() {
        super("Observation");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {

    }

    public Ability clone() throws CloneNotSupportedException {
        return new Observation();
    }

    public void initAbility() {
        
    }

    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("Observation");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
