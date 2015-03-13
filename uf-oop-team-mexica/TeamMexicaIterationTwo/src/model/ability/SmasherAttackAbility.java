package model.ability;

import java.io.IOException;
import model.mapstuff.aoe.AoE;
import model.effect.DamageEffect;
import model.stat.Stat;
import model.influence.CloseInfluence;
import model.item.equipment.weapon.Weapon;
import util.Vector;
import util.SaverLoader;

public class SmasherAttackAbility extends Ability {

    public SmasherAttackAbility() {
        super("SmasherAttackAbility");
    }

    public void initAbility() {
        setInitialPower(1);
        setRateOfPropagation(15);
        setMaxRange(0);
        setHitsOrigin(false);
        setRateOfEntropy(0);
        setAssociatedStat(Stat.STAT_OFFENSE);
        setCoolDown(Weapon.AS_MEDIUM);
    }

    public void prepareToCast(Vector origin, Vector direction, int power) {
        System.out.println("SmashAttackCalled");
        setShape(new CloseInfluence(origin, direction));
        DamageEffect dE = new DamageEffect(power);
        AoE aoe = new AoE(dE, 1);
        aoe.setRepresentation("Star");
        setAreaEffect(aoe);
    }

    public SmasherAttackAbility clone() {
        return new SmasherAttackAbility();
    }

    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("SmasherAttackAbility");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
