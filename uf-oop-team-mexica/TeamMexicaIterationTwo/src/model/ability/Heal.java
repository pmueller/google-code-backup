package model.ability;

import java.io.IOException;
import model.effect.HealEffect;
import model.influence.SingularInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.*;

public class Heal extends Ability {

    public Heal() {
        super("Heal");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        AoE aoe = new AoE(new HealEffect(pow),5);
        aoe.setRepresentation("Heal");
        setAreaEffect(aoe);
        setMaxRange(-1);
        
        setShape(new SingularInfluence(orig));
        
        setAssociatedStat(Stat.SKILL_BOON);
    }

    public Ability clone() throws CloneNotSupportedException {
        return new Heal();
    }

    public void initAbility() {
        setRateOfPropagation(10);
        setHitsOrigin(true);
        setCoolDown(30);
        setInitialPower(1);
        setManaCost(300);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Heal");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
