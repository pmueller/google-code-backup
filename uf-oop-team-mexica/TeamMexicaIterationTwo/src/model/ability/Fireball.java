package model.ability;

import java.io.IOException;
import model.effect.DamageEffect;
import model.influence.LinearInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.SaverLoader;
import util.Vector;

public class Fireball extends Ability {

    public Fireball() {
        super("Fireball");
    }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        setShape(new LinearInfluence(orig, dir, 5));
        AoE aoe = new AoE(new DamageEffect(pow), 1);
        aoe.setRepresentation("Fireball");
        setAreaEffect(aoe);
    }

    public Ability clone() throws CloneNotSupportedException {
        return new Fireball();
    }

    public void initAbility() {

        setMaxRange(10);
        setRateOfPropagation(5);
        setInitialPower(2);
        setHitsOrigin(false);
        setCoolDown(25);
        setAssociatedStat(Stat.SKILL_BANE);
        setManaCost(80);
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
