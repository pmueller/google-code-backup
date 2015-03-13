package model.item;

import java.io.IOException;
import model.Actor;
import model.entity.Entity;
import model.mapstuff.Interactive;
import util.*;

public class Obstacle extends Interactive {

    /**
     * Obviously, we can't move onto obstacles
     * @return
     */
    public boolean blocksMovement() {
        return true;
    }

    public void interactWithEntity(Entity entity) {
    }

    /* *************************************************************************
     *
     * Saveable Interface Methods
     *
     **************************************************************************/
    public void interactWithActor(Actor actor) {
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Obstacle");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }
}
