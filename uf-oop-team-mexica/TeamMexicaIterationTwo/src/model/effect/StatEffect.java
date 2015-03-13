package model.effect;

import java.io.IOException;

import model.entity.Entity;
import model.stat.modifier.*;
import util.SaverLoader;

public class StatEffect extends Effect {

    private StatModifier modifier;

    public StatEffect(int pow, StatModifier modifier) {
        super(pow);
    }

    public void applyToEntity(Entity target) {
        target.addStatModifier(this.modifier);
    }

    public Effect clone() {
        return new StatEffect(this.getPower(), this.getModifier());
    }

    public StatModifier getModifier() {
        return this.modifier;
    }

    public void setModifier(StatModifier mod) {
        this.modifier = mod;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.modifier = s.pullSaveable();
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("StatEffect");
        }

        s.push(this.modifier, true);
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public String getRepresentation() {
        return null;
    }
}
