package model.stat;

import java.io.IOException;
import java.util.*;
import model.item.equipment.armor.Armor;
import model.item.equipment.weapon.Weapon;
import model.stat.modifier.StatModifier;
import util.*;

public class StatContainer implements Saveable {

    private HashMap<String, Stat> stats;

    /**
     * Constructor: creates a new hashmap
     */
    public StatContainer() {
        stats = new HashMap<String, Stat>();
    }

    /**
     *
     * @return the set of stat keys
     */
    public Set<String> getStatNames() {
        return stats.keySet();
    }

    /**
     *
     * @param name
     * @return the base value for the specified stat
     */
    public int getBaseValueForName(String name) {
        Stat stat = this.stats.get(name);
        if(stat != null)
            return stat.getBaseValue();
        return 0;
    }

    /**
     *
     * @param name
     * @return the calculated effective value of the specified, taking into
     * account the associated stat modifiers
     */
    public int getEffectiveValueForName(String name) {
        Stat stat = this.stats.get(name);
        //System.out.println(name);
        if(stat != null)
            return stat.getEffectiveValue();
        return 0;
    }

    /**
     *
     * @param name
     * @return the stat for the specified name
     */
    public Stat getStatForName(String name) {
        return this.stats.get(name);
    }

    /**
     *
     * @param name
     * @return true if the stat exists, false otherwise
     */
    public boolean containsStatForName(String name) {
        return this.stats.get(name) != null;
    }

    /**
     * Puts the stat into the hashmap
     * @param stat
     */
    public void insertStat(Stat stat) {
        this.stats.put(stat.getName(), stat);
    }

    /**
     * Sets the base HP
     * @param hp
     */
    public void setHP(int hp) {
        Stat currentHP = this.getStatForName(Stat.STAT_HP);
        currentHP.setBaseValue(hp);
    }

    /**
     * Sets the base MP
     * @param mp
     */
    public void setMP(int mp) {
        Stat currentMP = this.getStatForName(Stat.STAT_MP);
        currentMP.setBaseValue(mp);
    }

    /**
     * Permanently adds to a Stat's base value
     */
    public void addToBase(String nameOfStat, int addedValue) {
        Stat s = getStatForName(nameOfStat);
        int value = s.getBaseValue() + addedValue;
        if (value < 0) {
            value = 0;
        }
        s.setBaseValue(value);
    }

    /**
     * Adds a modifier to the stat with the corresponding name
     * @param modifier
     */
    public void addStatModifier(StatModifier modifier) {
        addStatModifierForNamedStat(modifier, modifier.getName());
    }

    /**
     * Adds a modifier to the named stat, so that the modifier may be referenced by name
     * @param modifier
     * @param nameOfStat	Should be one of the global constant string members of Stat.
     */
    public void addStatModifierForNamedStat(StatModifier modifier, String nameOfStat) {
        Stat stat = null;
        if ((stat = getStatForName(nameOfStat)) != null) {
            stat.addModifier(modifier);
        }
    }

    /**
     * StatsVisitor accept method
     * @param v
     */
    public void accept(util.StatsVisitor v) {
        v.visit(this);
    }

    /**
     *
     * @return an iterator that iterates through the stats in alphabetical order
     */
    public Iterator iterator() {
        final TreeSet set = new TreeSet(StatContainer.this.stats.keySet());

        return new Iterator() {

            /**
             * to ensure that keys are always returned in alphabetic order,
             * turn the set of keys into a TreeSet, which is automatically
             * sorted, and use that iterator
             */
            private Iterator<String> keyIterator = set.iterator();

            public boolean hasNext() {
                return keyIterator.hasNext();
            }

            public Stat next() {
                String key = keyIterator.next();
                return StatContainer.this.getStatForName(key);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        int size = s.pullInt();
        this.stats = new HashMap<String, Stat>(size);
        for (int i = 0; i < size; i++) {
            String key = s.pullString();
            Stat stat = s.pullSaveable();
            this.stats.put(key, stat);
        }

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("StatContainer");
        }

        s.push(this.stats.size());

        Set set = this.stats.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            s.push(key);
            s.push(this.getStatForName(key), true);
        }

        if (notSuperClass) {
            s.close();
        }
    }

    /**
     * Upgrades the specified stat the specified value
     * @param name
     * @param value
     */
    public void upgradeStat(String name, int value) {
        Stat s = stats.get(name);
        if (s != null) {
            s.upgrade(value);
        }
    }

    /**
     * Updates the offense, defense, and armor related stats
     * @param w
     * @param a
     */
    public void updateDerivedStats(Weapon w, Armor a){
        //derived stats we're concerned about...
        //offense, defense, armor
        int offense = 0;
        if(w == null){ //no weapon equipped
            offense += (this.getEffectiveValueForName(Stat.STAT_STRENGTH)) * 2;
            offense += this.getEffectiveValueForName(Stat.SKILL_BRAWLING);
            this.getStatForName(Stat.STAT_ATTACK).setBaseValue(Weapon.AS_FAST);
        }
        else { //weapon is equipped
            offense += w.getWeaponBonus();
            offense += ( this.getEffectiveValueForName(w.getRequiredStatName()) ) * 3;
            offense += this.getEffectiveValueForName(w.getRequiredSkillName());
            this.getStatForName(Stat.STAT_ATTACK).setBaseValue(w.getAttackSpeed());
        }
        this.getStatForName(Stat.STAT_OFFENSE).setBaseValue(offense);

        int armor = this.getEffectiveValueForName(Stat.STAT_HARDINESS) * 3;
        if (a != null){
            armor += this.getEffectiveValueForName(a.getRequiredSkillName());
        }
        this.getStatForName(Stat.STAT_ARMOR).setBaseValue(armor);

        // since defense will probably end up being a % chance that something will miss you
        // i didn't want these values to be too high
        int defense = this.getEffectiveValueForName(Stat.STAT_AGILITY) / 2 ;
        defense += this.getEffectiveValueForName(Stat.STAT_LEVEL) / 2 ;
        this.getStatForName(Stat.STAT_DEFENSE).setBaseValue(defense);

    }
}
