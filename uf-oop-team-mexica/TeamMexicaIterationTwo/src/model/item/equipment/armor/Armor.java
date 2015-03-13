package model.item.equipment.armor;

import util.*;
import java.io.IOException;
import model.entity.Entity;
import model.item.equipment.Equipment;

public class Armor extends Equipment implements Saveable {

    private String name = "";
    private int armorBonus;
    private String requiredSkillName;
    private int requiredSkillValue;

    /**
     * Inherited constructor
     * @param bonus
     */
    public Armor(int bonus) {
        this.armorBonus = bonus;
    }

    /**
     * Constructor: sets name and bonus
     * @param name
     * @param bonus
     */
    public Armor(String name, int bonus) {
        this.name = name;
        this.armorBonus = bonus;
    }

    /**
     * Constructor: sets all ivars
     * @param name
     * @param bonus
     * @param mod
     * @param reqStatNames
     * @param reqStatVals
     */
    public Armor(String name, int bonus, String reqSkillName, int reqSkillVal) {
        this.name = name;
        this.armorBonus = bonus;
        requiredSkillName = reqSkillName;
        requiredSkillValue = reqSkillVal;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public void load(SaverLoader sl, boolean notSuperClass) throws IOException {
        this.name = sl.pullString();
        this.armorBonus = sl.pullInt();
        
        requiredSkillName = sl.pullString();
        requiredSkillValue = sl.pullInt();

        super.load(sl, false);

        if (notSuperClass) {
            sl.assertEnd();
        }
    }

    public void save(SaverLoader sl, boolean nSC) throws IOException {
        if (nSC) {
            sl.start("Armor");
        }

        sl.push(name);
        sl.push(armorBonus);
        sl.push(requiredSkillName);
        sl.push(requiredSkillValue);


        super.save(sl, false);

        if (nSC) {
            sl.close();
        }
    }

    /**
     *
     * @return the armorBonus
     */
    public int getArmorBonus() {
        return armorBonus;
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

    /**
     * Equips self onto the entity specified
     * @param e
     */
    public void useOnEntity(Entity e) {
        e.equipArmor(this);
    }
}
