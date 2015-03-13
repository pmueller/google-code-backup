package model.item;

import util.*;
import java.io.*;
import model.Actor;
import model.mapstuff.Interactive;

public class TakeableItem extends Interactive {

    private InventoryItem inventoryItem;

    public TakeableItem(String s, String r) {
        inventoryItem = new InventoryItem(s);
        setRepresentation(r);
    }

    public TakeableItem(InventoryItem i, String r) {
        inventoryItem = i;
        setRepresentation(r);
    }

    /**
     * Entities pick up takeable items by walking onto them
     * @return
     */
    public boolean blocksMovement() {
        return false;
    }

    /**
     * Adds the associated inventory item to the actor's inventory
     * @param actor
     */
    public void interactWithActor(Actor actor) {
        actor.getInventory().addItem(inventoryItem);
         this.unregisterFromTile();
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.inventoryItem = s.pullSaveable();
        super.load(s, false);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("TakeableItem");
        }
        s.push(this.inventoryItem, true);
        super.save(s, false);
        if (notSuperClass) {
            s.close();
        }
    }
}
