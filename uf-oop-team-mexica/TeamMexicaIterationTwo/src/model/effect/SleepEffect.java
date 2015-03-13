package model.effect;

import java.io.IOException;
import model.Model;
import model.entity.Entity;
import util.Saveable;
import util.SaverLoader;

public class SleepEffect extends Effect implements Saveable {

    public SleepEffect(int power) {
        setPower(power);
    }

    public SleepEffect() {
        super();
    }

    public void applyToEntity(Entity target) {
        Model.getInstance().getLog().logGeneral( target.getName() + " is a sleepy head." );

        target.addToCoolDownTimer(getPower());
    }

    public String getRepresentation() {
        return "Sleep";
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("SleepEffect");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public Effect clone() {
        return new SleepEffect(this.getPower());
    }
}
