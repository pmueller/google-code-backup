package model.entity.npc;

import util.*;
import java.io.*;
import model.*;
import model.behavior.*;
import model.entity.Entity;
import model.occupation.BasicOccupation;
import model.stat.Stat;
import model.entity.Avatar;

public abstract class NPC extends Entity implements Saveable, Cloneable {

    private UpdateableBehavior behavior;

    /**
     * Constructor: calls super for setup
     * @param mapGrid
     * @param logger
     * @param name
     */
    public NPC(MapGrid mapGrid, Log logger, String name) {
        super(mapGrid, logger, name, new BasicOccupation("Basic"));

    }

    /**
     * Sets the specified behavior
     * @param behavior
     */
    public void setBehavior(UpdateableBehavior behavior) {
        this.behavior = behavior;
    }

    /**
     *
     * @return the behavior
     */
    public Behavior getBehavior() {
        return behavior;
    }

    /**
     * Passes this method call to the behavior to handle
     * @param actor
     */
    public void interactWithActor(Actor actor) {
        getBehavior().interactWithActor(actor);
    }

    /**
     * Handles entity death, adds initial experience and gp stats
     */
    public void deadEntity() {
        super.deadEntity();
        System.out.println("NPC deadEntity");
        //get a reference to the Avatar
        Avatar avatar = Model.getInstance().getAvatar();
        this.getLogger().logGeneral(this.getName() + " has died!");
        //get the amount of Exp to reward the player
        int exp = this.getStats().getBaseValueForName(Stat.STAT_EXPERIENCE);
        avatar.rewardExp(exp);
        this.getLogger().logGeneral("You gain " + exp + " experience.");
        //get the amount of GP to reward the player
        int gp = this.getStats().getBaseValueForName(Stat.STAT_GP);
        avatar.rewardGP(gp);
        this.getLogger().logGeneral("You get " + gp + " gold.");
    }

    public void update() {
        super.update();
        if (!this.isBusy()) {
            behavior.updateBehavior();
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.behavior = s.pullSaveable();
        this.behavior.setOwner(this);
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("NPC");
        }

        s.push(this.behavior, true);
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
