package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import model.stat.Stat;

public class StatsVisitor extends AvatarVisitor {

    //instance variable that hold the visitor state
    private List<String> upgradableStatNames;
    private List<String> nonUpgradableStatNames;
    private List<String> skillNames;
    private List<String> abilityNames;
    private List<String> upgradableStatVals;
    private List<String> nonUpgradableStatVals;
    private List<String> skillVals;
    private String occupation;
    private List<String> usKeys;
    private List<String> sKeys;
    private List<String> skillKeys;
    private int statPoints;
    private int skillPoints;
    private String GP;
    private String HP;
    private String MP;
    private String maxHP;
    private String maxMP;
    private String level;
    private String lives;
    private String exp;

    public StatsVisitor() {

        //initialize all the ivars
        upgradableStatNames = new ArrayList<String>();
        nonUpgradableStatNames = new ArrayList<String>();
        skillNames = new ArrayList<String>();

        abilityNames = new ArrayList<String>();

        upgradableStatVals = new ArrayList<String>();
        nonUpgradableStatVals = new ArrayList<String>();
        skillVals = new ArrayList<String>();

        usKeys = new ArrayList<String>();
        sKeys = new ArrayList<String>();
        skillKeys = new ArrayList<String>();

        usKeys.add(model.stat.Stat.STAT_AGILITY);
        usKeys.add(model.stat.Stat.STAT_STRENGTH);
        usKeys.add(model.stat.Stat.STAT_HARDINESS);
        usKeys.add(model.stat.Stat.STAT_INTELLECT);
        sKeys.add(model.stat.Stat.STAT_ARMOR);
        sKeys.add(model.stat.Stat.STAT_ATTACK);
        sKeys.add(model.stat.Stat.STAT_DEFENSE);
        sKeys.add(model.stat.Stat.STAT_MOVEMENT);
        sKeys.add(model.stat.Stat.STAT_OFFENSE);

        statPoints = 0;
        skillPoints = 0;
        
    }

    /**
     *
     * @return the HP
     */
    public String getHP() {
        return HP;
    }

    /**
     *
     * @return the maxHP
     */
    public String getMaxHP() {
        return maxHP;
    }

    /**
     *
     * @return the MP
     */
    public String getMP() {
        return MP;
    }

    /**
     *
     * @return the maxHP
     */
    public String getMaxMP() {
        return maxMP;
    }

    /**
     *
     * @return the GP
     */
    public String getGP() {
        return GP;
    }

    /**
     *
     * @return the exp
     */
    public String getExp() {
        return exp;
    }

    /**
     * visit the avatar by being accepted on the stats
     * then grab the abilites, skillpoints and stat points
     * @param a
     */
    public void visit(model.entity.Avatar a) {
        a.getStats().accept(this);
        abilityNames = a.getAbilities();
        skillPoints = a.getSkillPoints();
        statPoints = a.getStatPoints();
    }

    /**
     * visit stat container. for each state add the name
     * and value to the right lists/variables
     * @param sc
     */
    public void visit(model.stat.StatContainer sc) {

        Set<String> stats = sc.getStatNames();

        for(String statName : stats) {
            Stat stat = sc.getStatForName(statName);
            //this makes me cry every night
            //how could we missed this part of the design
            //so badly that this happens :'(
            // RIP dreams
            //add to the correct state variable
            if(usKeys.contains(statName)) {
                upgradableStatNames.add(stat.getName());
                upgradableStatVals.add(String.valueOf(stat.getEffectiveValue()));
            }
            else if (sKeys.contains(statName)) {
                nonUpgradableStatNames.add(stat.getName());
                nonUpgradableStatVals.add(String.valueOf(stat.getEffectiveValue()));
            }
            else if (statName.equals(Stat.STAT_GP)) {
                GP = String.valueOf(stat.getEffectiveValue());
            }
            else if (statName.equals(Stat.STAT_HP)) {
                HP = String.valueOf(stat.getEffectiveValue());
            }
            else if (statName.equals(Stat.STAT_MP)) {
                MP = String.valueOf(stat.getEffectiveValue());
            }
            else if (statName.equals(Stat.STAT_LIFE)) {
                maxHP = String.valueOf(stat.getEffectiveValue());
            }
            else if (statName.equals(Stat.STAT_MANA)) {
                maxMP = String.valueOf(stat.getEffectiveValue());
            }
            else if (statName.equals(Stat.STAT_LIVES)) {
                lives = String.valueOf(stat.getEffectiveValue());
            }
            else if (statName.equals(Stat.STAT_LEVEL)) {
                level = String.valueOf(stat.getEffectiveValue());
            }
            else if (statName.equals(Stat.STAT_EXPERIENCE)) {
                exp = String.valueOf(stat.getEffectiveValue());
            }
            else {
                skillNames.add(stat.getName());
                skillVals.add(String.valueOf(stat.getEffectiveValue()));
            }
        }
    }

    /**
     *
     * @return the statPoints
     */
    public int getStatPoints() {
        return statPoints;
    }

    /**
     *
     * @return the number of lives
     */
    public String getLives() {
        return lives;
    }

    /**
     *
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     *
     * @return the skillPoints
     */
    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     *
     * @return the upgradableStatNames
     */
    public List<String> getUpgradableStatNames() {

        return upgradableStatNames;
    }

    /**
     *
     * @return the nonUpgradableStatNames
     */
    public List<String> getNonUpgradableStatNames() {
        return nonUpgradableStatNames;
    }

    /**
     *
     * @return the skillNames
     */
    public List<String> getSkillNames() {
        return skillNames;
    }

    /**
     *
     * @return the upgradableStatVals
     */
    public List<String> getUpgradableStatVals() {
        return upgradableStatVals;
    }

    /**
     *
     * @return the nonUpgradabeStatVals
     */
    public List<String> getNonUpgradableStatVals() {
        return nonUpgradableStatVals;
    }

    /**
     *
     * @return the skillVals
     */
    public List<String> getSkillVals() {
        return skillVals;
    }

    /**
     *
     * @return the abilityNames
     */
    public List<String> getAbilityNames() {
        return abilityNames;
    }

}
