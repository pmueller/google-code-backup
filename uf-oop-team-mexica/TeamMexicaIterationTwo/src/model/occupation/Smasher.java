package model.occupation;

import java.io.IOException;
import model.ability.BindWounds;
import util.SaverLoader;
import model.stat.*;
import model.ability.SmasherAttackAbility;

public class Smasher extends BasicOccupation {

    public Smasher() {
        super(Stat.OCC_SMASHER);
    }

    @Override
    void initStats() {
        super.initStats();

        Stat brawlStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_BRAWLING);
        addToStats(brawlStat);

        Stat oneHandStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_ONEHANDED);
        addToStats(oneHandStat);

        Stat twoHandStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_TWOHANDED);
        addToStats(twoHandStat);

        Stat plate = new Stat(BASE_STAT_VAL, true, Stat.SKILL_PLATE);
        addToStats(plate);
    }

    @Override
    void initAbilities() {
        super.initAbilities();

        addToAbilities(new SmasherAttackAbility());
        addToAbilities(new BindWounds());
    }

    @Override
    public void levelEntity(StatContainer stats) {
        super.levelEntity(stats);

    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Smasher");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }


    @Override
    protected int getLives() {
        return 2;
    }
}
