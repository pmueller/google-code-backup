package model.occupation;

import java.io.IOException;
import model.item.equipment.weapon.Weapon;
import model.stat.Stat;
import model.stat.StatContainer;
import util.SaverLoader;

public class BasicOccupation extends Occupation {

    public BasicOccupation(String name) {
        super(name);
    }

    protected int getLives() {
        return 1;
    }

    void initStats() {
        addToStats(new Stat(getLives(), true, Stat.STAT_LIVES));
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.STAT_STRENGTH));
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.STAT_AGILITY));
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.STAT_INTELLECT));
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.STAT_HARDINESS));
        addToStats(new Stat(0, true, Stat.STAT_EXPERIENCE));
        addToStats(new Stat(10, false, Stat.STAT_MOVEMENT));

        //HP, MP, GP
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.STAT_HP));
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.STAT_MP));
        addToStats(new Stat(0, true, Stat.STAT_GP));

        //derived stats
        addToStats(new Stat(0, true, Stat.STAT_LEVEL));
        addToStats(new Stat(0, true, Stat.STAT_LIFE));
        addToStats(new Stat(0, true, Stat.STAT_MANA));
        addToStats(new Stat(0, true, Stat.STAT_OFFENSE));
        addToStats(new Stat(0, true, Stat.STAT_DEFENSE));
        addToStats(new Stat(0, true, Stat.STAT_ARMOR));
        addToStats(new Stat(Weapon.AS_MEDIUM, false, Stat.STAT_ATTACK));//attack speed

        addToStats(new Stat(BASE_STAT_VAL, true, Stat.SKILL_BINDING));
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.SKILL_BARGAIN));
        addToStats(new Stat(BASE_STAT_VAL, true, Stat.SKILL_OBSERVATION));

    }

    void initAbilities() {
        
    }

    public void levelEntity(StatContainer stats) {
        stats.addToBase(Stat.STAT_LEVEL, 1);
        //calculate life to give:
        int life = 100 + stats.getBaseValueForName(Stat.STAT_HARDINESS) * 20;
        stats.addToBase(Stat.STAT_LIFE, life);
        stats.setHP(stats.getBaseValueForName(Stat.STAT_LIFE));
        //calculate mana to give
        int mana = 50 + stats.getBaseValueForName(Stat.STAT_INTELLECT) * 30;
        stats.addToBase(Stat.STAT_MANA, mana);
        stats.setMP(stats.getBaseValueForName(Stat.STAT_MANA));
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("BasicOccupation");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
