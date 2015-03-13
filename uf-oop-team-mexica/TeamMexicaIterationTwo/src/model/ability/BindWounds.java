package model.ability;

import java.io.IOException;

import model.effect.HealEffect;
import model.influence.*;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.*;

public class BindWounds extends Ability {

    public BindWounds() {
        super("Bind Wounds");
    }

    public void initAbility() {
        
        setMaxRange(-1);
        setRateOfPropagation(5);
        setHitsOrigin(true);
        setInitialPower(1);
        // origin, direction, and power will be set when the ability is used
        setAssociatedStat(Stat.SKILL_BINDING);
        setCoolDown(20);
        setManaCost(250);
    }

    public Ability clone() {
        return new BindWounds();
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("BindWounds");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        AoE healAoE = new AoE(new HealEffect(pow), 5);
        healAoE.setRepresentation("Heal");
        setAreaEffect(healAoE);
        this.setShape(new SingularInfluence(orig));
    }
}
