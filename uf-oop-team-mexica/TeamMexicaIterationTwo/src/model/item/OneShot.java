package model.item;

import java.io.*;
import model.Actor;
import model.effect.Effect;
import model.mapstuff.Interactive;
import util.*;

/**
 * OneShots can be made on the fly or subclassed to be more complicated
 * like a treasure chest or switch. By default a OneShot will apply its
 * effect and then remove itself from the board. Subclasses can override
 * fired() to check for a condition, or define their own Effect that
 * handles the condition.
 */
public class OneShot extends Interactive implements Saveable {

    private Effect effect;

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
     * Entities cannot walk over OneShots
     * @return
     */
    public boolean blocksMovement() {
        return true;
    }

    /**
     * Applies the effect to the actor, then calls fired()
     * @param actor
     */
    public void interactWithActor(Actor actor) {
        actor.applyEffect(effect);
        fired();
    }

    /**
     * Called after the this OneShot has been triggered, calls
     * unregisterFromTile()
     */
    public void fired() {
        unregisterFromTile();
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("OneShot");
        }

        s.push(this.effect, true);
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.effect = s.pullSaveable();
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }
}
