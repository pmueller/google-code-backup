package model.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import util.*;
import model.*;
import model.ability.*;
import model.effect.Effect;
import model.item.equipment.armor.Armor;
import model.item.equipment.weapon.Weapon;
import model.item.InventoryItem;
import model.mapstuff.Interactive;
import model.occupation.Occupation;
import model.stat.*;
import model.stat.modifier.*;

public class Entity extends Interactive implements Updateable, Actor, Saveable, Cloneable {

    private StatContainer stats;
    private Inventory inventory;
    private AbilityMap abilityMap;
    private Occupation occupation;
    private MapGrid mapGrid;
    private Log logger;
    //a cool down timer will be used to determine when an entity will be able
    //to take another action, such as moving, or using an ability
    private int coolDownTimer;

    /**
     * Default constructor: sets up references to the logger and mapgrid
     */
    public Entity() {
        super("");
        Model m = Model.getInstance();
        logger = m.getLog();
        mapGrid = m.getMap();
    }

    /**
     * Constructor: sets default values, sets up references, and registers for
     * updates
     * @param mapGrid
     * @param logger
     * @param name
     * @param occ
     */
    public Entity(MapGrid mapGrid, Log logger, String name, Occupation occ) {
        super(name);

        this.mapGrid = mapGrid;
        this.logger = logger;

        stats = new StatContainer();
        abilityMap = new AbilityMap(stats);

        inventory = new Inventory();

        //occ will add/modify stats and add occupation specific abilities
        occupation = occ;
        occupation.initEntity(this);

        coolDownTimer = 0;
        registerForUpdate();
    }

    /**
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    /**
     * Uses the ability at the specified index
     * @param index
     */
    public void useAbility(int index) {
        if (coolDownTimer > 0) {
            System.out.println(abilityMap.getCoolDownFor(index));
            return;
        }

        abilityMap.useAbility(index, this.getPosition(), getOrientation());
        System.out.println(abilityMap.getCoolDownFor(index));

        coolDownTimer += abilityMap.getCoolDownFor(index);

    }

    /**
     *
     * @return Entities cannot co-exist on the same tile
     */
    public boolean blocksMovement() {
        return true;
    }

    /**
     *
     * @return a list of ability names
     */
    public List<String> getAbilities() {
        return abilityMap.getAbilities();
    }

    /**
     * Applies the effect to an Entity
     * @param eff
     */
    public void applyEffect(Effect eff) {
        eff.applyToEntity(this);
    }

    /**
     * Moves in the direction specified
     * @param direction
     */
    public void attemptMoveDirectionally(Vector direction) {

        if (coolDownTimer > 0) {
            return;
        }

        setOrientation(direction);

        Vector newPosition = getPosition().add(direction);
        if (moveToTile(newPosition)) {
            setCoolDownTimer((stats.getEffectiveValueForName("Movement")));
        }
    }

    /**
     * Moves the entity to the specified tile
     * @param v the destination vector
     */
    public boolean moveToTile(Vector v) {
        Tile t = mapGrid.getTileAtPosition(v);
        if (t == null) {
            return false;
        }
        boolean passable = true;

        if (t != null) {
            for (Interactive i : t.getInteractives()) {
                passable = passable & !i.blocksMovement();
                i.interactWithActor(this);
            }
        }

        if (passable) {
            this.setPosition(v);

            registerToTile(t);

            return true;     // return true if move succeeded
        } else {
            return false; // return false if move failed
        }
    }

    //***************************************************************
    // item stuff
    //****************************************************************
    /**
     * Adds the item to the inventory
     * @param item
     */
    public void addItem(InventoryItem item) {
        this.inventory.addItem(item);
    }

    /**
     * Uses the item at the specified index on this entity
     * @param index
     */
    public void useItem(int index) {
        InventoryItem item = this.inventory.itemAtIndex(index);
        if (item != null) {
            item.useOnEntity(this);
        }
    }

    /**
     * Removes the item at the specified index, and attempts to drop it onto the
     * tile in front of the entity
     * @param index
     */
    public void dropItem(int index) {
        InventoryItem dropped = inventory.removeItemAtIndex(index);
        if (dropped != null) {
            // gets the tile at the position if front of the entity
            Tile inFrontOfMe = mapGrid.getTileAtPosition(this.getPosition().add(this.getOrientation()));
            if (inFrontOfMe != null) {
                dropped.dropOntoTile(inFrontOfMe);
            }
        }
    }
    //********************************************************************8

    //*********************************************************************
    // Equipment and Unequip
    //***********************************************************************
    /**
     * Equips the armor, checking if you meet the stats required
     * @param a
     */
    public void equipArmor(Armor a) {
        if (a == null) {
            return;
        }

        int reqSkill = stats.getEffectiveValueForName(a.getRequiredSkillName());
        if (reqSkill < a.getRequiredSkillValue()) {
            logger.logDialogue("Your Inventory", "You don't have the stats necessary!");
            return;
        }

        if (inventory.getEquippedArmor() != null) {
            unequipArmor(); //unequip the current weapon if any
        }
        inventory.setEquippedArmor(a);
    }

    /**
     * Equips the weapon, checking if you have the stats required
     * @param w
     */
    public void equipWeapon(Weapon w) {
        if (w == null) {
            return;
        }

        int reqStat = stats.getEffectiveValueForName(w.getRequiredStatName());
        int reqSkill = stats.getEffectiveValueForName(w.getRequiredSkillName());
        if (reqStat < w.getRequiredStatValue() || reqSkill < w.getRequiredSkillValue()) {
            logger.logDialogue("Your Inventory", "You don't have the stats necessary!");
            return;
        }

        if (inventory.getEquippedWeapon() != null) {
            unequipWeapon(); //unequip the current weapon if any
        }
        inventory.setEquippedWeapon(w);
    }

    /**
     * Unequips the weapon, //and removes the associated stat modifier
     */
    public void unequipWeapon() {
        Weapon w = inventory.unequipWeapon();
    }

    /**
     * Unequips the armor, //and removes the associated stat modifier
     */
    public void unequipArmor() {
        Armor a = inventory.unequipArmor();
    }
    //**************************************************************************************************

    //************************************************************************
    // stuff that happens when an entity dies
    //************************************************************************
    /**
     * 1. Decrement lives
     * 2. If no more lives, drop all items
     * 
     * - Avatar will override to declare itself dead
     * - NPC will override to award avatar with exp and gp
     */
    public void deadEntity() {
        stats.addToBase(Stat.STAT_LIVES, -1);
        if (stats.getBaseValueForName(Stat.STAT_LIVES) <= 0) {
            Iterator iter = inventory.iterator();
            InventoryItem dropped;
            while (iter.hasNext()) {
                dropped = (InventoryItem) iter.next();
                dropped.dropOntoTile(mapGrid.getTileAtPosition(this.getPosition()));
            }
            unregisterFromTile();
            unRegisterForUpdate();
        }
    }

    /**
     * Respawns the entity at the mapgrid's starting point
     */
    public void respawn() {
        //occupation.initEntity(this);
        stats.setHP(stats.getStatForName(Stat.STAT_LIFE).getBaseValue());
        stats.setMP(stats.getStatForName(Stat.STAT_MANA).getBaseValue());

        boolean success = moveToTile(mapGrid.getStartingPoint());
        if ( !success ) System.out.println( " oops, that entity took your spot " ); // :(
        this.setPosition(mapGrid.getStartingPoint());
    }

    //***********************************************************************
    //*****************************************
    //Stat manipulation stuff
    //*************************************
    /**
     *
     * @return the stats container
     */
    public StatContainer getStats() {
        return stats;
    }

    /**
     * Call to this method forwarded to StatContainer.addStatModifier().
     * @param modifier
     */
    public void addStatModifier(StatModifier modifier) {
        stats.addStatModifier(modifier);
    }

    /**
     *
     * @return the time an entity is in cooldown mode
     */
    public int getCoolDownTimer() {
        return coolDownTimer;
    }

    /**
     * Sets the time the entity cannot do anything
     * @param time
     */
    public void setCoolDownTimer(int time) {
        coolDownTimer = time;
        if (coolDownTimer < 0) {
            coolDownTimer = 0;
        }
    }

    /**
     * Adds the time to the cooldown timer
     * @param time
     */
    public void addToCoolDownTimer(int time) {
        coolDownTimer += time;
        if (coolDownTimer < 0) {
            coolDownTimer = 0; //in case of negative
        }
    }


    /* *************************************************************************
     *
     * Character creation methods
     * called on by Occupation
     *
     **************************************************************************/
    /**
     * Adds the stat to the stats container
     * @param occSpecificSkill
     */
    public void addSkill(Stat occSpecificSkill) {
        stats.insertStat(occSpecificSkill);
    }

    /**
     * Adds the ability to the ability map
     * @param ab
     */
    public void addAbility(Ability ab) {
        abilityMap.insertAbility(ab);
    }

    /* *************************************************************************
     *
     * Saveable Interface Methods
     *
     **************************************************************************/
    @Override
    public void load(SaverLoader s, boolean nSC) throws IOException {
        this.stats = s.pullSaveable();
        this.inventory = s.pullSaveable();
        this.abilityMap = s.pullSaveable();
        this.abilityMap.setStats(stats);
        this.occupation = s.pullSaveable();
        this.mapGrid = Model.getInstance().getMap();
        this.logger = Model.getInstance().getLog();
        Model.getInstance().registerObserver(this);
        super.load(s, false);

        if (nSC) {
            s.assertEnd();
        }
    }

    @Override
    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Entity");
        }
        s.push(this.stats, true);
        s.push(this.inventory, true);
        s.push(this.abilityMap, true);
        s.push(this.occupation, true);

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    /* *************************************************************************
     *
     * Updateable Interface Methods
     *
     **************************************************************************/
    public void registerForUpdate() {
        model.Model.getInstance().registerObserver(this);
    }

    public void update() {
        if (--coolDownTimer < 0) {
            coolDownTimer = 0;
        }

        stats.updateDerivedStats(inventory.getEquippedWeapon(), inventory.getEquippedArmor());

        //interact with everything on your tile, every frame
        Tile t = mapGrid.getTileAtPosition(this.getPosition());
        if (t == null) {
            return;
        }
        for (Interactive i : t.getInteractives()) {
            if (i != this) {
                i.interactWithActor(this);
            }
        }
        if (stats == null) {
        } else {
            if (stats.getEffectiveValueForName(Stat.STAT_HP) <= 0) {
                deadEntity();
            }
        }
        if (stats.getBaseValueForName(Stat.STAT_MP)< stats.getBaseValueForName(Stat.STAT_MANA))
            stats.upgradeStat(Stat.STAT_MP, 1);
    }

    public void unRegisterForUpdate() {
        model.Model.getInstance().removeObserver(this);
    }

    /**
     *
     * @return true if the coolDownTimer is greater than zero, false otherwise
     */
    public boolean isBusy() {
        return coolDownTimer > 0;
    }

    /**
     * Says hello to the specified actor
     * @param actor
     */
    public void interactWithActor(Actor actor) {
        //logger.logDialogue(this.getName(), "Hello!");
    }

    /**
     * @return the logger
     */
    public Log getLogger() {
        return logger;
    }

    /**
     *
     * @return the mapGrid
     */
    protected MapGrid getMap() {
        return mapGrid;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Entity(mapGrid, logger, getName(), occupation);
    }

    /**
     *
     * @return true if the entity's HP is less than zero, false otherwise
     */
    public boolean isDead() {
        return this.stats.getEffectiveValueForName(Stat.STAT_HP) <= 0;
    }

    public int getEffectiveStat(String s){
        return stats.getEffectiveValueForName(s);
    }

}
