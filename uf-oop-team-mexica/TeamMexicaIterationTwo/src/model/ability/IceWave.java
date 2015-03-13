package model.ability;

import java.io.IOException;
import model.effect.DamageEffect;
import model.influence.AngularInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.*;

public class IceWave extends Ability {

    public IceWave() {
        super("Ice Wave");
    }

    @Override
    public void prepareToCast(Vector orig, Vector dir, int pow) {
        AoE aoe = new AoE(new DamageEffect(pow), 10);
        aoe.setRepresentation("IceWave");
        setAreaEffect(aoe);
        orig = orig.add(dir);
        setShape(new AngularInfluence(orig,dir,2,false));
    }

    @Override
    public void initAbility() {
        setHitsOrigin(false);
        setCoolDown(75);
        setAssociatedStat(Stat.SKILL_BANE);
        setMaxRange(2);
        setRateOfPropagation(5);
        setInitialPower(1);
        setManaCost(200);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("IceWave");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

}