package model.effect;

import java.io.IOException;
import model.Model;
import model.entity.Entity;
import model.stat.Stat;
import util.SaverLoader;

public class HealEffect extends Effect {

    public HealEffect(int pow) {
        super(pow);
    }

    public HealEffect() {
        
    }

    public void applyToEntity(Entity target) {
        /*
        StatModifierConstant heal = new StatModifierConstant(Stat.STAT_HP, this.getPower(), -1);
        target.addStatModifier(heal);
         */
        int HP = target.getStats().getEffectiveValueForName(Stat.STAT_HP);
        int oldHealth = HP;
        HP += getPower();
        int maxHealth = target.getStats().getEffectiveValueForName(Stat.STAT_LIFE);
        if (HP > maxHealth) {
            HP = maxHealth;
        }

        target.getStats().setHP(HP);
        //System.out.println("gained HP from heal effect");


        Model.getInstance().getLog().logStatChangedByDelta(target.getName(),
                Stat.STAT_HP, HP - oldHealth);
        

    }

    public Effect clone() {
        return new HealEffect(this.getPower());
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("HealEffect");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public String getRepresentation() {
        return "";
    }
}
