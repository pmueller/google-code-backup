package model.effect;

import java.io.IOException;
import java.util.Random;
import model.Model;
import model.entity.Entity;
import model.stat.Stat;
import util.Saveable;
import util.SaverLoader;

public class DamageEffect extends Effect implements Saveable {

    public DamageEffect(int pow) {

        super(pow);
        System.out.println(pow);
    }

    public DamageEffect() {
        super();
    }

    public void applyToEntity(Entity target) {
        int def = target.getEffectiveStat(Stat.STAT_DEFENSE);
        int arm = target.getEffectiveStat(Stat.STAT_ARMOR);
        double temp = (100.0 + def)/1000.0;
        int armRed = 10000*(100)/(100+arm);
        Random ran = new Random();
        if(ran.nextDouble() < temp){
            Model.getInstance().getLog().logCombatDodge(target.getName());
            return;
        }
        int damage = getPower()*armRed/10000;
        damage = (int) ((int) damage * ran.nextDouble())+1;
        
        int HP = target.getStats().getEffectiveValueForName(Stat.STAT_HP);
        int oldHealth = HP;
        HP -= damage;
        int maxHealth = target.getStats().getEffectiveValueForName(Stat.STAT_LIFE);
        if (HP > maxHealth) {
            HP = maxHealth;
        }

        target.getStats().setHP(HP);

        Model.getInstance().getLog().logStatChangedByDelta(target.getName(),
                Stat.STAT_HP, HP - oldHealth);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("DamageEffect");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public String getRepresentation() {
        return "Star";
    }

    public DamageEffect clone() {
        return new DamageEffect();
    }
}
