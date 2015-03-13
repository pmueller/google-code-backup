package model.effect;

import java.io.IOException;
import model.entity.Entity;
import util.*;

public abstract class Effect implements Saveable {

    private int power;

    public Effect(int pow) {
        power = pow;
    }

    public Effect() {
        power = 0;
    }

    public void setPower(int pow) {
        power = pow;
    }

    public int getPower() {
        return power;
    }

    public abstract void applyToEntity(Entity target);

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Effect");
        }
        s.push(power);
        if (notSuperClass) {
            s.close();
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.power = s.pullInt();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public abstract String getRepresentation();
}
