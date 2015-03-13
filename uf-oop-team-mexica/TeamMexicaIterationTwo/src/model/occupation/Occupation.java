package model.occupation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import model.ability.Ability;
import model.entity.Entity;
import model.stat.Stat;
import model.stat.StatContainer;
import util.*;

public abstract class Occupation implements Saveable {

    private String name;
    private ArrayList<Stat> occSpecificSkills;
    private ArrayList<Ability> occSpecificAbilities;
    protected static final int BASE_STAT_VAL = 10;

    public Occupation(String name) {
        this.name = name;

        occSpecificSkills = new ArrayList<Stat>();
        occSpecificAbilities = new ArrayList<Ability>();

        initStats();
        initAbilities();
    }

    /**
     * initializes occ specific stats
     */
    abstract void initStats();

    /**
     * initializes occ specific Abilities
     */
    abstract void initAbilities();

    /**
     * levelEntity
     * @param stats
     */
    public abstract void levelEntity(StatContainer stats);

    /**
     * initializes an Entity's class specific skills and abilities
     * @param noob
     */
    public void initEntity(Entity noob) {
        Iterator iter = occSpecificSkills.iterator();
        while (iter.hasNext()) {
            noob.addSkill((Stat) iter.next());
        }//end while

        iter = occSpecificAbilities.iterator();
        while (iter.hasNext()) {
            noob.addAbility((Ability) iter.next());
        }
        levelEntity(noob.getStats());
    }

    /**
     * Adds the specified stat to the skills list
     * @param skill
     */
    public void addToStats(Stat skill) {
        occSpecificSkills.add(skill);
    }

    /**
     * Adds the specified ability to the abilities list
     * @param ab
     */
    public void addToAbilities(Ability ab) {
        occSpecificAbilities.add(ab);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.name = s.pullString();
        this.occSpecificSkills = s.pullList();
        this.occSpecificAbilities = s.pullList();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Occupation");
        }

        s.push(this.name);
        System.out.println(occSpecificSkills.getClass().getName());
        s.push((ArrayList<Stat>)this.occSpecificSkills);
        s.push(this.occSpecificAbilities);

        if (notSuperClass) {
            s.close();
        }
    }
}
