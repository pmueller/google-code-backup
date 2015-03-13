package model.item;

import java.io.IOException;
import model.entity.Entity;
import model.Tile;
import util.Saveable;
import util.SaverLoader;

public class InventoryItem implements Saveable {
    private String name;

    /**
     * Constructor: initializes the item to a default name
     */
    public InventoryItem() {
        this("Unnamed item");
    }

    /**
     * Constructor: initializes the item name to the specified name
     * @param name
     */
    public InventoryItem(String name) {
        this.name = name;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to use this item on the specified entity
     * @param e
     */
    public void useOnEntity(Entity e){
        return;
    }
    /**
     * Drops the item onto the specified tile
     * @param t
     */
    public void dropOntoTile(Tile t) {
        TakeableItem takeable = new TakeableItem(this, this.getName());
        takeable.registerToTile(t);
    }

    /**
     * Boolean test to determine whether two items have the same name
     * @param i
     * @return true of they have the same name, false otherwise
     */
    public boolean equals(InventoryItem i) {
        return i.getName().equals(this.name);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.name = s.pullString();

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("InventoryItem");
        }

        s.push(this.name);

        if (notSuperClass) {
            s.close();
        }
    }
}
