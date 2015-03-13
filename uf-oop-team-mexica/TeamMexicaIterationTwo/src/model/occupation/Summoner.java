package model.occupation;

import java.io.IOException;
import model.ability.*;
import model.stat.*;
import util.SaverLoader;

public class Summoner extends BasicOccupation {

    public Summoner() {
        super(Stat.OCC_SUMMONER);
    }

    public void initStats() {
        super.initStats();

        //create enchantment skill
        Stat enchStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_ENCHANT);
        addToStats(enchStat);
        //create boon skill
        Stat boonStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_BOON);
        addToStats(boonStat);
        //create bane skill
        Stat baneStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_BANE);
        addToStats(baneStat);
        //creat staff skill
        Stat staffStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_STAFF);
        addToStats(staffStat);

        Stat robes = new Stat(BASE_STAT_VAL, true, Stat.SKILL_ROBES);
        addToStats(robes);
    }

    public void initAbilities() {
        super.initAbilities();

        addToAbilities(new GeneralAttack());
        addToAbilities(new Heal());
        addToAbilities(new SleepAbility());
        addToAbilities(new IceWave());
        addToAbilities(new Fireball());
        addToAbilities(new MagicAttack());
    }

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
            s.start("Summoner");
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
