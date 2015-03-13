package util;

import java.util.List;

public class InventoryVisitor extends AvatarVisitor {

    //visitor state from the inventory
    private String weapon;
    private String armor;
    private String[] inv;

    /**
     *
     * @return the armor
     */
    public String getArmor() {
        return armor;
    }

    /**
     *
     * @return the weapon
     */
    public String getWeapon() {
        return weapon;
    }

    /**
     *
     * @return the inventory
     */
    public String[] getInventory() {
        return inv;
    }

    /**
     * visit the avatar, which accepts onto the inventory
     * @param a
     */
    public void visit(model.entity.Avatar a) {
        a.getInventory().accept(this);
    }

    /**
     * visit the inventory, which gets the equipment and inventory
     * @param inventory
     */
    public void visit(model.Inventory inventory) {
        //if there is a weapon, get the name
        if (inventory.getEquippedWeapon() != null)
            this.weapon = inventory.getEquippedWeapon().getName();
        else
            weapon = "";
        //if there is an armor, get the name
        if (inventory.getEquippedArmor() != null)
            this.armor = inventory.getEquippedArmor().getName();
        else
            armor = "";

        //get all items in inventory
        java.util.Iterator<model.item.InventoryItem> it = inventory.iterator();
        inv = new String[ inventory.MAX_SIZE ];
        int count = 0;
        for( ; it.hasNext() && count < inventory.MAX_SIZE; ) {
            inv[count] = it.next().getName();
            ++count;

        }
        //fill with empty if not full
        for(int i = count; i < inventory.MAX_SIZE; ++i) {
            inv[i] = "empty";
        }

    }
    
}
