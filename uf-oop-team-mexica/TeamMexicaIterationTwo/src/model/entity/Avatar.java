package model.entity;

import java.io.IOException;
import model.*;
import model.occupation.Occupation;
import model.stat.Stat;
import util.*;

public class Avatar extends Entity implements util.Saveable {

    boolean debug;
    private int statPoints;
    private int skillPoints;

    public static final int EXP_FOR_LVL = 300;
    
    /**
     * Default constructor: sets the orientation to facing down
     */
    public Avatar() {
        debug = false;
        this.setOrientation(new util.Vector(0, -1));
    }

    /**
     * Constructor: calls super to do initial setup, starts with two lives
     * @param mapGrid
     * @param logger
     * @param name
     * @param occupation
     */
    public Avatar(MapGrid mapGrid, Log logger, String name, Occupation occupation) {
        super(mapGrid, logger, name, occupation);
        debug = false;
        // the player will start with 3 lives instead of 1
        this.getStats().addToBase(Stat.STAT_LIVES, 2);

        this.statPoints = 5;
        this.skillPoints = 15;
    }

    public int getStatPoints() {
        return statPoints;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     * Adds the specified value to the experience stat
     * @param value
     */
    public void rewardExp(int value) {
        this.getStats().addToBase(Stat.STAT_EXPERIENCE, value);
    }

    /**
     * Adds the specified value to the GP stat
     * @param value
     */
    public void rewardGP(int value) {
        this.getStats().addToBase(Stat.STAT_GP, value);
    }

    /**
     * Calls super to handle entity death
     */
    @Override
    public void deadEntity() {
        super.deadEntity();
        
        //this.getStats().addToBase(Stat.STAT_LIVES, -1);
        if (this.getStats().getBaseValueForName(Stat.STAT_LIVES) > 0) {
           super.respawn();
        } else {
            this.addToCoolDownTimer(10000);
          this.getLogger().logGeneral(" GAME OVER !!!!!!!!!!!!!!!!!!!!! ");
            Model.getInstance().setPaused( true );
        }
        //    System.out.println("Wipe yourself off man, you dead!");
        //
    }

    /**
     * AvatarVisitor accept method
     * @param v
     */
    public void accept(AvatarVisitor v) {
        v.visit(this);
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Avatar");
        }

        s.push(statPoints);
        s.push(skillPoints);
        s.push(this.debug);
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        statPoints = s.pullInt();
        skillPoints = s.pullInt();
        this.debug = s.pullBool();
        super.load(s, false);
        Model.getInstance().setAvatar(this);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    /**
     * Adds support for a debug mode
     * @param direction
     */
    public void attemptMoveDirectionally(util.Vector direction) {
        if (!debug) {
            //System.out.println(getMap().getTileAtPosition(getPosition()).toString());
            super.attemptMoveDirectionally(direction);
        } else {
            setOrientation(direction);
            util.Vector newPosition = getPosition().add(direction);
            model.Tile t = getMap().getTileAtPosition(newPosition);
            if (t == null) {
                return;
            }
            this.setPosition(newPosition);
            registerToTile(t);
        }
    }

    /*
     * interface for leveling stats
     */
    /**
     * Upgrades the specified stat by one
     * @param s
     */
    public void upgradeStat(String s) {

        if (statPoints > 0) {
            getStats().upgradeStat(s, 1);
            statPoints -= 1;
        }
    }

    /**
     * Upgrades the specified skill by one
     * @param s
     */
    public void upgradeSkill(String s) {
        if (skillPoints > 0) {
            getStats().upgradeStat(s, 1);
            skillPoints -= 1;
        }
    }

    @Override
    public void update() {
        super.update();
        int currentExp = getStats().getBaseValueForName(Stat.STAT_EXPERIENCE);
        int currentLvl = getStats().getBaseValueForName(Stat.STAT_LEVEL);
        if ((currentExp + EXP_FOR_LVL) - (currentLvl * EXP_FOR_LVL) >= EXP_FOR_LVL) { // 100 exp per lvl
            //add stat and skill points on level up
            statPoints += 5;
            skillPoints += 15;
            //call levelEntity
            this.getOccupation().levelEntity(this.getStats());

        }
        //System.out.println("Updating Avatar");
    }

    /**
     * Toggles debug mode
     */
    public void debug() {

        debug = !debug;
    }
}
