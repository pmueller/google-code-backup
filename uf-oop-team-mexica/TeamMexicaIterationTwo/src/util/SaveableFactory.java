package util;

import model.*;
import model.ability.*;
import model.behavior.*;
import model.effect.*;
import model.entity.*;
import model.entity.npc.*;
import model.influence.*;
import model.item.*;
import model.item.equipment.armor.Armor;
import model.item.equipment.weapon.Weapon;
import model.mapstuff.*;
import model.mapstuff.aoe.AoE;
import model.occupation.*;
import model.stat.*;
import model.stat.modifier.*;

public abstract class SaveableFactory {

    public abstract Saveable construct();

    /**
     * Initializes the saveable factory acceptor with the saveable factories
     * @param sl
     */
    public static void init(saveableFactoryAcceptor sl) {
        /* Use these to add to the list. Please keep it aphlabetical order so we can
         * look through it quickly.
         *
        sf = new SaveableFactory(){public Armor construct(){return new Armor(0);}};
        sl.addFactory("Armor",sf);

        sf = new SaveableFactory(){public Saveable construct(){return new Armor(0);}};
        sl.addFactory("Armor",sf);

        sf = new SaveableFactory(){public Saveable construct(){return new SingularInfluence();}};
        sl.addFactory("SingularInfluence",sf);
         *
         *
         */

        SaveableFactory sf = new SaveableFactory() {

            public Saveable construct() {
                return new Armor(0);
            }
        };
        sl.addFactory("Armor", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new AbilityMap(null);
            }
        };
        sl.addFactory("AbilityMap", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new AoE();
            }
        };
        sl.addFactory("AoE", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Avatar();
            }
        };
        sl.addFactory("Avatar", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new BasicOccupation("default");
            }
        };
        sl.addFactory("BasicOccupation", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new BindWounds();
            }
        };
        sl.addFactory("BindWounds", sf);


        sf = new SaveableFactory() {

            public Saveable construct() {
                return new CloseInfluence();
            }
        };
        sl.addFactory("CloseInfluence", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new ConsumableItem();
            }
        };
        sl.addFactory("ConsumableItem", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Creep();
            }
        };
        sl.addFactory("Creep", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new DamageEffect(0);
            }
        };
        sl.addFactory("DamageEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Decal();
            }
        };
        sl.addFactory("Decal", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new DetectAndRemoveTrap();
            }
        };
        sl.addFactory("DetectAndRemoveTrap", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Entity();
            }
        };
        sl.addFactory("Entity", sf);


        sf = new SaveableFactory() {

            public Saveable construct() {
                return new GeneralAttack();
            }
        };
        sl.addFactory("GeneralAttack", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new HealEffect();
            }
        };
        sl.addFactory("HealEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Heal();
            }
        };
        sl.addFactory("Heal", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new IceWave();
            }
        };
        sl.addFactory("IceWave", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Inventory();
            }
        };
        sl.addFactory("Inventory", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new MagicAttack();
            }
        };
        sl.addFactory("MagicAttack", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new MapGrid();
            }
        };
        sl.addFactory("MapGrid", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new MapSwitchEffect(null);
            }
        };
        sl.addFactory("MapSwitchEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return null;
            }
        };
        sl.addFactory("null", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Observation();
            }
        };
        sl.addFactory("Observation", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Obstacle();
            }
        };
        sl.addFactory("Obstacle", sf);


        sf = new SaveableFactory() {

            public Saveable construct() {
                return new OneShot();
            }
        };
        sl.addFactory("OneShot", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new PickPocket();
            }
        };
        sl.addFactory("PickPocket", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new PickPocketEffect();
            }
        };
        sl.addFactory("PickPocketEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new PortalEffect(new Vector(0, 0));
            }
        };
        sl.addFactory("PortalEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new RangedWeapon();
            }
        };
        sl.addFactory("RangedWeapon", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new SingularInfluence();
            }
        };
        sl.addFactory("SingularInfluence", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new SleepAbility();
            }
        };
        sl.addFactory("SleepAbility", sf);
        sf = new SaveableFactory() {

            public Saveable construct() {
                return new SleepEffect();
            }
        };
        sl.addFactory("SleepEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new SlowingEffect(0);
            }
        };
        sl.addFactory("SlowingEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Smasher();
            }
        };
        sl.addFactory("Smasher", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new SmasherAttackAbility();
            }
        };
        sl.addFactory("SmasherAttackAbility", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Sneak();
            }
        };
        sl.addFactory("Sneak", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new StaffAttack();
            }
        };
        sl.addFactory("Staff", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Stat(0, false, "");
            }
        };
        sl.addFactory("Stat", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new StatContainer();
            }
        };
        sl.addFactory("StatContainer", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new StatEffect(0, null);
            }
        };
        sl.addFactory("StatEffect", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new StatModifierConstant("", 0, 0);
            }
        };
        sl.addFactory("StatModifierConstant", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new StatModifierPercentage("", 0, 0);
            }
        };
        sl.addFactory("StatModifierPercentage", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Summoner();
            }
        };
        sl.addFactory("Summoner", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new TakeableItem("", null);
            }
        };
        sl.addFactory("TakeableItem", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Terrain(false, null, null);
            }
        };
        sl.addFactory("Terrain", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new TigerNPC(null, null, null);
            }
        };
        sl.addFactory("TigerNPC", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new TigerBehavior(null, null);
            }
        };
        sl.addFactory("TigerBehavior", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Tile();
            }
        };
        sl.addFactory("Tile", sf);


        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Weapon(0);
            }
        };
        sl.addFactory("Weapon", sf);

        sf = new SaveableFactory() {

            public Saveable construct() {
                return new Vector(0, 0);
            }
        };
        sl.addFactory("Vector", sf);
        /*Use these for 
        sf = new SaveableFactory(){public Saveable construct(){return new StatModifierPercentage("",0.0,0);}};
        sl.addFactory("StatModifierPercentage",sf);

        sf = new SaveableFactory(){public Saveable construct(){return new Summoner();}};
        sl.addFactory("Summoner",sf);

        sf = new SaveableFactory(){public Saveable construct(){return new TakeableItem(null);}};
        sl.addFactory("TakeableItem",sf);

        sf = new SaveableFactory(){public Saveable construct(){return new Terrain();}};
        sl.addFactory("Terrain",sf);

        sf = new SaveableFactory(){public Saveable construct(){return new TigerNPC();}};
        sl.addFactory("TigerNPC",sf);

        sf = new SaveableFactory(){public Saveable construct(){return new TigerBehavior(null,null);}};
        sl.addFactory("TigerBehavior",sf);
         * 
         */
    }
}
