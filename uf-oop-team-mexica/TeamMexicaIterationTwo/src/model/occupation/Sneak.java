package model.occupation;

import java.io.IOException;
import model.ability.BindWounds;
import model.ability.Creep;
import model.ability.PickPocket;
import model.ability.RangedWeapon;
import model.entity.Entity;
import model.item.equipment.weapon.Weapon;
import model.stat.*;
import util.SaverLoader;

public class Sneak extends BasicOccupation {

    public Sneak() {
        super(Stat.OCC_SNEAK);
    }

    @Override
    public void initStats() {
        super.initStats();

        Stat creepStat = new Stat(BASE_STAT_VAL, true, Stat.SKILL_CREEP);
        addToStats(creepStat);

        Stat pick = new Stat(BASE_STAT_VAL, true, Stat.SKILL_PICKPOCKET);
        addToStats(pick);

        Stat range = new Stat(BASE_STAT_VAL, true, Stat.SKILL_RANGED);
        addToStats(range);

        Stat leather = new Stat(BASE_STAT_VAL, true, Stat.SKILL_LEATHER);
        addToStats(leather);
    }

    @Override
    void initAbilities() {
        super.initAbilities();

        addToAbilities(new RangedWeapon());

        addToAbilities(new PickPocket());

        //create new Ceep Ability
        addToAbilities(new Creep());

        addToAbilities(new BindWounds());

    }

    public void levelEntity(StatContainer stats) {
        super.levelEntity(stats);
    }

    public void initEntity(Entity ent) {
        super.initEntity(ent);
        Weapon crossbow = new Weapon("Crossbow", 10, Weapon.AS_MEDIUM, Stat.STAT_AGILITY, 1, Stat.SKILL_RANGED, 1);
        ent.addItem(crossbow);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Sneak");
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
