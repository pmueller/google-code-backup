package model.ability;

import java.io.IOException;
import model.effect.DamageEffect;
import model.influence.LinearInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.SaverLoader;
import util.Vector;

public class RangedWeapon extends Ability {

    public RangedWeapon() {
        super("RangedWeapon");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        this.setShape(new LinearInfluence(orig, dir, 10));
        AoE aoe = new AoE(new DamageEffect(pow), 1);
        aoe.setRepresentation("Arrow");
        aoe.setOrientation(dir);
        setAreaEffect(aoe);
    }

    public Ability clone() throws CloneNotSupportedException {
        return new RangedWeapon();
    }

    public void initAbility() {
        setMaxRange(10);
        setRateOfPropagation(3);
        setInitialPower(1);
        setHitsOrigin(false);
        setCoolDown(10);
        setAssociatedStat(Stat.STAT_OFFENSE);
    }

    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("RangedWeapon");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
