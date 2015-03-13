package model.effect;

import java.io.IOException;
import model.entity.Entity;
import util.SaverLoader;

public class SlowingEffect extends Effect {

    public SlowingEffect(int pow) {
        super(pow);
    }

    public void applyToEntity(Entity target) {
        target.addStatModifier(new model.stat.modifier.StatModifierConstant("Movement", -getPower(), 1));

    }

    public Effect clone() {
        return new SlowingEffect(this.getPower());
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("SlowingEffect");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public String getRepresentation() {
        return null;
    }
}
