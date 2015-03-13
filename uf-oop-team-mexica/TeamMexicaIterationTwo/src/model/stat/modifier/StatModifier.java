package model.stat.modifier;

import java.io.IOException;
import util.*;
import model.Updateable;
import model.Model;

/**
 * Represents a persistent effect which alters an Entity for some duration (including instantaneously and indefinitely).
 * <p>Gets added to the relevant stat object owned by the Entity this modifier is targeted toward.
 * <p>Simply waits for <code>duration</code> game clock cycles to elapse. The associated Stat provides any interested
 * party with its total effective value on request by collecting the values from its StatModifiers.
 */
public abstract class StatModifier implements Saveable, Updateable {

    /** The signed magnitude of this modifier's effect per game clock cycle. */
    protected int value;
    /** The number of game clock cycles to apply this modifier over. A value of -1 results in indefinite modification. */
    protected int duration;
    /** The number of cycles that have been counted since this modifier's initialization. */
    protected int cyclesElapsed;
    protected String statName;
    protected boolean done;
    private model.stat.Stat owner;

    /**
     * Constructor: sets the ivars, and registers for update if duration != -1
     * @param name	
     * @param val	Magnitude of this modifier's effect per game clock cycle.
     * @param dur	Pass "-1" to denote permanent modification
     */
    public StatModifier(String name, int val, Integer dur) {
        statName = name;
        value = val;
        duration = dur;
        done = false;

        if (duration != -1) {
            this.registerForUpdate();
        }
    }

    /**
     * Sets the owner
     * @param s
     */
    public void setOwner(model.stat.Stat s) {
        owner = s;
    }

    /**
     * Sets the value
     * @param val
     */
    public void setValue(int val) {
        this.value = val;
    }

    /**
     *
     * @return the value
     */
    public int getValue() {
        return this.value;
    }

    /**
     *
     * @return the statName
     */
    public String getName() {
        return this.statName;
    }

    /**
     * Sets the name
     * @param n
     */
    public void setName(String n) {
        this.statName = n;
    }

    /**
     * Returns whether or not this modifier is done or not, used for modifiers
     * that act over a duration
     * @return done
     */
    public boolean isDone() {
        return done;
    }

    abstract public StatModifier clone();

    /**
     * to calculate the value for this modifier, since it can be either
     * a percentage or a constant value, we pass in the base value of the
     * affected stat so we can calculate the percentage properly
     */
    abstract public int calculate(int baseValue);

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.value = s.pullInt();
        this.duration = s.pullInt();
        this.cyclesElapsed = s.pullInt();
        this.statName = s.pullString();
        this.done = s.pullBool();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("StatModifier");
        }

        s.push(this.value);
        s.push(this.duration);
        s.push(cyclesElapsed);
        s.push(this.statName);
        s.push(this.done);

        if (notSuperClass) {
            s.close();
        }
    }

    /**
     * Called in Stat.update() on each game clock cycle. Increments the cycle counter.
     * If the counter reaches the duration value, this modifier is marked for removal.
     */
    public void registerForUpdate() {
        Model.getInstance().registerObserver(this);
    }

    public void update() {
        System.out.println(duration);
        duration = --duration;
        if (duration == 0) {
            done = true;
        }

        if (done) {
            owner.removeModifier(this);
            unRegisterForUpdate();
        }
    }

    public void unRegisterForUpdate() {
        model.Model.getInstance().removeObserver(this);
    }
}
