package model.mapstuff;

import util.*;
import java.io.*;
import model.Actor;
import model.effect.Effect;

public class Terrain extends Interactive {

    private boolean blocksMovement;
    private Effect effect;

    public Terrain(boolean passable, Effect e, String rep) {
        blocksMovement = !passable;
        effect = e;
        setRepresentation(rep);
    }

    /**
     * Applies the effect to the actor
     * @param actor
     */
    public void interactWithActor(Actor actor) {
        if (effect != null) {
            actor.applyEffect(effect);
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.blocksMovement = s.pullBool();
        this.effect = s.pullSaveable();
        super.load(s, false);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Terrain");
        }

        s.push(this.blocksMovement);
        s.push(this.effect, true);
        super.save(s, false);
        if (notSuperClass) {
            s.close();
        }
    }

    public boolean blocksMovement() {
        return blocksMovement;
    }
}
