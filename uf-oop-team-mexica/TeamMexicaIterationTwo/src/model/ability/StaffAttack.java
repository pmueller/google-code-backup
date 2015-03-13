package model.ability;

import java.io.IOException;
import model.effect.*;
import model.influence.SingularInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.*;

public class StaffAttack extends Ability {

    public StaffAttack() {
        super("StaffAttack");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        AoE aoe = new AoE(new DamageEffect(pow), -1);
        aoe.setRepresentation("Star");
        setAreaEffect(aoe);
        setMaxRange(-1);
        setRateOfPropagation(10);
        setShape(new SingularInfluence(orig.add(dir)));
        setHitsOrigin(false);
        setCoolDown(10);
        setAssociatedStat(Stat.SKILL_STAFF);
    }

    public Ability clone() throws CloneNotSupportedException {
        return new StaffAttack();
    }

    public void initAbility() {
        
    }

    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("StaffAttack");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
