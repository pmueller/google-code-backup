package model.mapstuff;

import java.io.IOException;
import model.Actor;
import model.Tile;
import model.entity.Avatar;
import util.SaverLoader;

public abstract class Interactive extends MapStuff implements util.Saveable {

    private Tile tile;

    /**
     * Default constructor: calls super
     */
    public Interactive() {
        super();
    }

    /**
     * Constructor: calls super
     * @param name
     */
    public Interactive(String name) {
        super(name);
    }

    /**
     * Registers the Interactive to a tile and unregisters it
     * from its current tile (if it has a current tile).
     *
     * @param t
     */
    public void registerToTile(Tile t) {
        if(this instanceof Avatar){
            int n = 5;
        }
        unregisterFromTile();
        t.add(this);
        this.tile = t;
    }

    /**
     * Unregisters the Interactive from its current tile
     * (if it has a current tile).
     */
    protected void unregisterFromTile() {
        if (tile != null) {
            tile.remove(this);
        }
    }

    /**
     *
     * @return true if an Entity can move through this object
     */
    public abstract boolean blocksMovement();

    /**
     * Interacts this object with the specified actor
     * @param actor
     */
    public abstract void interactWithActor(Actor actor);

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Interactive");
        }
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}

//========================================================\\
