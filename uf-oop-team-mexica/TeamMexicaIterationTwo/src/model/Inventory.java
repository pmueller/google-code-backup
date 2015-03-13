package model;

import java.io.*;
import java.util.*;

import model.item.*;
import model.item.equipment.armor.Armor;
import model.item.equipment.weapon.Weapon;
import util.*;

public class Inventory implements util.Saveable {

    private ArrayList<InventoryItem> items;
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    public static final int MAX_SIZE = 30;

    /**
     * Constructor: initializes items to empty list, and weapon/armor to null
     */
    public Inventory() {
        items = new ArrayList<InventoryItem>();
        equippedWeapon = null;
        equippedArmor = null;
    }

    /**
     *
     * @param index
     * @return the InventoryItem at the specified index, or null if out of bounds
     */
    public InventoryItem itemAtIndex(int index) {
        try {
            return items.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     *
     * @return the equippedWeapon
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    /**
     * equippedWeapon setter
     * @param w
     */
    public void setEquippedWeapon(Weapon w) {
        if (w == null) {
            return;  // no weapon, return
        }
        unequipWeapon(); // unequips the current weapon if any
        equippedWeapon = w;
        items.remove(w);        // remove weapon from list of items

    }

    /**
     *
     * @return the equippedArmor
     */
    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    /**
     * equippedArmor setter
     * @param a
     */
    public void setEquippedArmor(Armor a) {
        if (a == null) {
            return;  // no armor, return
        }
        equippedArmor = a;
        items.remove(a);        // remove armor from list of items
    }

    /**
     *
     * @return the items
     */
    public List<InventoryItem> getItems() {
        return items;
    }

    /**
     * Adds the item to the inventory
     * @param item
     * @return false if the item is null, true otherwise
     */
    public boolean addItem(InventoryItem item) {
        if (item == null) {
            System.out.println("potion not added");
            return false; // no item, return false
        }
        System.out.println("potion added");
        items.add(item);                // add item to list of items
        return true;
    }

    /**
     * Removes the item at the specified index
     * @param index
     * @return the removed item, or null if out of bounds
     */
    public InventoryItem removeItemAtIndex(int index) {
        try {
            return items.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Removes the item specified
     * @param item
     * @return the removed item, or null if out of bounds
     */
    public InventoryItem removeItem(InventoryItem item) {
        try {
            return removeItemAtIndex(items.indexOf(item));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Unequips the current weapon
     * @return the previously equipped weapon, or null if there is no weapon
     * equipped
     */
    public Weapon unequipWeapon() {
        if (equippedWeapon == null) {
            return null;
        }

        Weapon unequipped = equippedWeapon;
        // unequip the weapon
        equippedWeapon = null;

        // re-add the weapon to the inventory
        addItem(unequipped);

        return unequipped;
    }

    /**
     * Unequips the current armor
     * @return the previously equipped armor, or null if there is no armor
     * equipped
     */
    public Armor unequipArmor() {
        if (equippedArmor == null) {
            return null;
        }

        Armor unequipped = equippedArmor;
        // unequip the armor
        equippedArmor = null;

        // re-add the armor to the inventory
        addItem(unequipped);

        return unequipped;
    }

    /**
     *
     * @return number of items in inventory
     */
    public int size() {
        return items.size();
    }

    /**
     * 
     * @return an iterator for traversing items in inventory
     */
    public Iterator<InventoryItem> iterator() {
        return items.iterator();
    }

    /**
     * Drops the item onto the specified tile
     * @param index
     * @param tile
     * @return false if index is out of bounds, true otherwise
     */
    public boolean dropItemAtIndexOntoTile(int index, Tile tile) {
        InventoryItem removedItem = removeItemAtIndex(index);
        //String rep = removedItem.getTakeable().getRepresentation();
        if (removedItem == null) {
            // index out of bounds, return false
            return false;
        }

        removedItem.dropOntoTile(tile);
        // create new takeable item, add to tile
        //TakeableItem ti = new TakeableItem(removedItem,rep);
        //ti.registerToTile(tile);

        return true;
    }

    /**
     * Tests if the named item is in the inventory
     * @param itemName
     * @return true of there is an item in the inventory by the specified name,
     * false otherwise
     */
    public boolean containsItem(String itemName) {
        for (InventoryItem i : items) {
            if (i.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * InventoryVisitor accept method
     * @param iv
     */
    public void accept(InventoryVisitor iv) {
        iv.visit(this);
    }

    public void save(SaverLoader s, boolean nSC) throws IOException {
        if (nSC) {
            s.start("Inventory");
        }

        s.push(this.equippedArmor, true);
        s.push(this.equippedWeapon, true);
        s.push(this.items);

        if (nSC) {
            s.close();
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.equippedWeapon = s.pullSaveable();
        this.equippedArmor = s.pullSaveable();
        this.items = s.pullList();

        if (notSuperClass) {
            s.assertEnd();
        }
    }
}
