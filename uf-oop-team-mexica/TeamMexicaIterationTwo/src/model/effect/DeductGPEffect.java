package model.effect;

import java.io.IOException;
import model.Model;
import model.entity.Entity;
import model.stat.Stat;
import model.stat.modifier.*;
import util.SaverLoader;

public class DeductGPEffect extends Effect {

    public DeductGPEffect(int pow) {
        super(pow);
    }

    public DeductGPEffect() {
        super(0);
    }

    /**
     * Deducts the specified amount of gold one time.
     * @param target	The Entity being affected.
     **/
    public void applyToEntity(Entity target) {
        applyToEntity(target, 0);
    }

    public void applyToEntity(Entity target, Integer duration) {

        int oldValue = target.getStats().getEffectiveValueForName(Stat.STAT_GP);

        StatModifier cost = new StatModifierConstant(Stat.STAT_GP, this.getPower(), duration);
        target.addStatModifier(cost);

        int newValue = target.getStats().getEffectiveValueForName(Stat.STAT_GP);

        Model.getInstance().getLog().logStatChangedByDelta(target.getName(),
                Stat.STAT_GP, oldValue - newValue);

    }

    public Effect clone() {
        return new DeductGPEffect(this.getPower());
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("DeductGPEffect");
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
