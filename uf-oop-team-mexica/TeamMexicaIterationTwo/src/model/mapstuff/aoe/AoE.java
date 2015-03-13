package model.mapstuff.aoe;

import java.io.IOException;
import model.*;
import model.effect.Effect;
import model.mapstuff.Interactive;
import util.*;

public final class AoE extends Interactive implements Updateable, Saveable, Cloneable {

    private Effect effect;
    private int duration;  // -1 for infinite

    /**
     * Constructor: effect is null and duration is 0
     */
    public AoE() {
        effect = null;
        duration = 0;
    }

    /**
     * Constructor: sets the ivars, and sets the representation to the effect's
     * representation
     * @param e
     * @param d
     */
    public AoE(Effect e, Integer d) {
        effect = e;
        duration = d;
        this.setRepresentation(e.getRepresentation());
    }

    /**
     * Calls super, then calls registerForUpdate
     * @param t
     */
    public void registerToTile(Tile t) {
        super.registerToTile(t);
        if (duration >=0)
            this.registerForUpdate();
    }

    /**
     * Calls super, then calls unRegisterForUpdate
     */
    public void unregisterFromTile() {
        super.unregisterFromTile();
        this.unRegisterForUpdate();
    }

    /**
     * Sets the effect
     * @param e
     */
    public void setEffect(Effect e) {
        this.effect = e;
    }

    /**
     *
     * @return the effect
     */
    public Effect getEffect() {
        return this.effect;
    }

    /**
     * Sets the duration
     * @param dur
     */
    public void setDuration(Integer dur) {
        this.duration = dur;
    }

    /**
     *
     * @return the duration
     */
    public Integer getDuration() {
        return this.duration;
    }

    /**
     * Sets the effect's power
     * @param pow
     */
    public void setEffectPower(int pow) {
        effect.setPower(pow);
    }

    public AoE clone() throws CloneNotSupportedException {
        return (AoE) super.clone();
    }

    /* *************************************************************************
     *
     * Interactive Abstract Methods
     *
     **************************************************************************/
    public boolean blocksMovement() {
        return false;
    }

    /* *************************************************************************
     *
     * Updateable Interface Methods
     *
     **************************************************************************/
    public void registerForUpdate() {
        Model.getInstance().registerObserver(this);
    }

    public void update() {
        if(duration-- <0)
            unregisterFromTile();
    }


    /* *************************************************************************
     *
     * Saveable Interface Methods
     *
     **************************************************************************/
    public void interactWithActor(Actor actor) {
        actor.applyEffect(effect);
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("AoE");

        }

        s.push(this.effect, true);
        s.push(this.duration);
        super.save(s, false);
        if (notSuperClass) {
            s.close();
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.effect = s.pullSaveable();
        this.duration = s.pullInt();
        super.load(s, false);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void unRegisterForUpdate() {
        model.Model.getInstance().removeObserver(this);
        
    }
}

//========================================================\\

