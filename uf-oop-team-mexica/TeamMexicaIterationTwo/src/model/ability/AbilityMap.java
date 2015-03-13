package model.ability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Model;
import model.stat.*;
import util.*;

public class AbilityMap implements Saveable {

    private ArrayList<Ability> abilities;
    private AbilityManager manager;
    private StatContainer stats;

    /**
     * Constructor: initializes the abilities list and references to stats and
     * model
     * @param stats
     */
    public AbilityMap(StatContainer stats) {
        abilities = new ArrayList<Ability>();
        this.stats = stats;
        manager = Model.getInstance().getAbilityManager();
    }

    /**
     *
     * @return a list of names of abilities
     */
    public ArrayList<String> getAbilities() {
        ArrayList<String> ret = new ArrayList<String>();
        for (Ability a : abilities) {
            ret.add(a.getName());
        }
        return ret;
    }

    /**
     * Uses the ability at the specified index, at the origin vector in the
     * specified direction
     * @param index
     * @param origin
     * @param direction
     */
    public void useAbility(int index, Vector origin, Vector direction) {
        try {
            Ability ab = abilities.get(index);
            if (stats.getEffectiveValueForName(Stat.STAT_MP) < ab.getManaCost()) {
                return;
            }
            stats.addToBase(Stat.STAT_MP, -ab.getManaCost());

            String key = ab.getAssociatedStat();


            int power = stats.getEffectiveValueForName(key) * ab.getInitialPower();
            System.out.println("returned effective value for stat " + stats.getEffectiveValueForName(key));
            ab.prepareToCast(origin, direction, power);
            manager.propagateAbility(ab);
        } catch (IndexOutOfBoundsException e) {
        }
    }

    /**
     *
     * @param index
     * @return the cooldown time for the specified ability index
     */
    public int getCoolDownFor(int index) {
        try {
            int CD = abilities.get(index).getCoolDown();
            CD += stats.getEffectiveValueForName(Stat.STAT_ATTACK);
            return CD;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Key not bound, fool! (Controller's fault!)");
            return 0;
        }
    }

    /**
     * Adds the ability to the list
     * @param ab
     */
    public void insertAbility(Ability ab) {
        abilities.add(ab);
    }

    /**
     * Sets the stats container
     * @param sc
     */
    public void setStats(StatContainer sc) {
        stats = sc;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.abilities = s.pullList();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("AbilityMap");
        }
        Iterator<Ability> iter = abilities.iterator();
        s.push(this.abilities);
        if (notSuperClass) {
            s.close();
        }
    }
}
