package model;

import java.io.IOException;
import java.util.ArrayList;
import model.ability.*;
import model.effect.*;
import model.entity.*;
import model.entity.npc.*;
import model.mapstuff.aoe.*;
import model.occupation.*;
import model.item.*;
import model.item.equipment.armor.Armor;
import model.item.equipment.weapon.Weapon;
import model.mapstuff.MapStuff;
import model.mapstuff.SpawnPoint;
import model.stat.Stat;
import util.*;

//========================================================\\
public class Model {

    private static Model instance = null;
    private boolean paused;
    private ArrayList<Updateable> observers;
    private MapGrid map;
    private Avatar avatar;
    private Log log;
    private boolean editMode;
    private AbilityManager am;

    /**
     * Private constructor, Model is a singleton
     */
    private Model() {
        editMode = false;
        paused = true;
        observers = new ArrayList<Updateable>();
        log = new Log();


    }

    /**
     * Model is a singleton
     * @return the Model singleton instance
     */
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    //========================================================\\
    /**
     * Pauses/Unpauses the game
     * @param b true to pause the game, false to resume
     */
    public void setPaused(boolean b) {
        paused = b;
    }

    /**
     * Forwards the MapVisitor to the MapGrid
     * @param v
     */
    public void forwardMapVisitor(MapVisitor v) {
        v.setAvatarPosition(avatar.getPosition());
        map.accept(v);
    }

    /**
     * Forwards the AvatarVisitor to the Avatar
     * @param v
     */
    public void forwardAvatarVisitor(AvatarVisitor v) {
        avatar.accept(v);
    }

    /**
     * Forwards the LogVisitor to the Log
     * @param v
     */
    public void forwardLogVisitor(LogVisitor v) {
        log.accept(v);
    }

    /**
     * Avatar getter
     * @return
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Avatar setter
     * @param av
     */
    public void setAvatar(Avatar av) {
        avatar = av;
    }

    /**
     * Log getter
     * @return
     */
    public Log getLog() {
        return log;
    }

    /**
     * AbilityManager getter
     * @return
     */
    public AbilityManager getAbilityManager() {
        return am;
    }

    /**
     * MapGrid getter
     * @return
     */
    public MapGrid getMap() {
        return map;
    }

    /**
     * Creates a new game
     * @param name the name of the avatar
     * @param profession the name of the occupation ("Smasher", "Summoner", or
     * "Sneak")
     */
    public void newGame(String name, String profession) {

        map = new MapGrid(30, 30);

        am = new AbilityManager(map);
        Occupation o;

        if (profession.equals("Smasher")) {
            o = new Smasher();
        } else if (profession.equals("Summoner")) {
            o = new Summoner();
        } else {
            o = new Sneak();
        }
        avatar = new Avatar(map, log, "Avatar", o);
        avatar.setRepresentation("Avatar");
        avatar.setPosition(new Vector(0, 0));
        avatar.registerToTile(map.getTileAtPosition(new util.Vector(0, 0)));
        avatar.setOrientation(Vector.EAST);
        /*
        /**
         * for testing
         *

        Entity t = new TigerNPC(map, log, "A glorious tiger");
        t.setPosition(new Vector(3, 3));
        t.setRepresentation("Tiger");
        t.registerToTile(map.getTileAtPosition(new util.Vector(3, 3)));


        TakeableItem p = new TakeableItem(new ConsumableItem("Potion", new HealEffect(5)),"Potion");
        p.registerToTile(map.getTileAtPosition(new util.Vector(4,4)));

        TakeableItem a = new TakeableItem(new Armor("GoldenArmor", 5), "GoldenArmor");
        a.registerToTile(map.getTileAtPosition(new util.Vector(4,5)));



        // test portal effect
        PortalEffect effect = new PortalEffect(new Vector(5, 5));
        AoE portal = new AoE(effect, -1);
        Tile portalTile = map.getTileAtPosition(new Vector(5, 1));
        portal.registerToTile(portalTile);

        registerObserver(t);
        registerObserver(avatar);
        registerObserver(am);


        // test spawn point
        MapStuff sp = new SpawnPoint( t, 3, 8 );
        sp.setPosition( new Vector( 7, 7 ) );

        //TODO: add weapon, armor, takeable items to the map
        */
    }

    /**
     * Attempts to move the avatar
     * @param v
     */
    public void moveAvatar(Vector v) {
        //System.out.println(v);
        avatar.attemptMoveDirectionally(v);
    }

    /**
     *
     * @param statName
     */
    public void upgradeAvatarStat(String statName) {
        avatar.upgradeStat(statName);
    }

    public void upgradeAvatarSkill(String statName) {
        avatar.upgradeSkill(statName);
    }

    /**
     * Registers an instance of Updateable with the observers list
     * @param observer
     */
    public void registerObserver(Updateable observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Removes the instance of Updateable from the observers list
     * @param observer
     */
    public void removeObserver(Updateable observer) {
        observers.remove(observer);
    }

    /**
     * Updates each Updateable instance in this.observers list
     */
    public void update() {
        if(!this.isPaused()) {
            Updateable[] temp = new Updateable[observers.size()];
            temp = observers.toArray(temp);

            for (int i = 0; i < temp.length; ++i)
                temp[i].update();
                //System.out.println("List of ob: "+observers.size());
             // TODO: fix
        }
    }

    /**
     *
     * @return true/false whether the game is paused or not
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Tells the avatar to use its ability at the specified index
     * @param index
     */
    public void useAvatarAbility(Integer index) {
        avatar.useAbility(index);
    }

    /**
     * Tells the MapGrid to save itself
     * @param sl
     * @throws IOException
     */
    public void saveState(SaverLoader sl) throws IOException {
        map.save(sl, true);
    }

    /**
     * Loads the game state
     * @param sl
     * @throws IOException
     */
    public void loadState(SaverLoader sl) throws IOException {
        
            SaveableFactory.init(sl);
            map = null;
            avatar = null;
            observers = new ArrayList<Updateable>();
            am = new AbilityManager(null);
            map = new MapGrid(0, 0);
            map.load(sl, true);
            
    }

    /**
     * Toggles map editing mode
     */
    public void toggleEditor() {
        System.out.println("Edit mode Toggled");
        editMode = !editMode;
        avatar.debug();
        addItemsToAvatar();
    }

    /**
     * If we are in editing mode, edit the map
     * @param i
     */
    public void editMap(int i) {
        if (editMode) {
            map.edit(i, avatar.getPosition());
        }
    }

    /**
     * Adds the item to the avatar's inventory
     * @param item
     */
    public void addItem(InventoryItem item) {
        this.avatar.getInventory().addItem(item);
    }

    /**
     * Drops the item at the specified index
     * @param index
     */
    public void dropItem(int index) {
        this.avatar.dropItem(index);
    }

    /**
     * Uses the item at the specified index
     * @param index
     */
    public void useItem(int index) {
        this.avatar.useItem(index);
    }

    public void unequipArmor() {
        this.avatar.unequipArmor();
    }

    public void unequipWeapon() {
        this.avatar.unequipWeapon();
    }

    //for debug mode
    private void addItemsToAvatar(){
        Weapon oneHand = new Weapon(
            "Dagger", 5, Weapon.AS_MEDIUM, Stat.STAT_STRENGTH,
            1, Stat.SKILL_ONEHANDED, 1 );
        avatar.addItem(oneHand);
        Weapon oneHand2 = new Weapon(
                "SmallAxe", 10, Weapon.AS_MEDIUM, Stat.STAT_STRENGTH,
                1, Stat.SKILL_ONEHANDED, 10
                );
        avatar.addItem(oneHand2);
        Weapon oneHand3 = new Weapon(
                "SwordOfFire", 30, Weapon.AS_MEDIUM, Stat.STAT_STRENGTH,
                1, Stat.SKILL_ONEHANDED, 20
                );
        avatar.addItem(oneHand3);

        Weapon twoHand1 = new Weapon(
                "Pitchfork", 10, Weapon.AS_SLOW, Stat.STAT_STRENGTH,
                1, Stat.SKILL_TWOHANDED, 1
                );
        avatar.addItem(twoHand1);
       Weapon twoHand2 = new Weapon(
                "Scythe", 20, Weapon.AS_SLOW, Stat.STAT_STRENGTH,
                1, Stat.SKILL_TWOHANDED, 10
                );
        avatar.addItem(twoHand2);
        Weapon twoHand3 = new Weapon(
                "DemonBlade", 40, Weapon.AS_SLOW, Stat.STAT_STRENGTH,
                1, Stat.SKILL_TWOHANDED, 20
                );
        avatar.addItem(twoHand3);

        Weapon brawl1 = new Weapon(
                "BrassKnuckles", 10, Weapon.AS_FAST, Stat.STAT_STRENGTH,
                1, Stat.SKILL_BRAWLING, 10
                );
        avatar.addItem(brawl1);

        Weapon brawl2 = new Weapon(
                "Katar", 10, Weapon.AS_FAST, Stat.STAT_STRENGTH,
                1, Stat.SKILL_BRAWLING, 20
                );
        avatar.addItem(brawl2);

        Weapon shortBow = new Weapon(
                "ShortBow", 20, Weapon.AS_FAST, Stat.STAT_AGILITY,
                1, Stat.SKILL_RANGED, 10
                );
        avatar.addItem(shortBow);
        Weapon bow = new Weapon(
                "Bow", 30, Weapon.AS_FAST, Stat.STAT_AGILITY,
                1, Stat.SKILL_RANGED, 20
                );
        avatar.addItem(bow);

        Armor leather = new Armor(
                "LeatherVest", 10, Stat.SKILL_LEATHER, 1 );
        avatar.addItem(leather);

        Armor plate = new Armor("Platemail", 20, Stat.SKILL_PLATE, 5);
        avatar.addItem(plate);
        Armor gold = new Armor("GoldenArmor", 30, Stat.SKILL_PLATE, 20);
        avatar.addItem(gold);

        ConsumableItem pot;
        for (int i = 0; i < 5; ++i){
            pot = new ConsumableItem("Potion", new HealEffect(100));
            avatar.addItem(pot);
        }
    }
}

//========================================================\\

