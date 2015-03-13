package model.ability;

import util.*;
import java.io.*;
import model.influence.ShapeOfInfluence;
import model.mapstuff.aoe.AoE;


public abstract class Ability implements Saveable {

    private int rateOfPropagation;
    private int maxRange;
    private ShapeOfInfluence shape;
    private boolean hitsOrigin;
    private Vector origin;
    private Vector direction;
    private int initialPower;
    private String name;
    private AoE areaEffect;
    private int rateOfEntropy;
    private String associatedStat;
    private int coolDown;
    private int manaCost;

    /**
     * Constructor: sets the name and calls intiAbility
     * @param name
     */
    public Ability(String name) {
        this.name = name;
        this.manaCost = 0;
        initAbility();
    }

    public abstract void prepareToCast(Vector orig, Vector dir, int pow);

    public abstract void initAbility();

    //******************************************************
    // setters
    //*****************************************************
    /**
     * Sets the initialPower
     * @param power
     */
    public void setInitialPower(int power) {
        initialPower = power;
    }

    /**
     * sets the areaEffect
     * @param aoe
     */
    public void setAreaEffect(AoE aoe) {
        areaEffect = aoe;
    }

    /**
     * sets the rateOfPropagation
     * @param rate
     */
    public void setRateOfPropagation(int rate) {
        rateOfPropagation = rate;
    }

    /**
     * sets the maxRange
     * @param range
     */
    public void setMaxRange(int range) {
        maxRange = range;
    }

    /**
     * sets the shape
     * @param soi
     */
    public void setShape(ShapeOfInfluence soi) {
        shape = soi;
    }

    /**
     * sets the hitsOrigin
     * @param hits
     */
    public void setHitsOrigin(boolean hits) {
        hitsOrigin = hits;
    }

    /**
     * sets the rateOfEntropy
     * @param rate
     */
    public void setRateOfEntropy(int rate) {
        rateOfEntropy = rate;
    }

    /**
     * sets the associatedStat
     * @param statName
     */
    public void setAssociatedStat(String statName) {
        associatedStat = statName;
    }

    /**
     * sets the coolDown
     * @param CD
     */
    public void setCoolDown(int CD) {
        coolDown = CD;
    }

    /**
     * sets the manaCost
     * @param val
     */
    public void setManaCost(int val){
        manaCost = val;
    }

    // getters
    /**
     *
     * @return the areaEffect
     */
    public AoE getAreaEffect() {
        return areaEffect;
    }

    /**
     *
     * @return the maxRange
     */
    public int getMaxRange() {
        return maxRange;
    }

    /**
     *
     * @return the rateOfPropagation
     */
    public int getRateOfPropagation() {
        return rateOfPropagation;
    }

    /**
     *
     * @return the shape
     */
    public ShapeOfInfluence getShape() {
        return shape;
    }

    /**
     *
     * @return the hitsOrigin
     */
    public boolean hitsOrigin() {
        return hitsOrigin;
    }

    /**
     *
     * @return the origin
     */
    public Vector getOrigin() {
        return origin;
    }

    /**
     *
     * @return the direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     *
     * @return the initialPower
     */
    public int getInitialPower() {
        return initialPower;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the rateOfEntropy
     */
    public int getRateOfEntropy() {
        return rateOfEntropy;
    }

    /**
     *
     * @return the associatedStat
     */
    public String getAssociatedStat() {
        return associatedStat;
    }

    /**
     *
     * @return the coolDown
     */
    public int getCoolDown() {
        return coolDown;
    }

    /**
     *
     * @return the manaCost
     */
    public int getManaCost(){
        return manaCost;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.rateOfPropagation = s.pullInt();
        this.maxRange = s.pullInt();
        this.shape = s.pullSaveable();
        this.hitsOrigin = s.pullBool();
        this.origin = s.pullSaveable();
        this.direction = s.pullSaveable();
        this.initialPower = s.pullInt();
        this.name = s.pullString();
        this.areaEffect = s.<AoE>pullSaveable();
        this.rateOfEntropy = s.pullInt();
        this.associatedStat = s.pullString();
        this.coolDown = s.pullInt();
        this.manaCost = s.pullInt();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if(notSuperClass){
            s.start("Ability");
        }

        s.push(this.rateOfPropagation);
        s.push(this.maxRange);
        s.push(this.shape, true);
        s.push(this.hitsOrigin);
        s.push(this.origin, true);
        s.push(this.direction, true);
        s.push(this.initialPower);
        s.push(this.name);
        s.push(this.areaEffect, true);
        s.push(this.rateOfEntropy);
        s.push(this.associatedStat);
        s.push(this.coolDown);
        s.push(this.manaCost);

        if (notSuperClass) {
            s.close();
        }
    }
}
