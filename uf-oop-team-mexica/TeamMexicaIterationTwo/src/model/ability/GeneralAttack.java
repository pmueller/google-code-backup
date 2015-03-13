package model.ability;

import java.io.IOException;
import model.effect.DamageEffect;
import model.influence.SingularInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.SaverLoader;
import util.Vector;

public class GeneralAttack extends Ability {

    public GeneralAttack() {
        super("GeneralAttack");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        setShape( new SingularInfluence( orig.add(dir) ) );
        AoE aoe = new AoE(new DamageEffect(pow), 4);
        aoe.setRepresentation("Star");
        setAreaEffect(aoe);

        setShape(new SingularInfluence(orig.add(dir)));
    }

    public void initAbility() {
        setMaxRange(0);
        setRateOfPropagation(15);
        setHitsOrigin(false);
        setCoolDown(20);
        setAssociatedStat(Stat.STAT_OFFENSE);
        setInitialPower(1);
    }

    public Ability clone() {
        return new GeneralAttack();
    }

    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("GeneralAttack");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
