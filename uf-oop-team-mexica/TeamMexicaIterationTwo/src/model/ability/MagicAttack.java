package model.ability;

import java.io.IOException;
import model.effect.DamageEffect;
import model.influence.LinearInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.SaverLoader;
import util.Vector;

public class MagicAttack extends Ability {

    public MagicAttack() {
        super("Magic Attack");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        setShape(new LinearInfluence(orig, dir, 3));
        AoE aoe = new AoE(new DamageEffect(pow), 5);
        aoe.setRepresentation("Sleep");
        setAreaEffect(aoe);
    }

    public Ability clone() throws CloneNotSupportedException {
        return new MagicAttack();
    }

    public void initAbility() {

        setMaxRange(10);
        setRateOfPropagation(5);
        setInitialPower(1);
        setHitsOrigin(false);
        setCoolDown(25);
        setAssociatedStat(Stat.SKILL_BANE);
        setManaCost(100);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("MagicAttack");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
