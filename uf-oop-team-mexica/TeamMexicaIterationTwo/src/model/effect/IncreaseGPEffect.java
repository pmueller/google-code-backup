package model.effect;

import java.io.IOException;
import model.entity.Entity;
import model.stat.Stat;
import model.stat.modifier.StatModifier;
import model.stat.modifier.StatModifierConstant;
import util.SaverLoader;

public class IncreaseGPEffect extends Effect {

    public IncreaseGPEffect(int pow) {
        super(pow);
    }

    public IncreaseGPEffect() {
    	super(0);
    }

    /**
     * Adds the specified amount of gold one time.
     * @param target	The Entity being affected.
     **/
    public void applyToEntity(Entity target) {
        applyToEntity(target,0);
    }
    
    /**
     * Adds the specified amount of gold once per frame for the number of frames specified.
     * @param target
     * @param duration
     */
    public void applyToEntity(Entity target, Integer duration) {
        StatModifier cost = new StatModifierConstant(Stat.STAT_GP, this.getPower(), duration);
        target.addStatModifier(cost);
    }

    public Effect clone() {
        return new DeductGPEffect(this.getPower());
    }


    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("IncreaseGPEffect");
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
