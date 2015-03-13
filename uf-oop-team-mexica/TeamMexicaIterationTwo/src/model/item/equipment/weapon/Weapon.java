package model.item.equipment.weapon;

import java.io.IOException;
import util.*;
import model.entity.Entity;
import model.item.equipment.Equipment;

public class Weapon extends Equipment implements Saveable {

    private String name = "";
    private int weaponBonus;
    private String requiredStatName;
    private int requiredStatValue;
    private String requiredSkillName;
    private int requiredSkillValue;
    private int attackSpeed;
    public static final int AS_SLOW = 30;
    public static final int AS_MEDIUM = 20;
    public static final int AS_FAST = 10;

    /**
     * Inherited constructor
     * @param bonus
     */
    public Weapon(int bonus) {
        this.weaponBonus = bonus;
    }

    /**
     * Constructor: sets name and bonus
     * @param name
     * @param bonus
     */
    public Weapon(String name, int bonus) {
        this.name = name;
        this.weaponBonus = bonus;
    }

    /**
     * Constructor: sets all ivars
     * @param name
     * @param bonus
     * @param statMod
     * @param speedMod
     * @param reqStatNames
     * @param reqStatVals
     */
    public Weapon(String name, int bonus, int speedMod, String reqStatName,
            int reqStatVal, String reqSkillName, int reqSkillVal) {
        this.name = name;
        this.weaponBonus = bonus;
        requiredStatName = reqStatName;
        requiredStatValue = reqStatVal;
        requiredSkillName = reqSkillName;
        requiredSkillValue = reqSkillVal;
        attackSpeed = speedMod;
    }

    //stuff dealing with stats
    //************************************************
    /**
     *


    /**
     *
     * @return the attackSpeedModifier
     */
    public int getAttackSpeed() {
        return attackSpeed;
    }

    /**
     *
     * @return the requiredStatNames
     */
    public String getRequiredStatName() {
        return requiredStatName;
    }
    public void setRequiredStatName(String name){
        requiredStatName = name;
    }

    /**
     *
     * @return the requiredStatValues
     */
    public int getRequiredStatValue() {
        return requiredStatValue;
    }

    /**
     * sets the requiredStatValue
     * @param val
     */
    public void setRequiredStatValue(int val){
        requiredStatValue = val;
    }

    /**
     *
     * @return the requiredSkillName
     */
    public String getRequiredSkillName(){
        return requiredSkillName;
    }

    /**
     * sets the requiredSkillName
     * @param name
     */
    public void setRequiredSkillName(String name){
        requiredSkillName = name;
    }

    /**
     *
     * @return the requiredSkillValue
     */
    public int getRequiredSkillValue(){
        return requiredSkillValue;
    }

    /**
     * sets the requiredSkillValue
     * @param val
     */
    public void setRequiredSkillValue(int val){
        requiredSkillValue = val;
    }


    //**************************************************
    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the weaponBonus
     */
    public int getWeaponBonus() {
        return weaponBonus;
    }

    /**
     * Equips self onto the specified entity
     * @param e
     */
    public void useOnEntity(Entity e) {
        e.equipWeapon(this);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.name = s.pullString();
        this.weaponBonus = s.pullInt();
        this.requiredStatName = s.pullString();
        this.requiredStatValue = s.pullInt();
        this.requiredSkillName = s.pullString();
        this.requiredSkillValue = s.pullInt();
        this.attackSpeed = s.pullInt();

        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Weapon");
        }

        s.push(this.name);
        s.push(this.weaponBonus);
        s.push(this.requiredStatName);
        s.push(this.requiredStatValue);
        s.push(this.requiredSkillName);
        s.push(this.requiredSkillValue);
        s.push(this.attackSpeed);

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
