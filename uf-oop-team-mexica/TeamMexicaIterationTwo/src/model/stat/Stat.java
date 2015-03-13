package model.stat;

import java.io.IOException;
import java.util.*;
import model.stat.modifier.StatModifier;
import util.*;

public class Stat implements Saveable {

    private String name;
    private int baseValue;
    private Boolean visible;
    private ArrayList<StatModifier> modifiers;
    /*	Global primary stat names:	*/
    public static final String STAT_LIVES = "Lives";
    public static final String STAT_STRENGTH = "Strength";
    public static final String STAT_AGILITY = "Agility";
    public static final String STAT_INTELLECT = "Intellect";
    public static final String STAT_HARDINESS = "Hardiness";
    public static final String STAT_EXPERIENCE = "Experience";
    public static final String STAT_MOVEMENT = "Movement";

    /*	Global derived stat names:	*/
    public static final String STAT_LEVEL = "Level";
    public static final String STAT_LIFE = "Life";
    public static final String STAT_MANA = "Mana";
    public static final String STAT_OFFENSE = "Offensive rating";
    public static final String STAT_DEFENSE = "Defensive rating";
    public static final String STAT_ARMOR = "Armor rating";
    public static final String STAT_ATTACK = "Attack speed";
    public static final String STAT_HP = "Current HP";
    public static final String STAT_MP = "Current MP";
    public static final String STAT_GP = "Current GP";

    /*	Global occupation stat names:	*/
    public static final String OCC_SMASHER = "Smasher";
    public static final String OCC_SUMMONER = "Summoner";
    public static final String OCC_SNEAK = "Sneak";
    /*	Global skill names:	*/
    /* Summoner skills */
    public static final String SKILL_ENCHANT = "Enchantment";
    public static final String SKILL_BOON = "Boon";
    public static final String SKILL_BANE = "Bane";
    public static final String SKILL_STAFF = "Staff";
    public static final String SKILL_ROBES = "Roves";

    /* Sneak skills */
    public static final String SKILL_PICKPOCKET = "Pickpocket";
    public static final String SKILL_DETECT = "Detect";
    public static final String SKILL_CREEP = "Creep";
    public static final String SKILL_RANGED = "Ranged";
    public static final String SKILL_LEATHER = "Leather";

    /* Smash skills */
    public static final String SKILL_ONEHANDED = "One handed";
    public static final String SKILL_TWOHANDED = "Two handed";
    public static final String SKILL_BRAWLING = "Brawling";
    public static final String SKILL_PLATE = "Plate";

    /* Shared Avatar skills */
    public static final String SKILL_BINDING = "Wound binding";
    public static final String SKILL_BARGAIN = "Bargain";
    public static final String SKILL_OBSERVATION = "Observation";

    /* Other global names */
    public static final String STAT_MOD_UNARMED = "fists";

    /**
     * Constructor: sets the ivars and creates a new list for modifiers
     * @param base
     * @param vis
     * @param n
     */
    public Stat(int base, Boolean vis, String n) {
        baseValue = base;
        visible = vis;
        name = n;
        modifiers = new ArrayList<StatModifier>();
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name
     * @param n
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     *
     * @return the baseValue
     */
    public int getBaseValue() {
        return this.baseValue;
    }

    /**
     * Sets the baseValue
     * @param base
     */
    public void setBaseValue(int base) {
        this.baseValue = base;
    }

    /**
     * Applies each StatModifier's effect to the baseValue
     * @return the modified baseValue
     */
    public int getEffectiveValue() {
        int base = this.getBaseValue();
        int effective = this.getBaseValue();

        Iterator<StatModifier> iterator = this.modifiers.iterator();
        while (iterator.hasNext()) {
            StatModifier modifier = iterator.next();
            int delta = modifier.calculate(base);
            effective += delta;
        }

        return effective;
    }

    /**
     *
     * @return true if visible, false if not
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Sets visible
     * @param vis
     */
    public void setVisible(Boolean vis) {
        this.visible = vis;
    }

    /**
     * Adds the specified modifier
     * @param modifier
     */
    public void addModifier(StatModifier modifier) {
        this.modifiers.add(modifier);
        modifier.setOwner(this);
    }

    /**
     * Removes the specified modifier
     * @param modifier
     */
    public void removeModifier(StatModifier modifier) {
        this.modifiers.remove(modifier);
    }

    /**
     * Removes the specified modifier
     * @param name
     */
    public void removeModifierWithName(String name) {
        Iterator<StatModifier> iter = modifiers.iterator();
        while (iter.hasNext()) {
            StatModifier mod = iter.next();
            if (mod.getName().equals(name)) {
                removeModifier(mod);
            }
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.name = s.pullString();
        this.baseValue = s.pullInt();
        this.visible = s.pullBool();
        this.modifiers = s.pullList();
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Stat");
        }
        s.push(this.name);
        s.push(this.baseValue);
        s.push(this.visible);
        s.push(this.modifiers);

        if (notSuperClass) {
            s.close();
        }
    }

    /**
     * Adds i to the baseValue
     * @param i
     */
    public void upgrade(int i) {
        baseValue += i;
    }
}
