package model.item;

import java.io.IOException;
import model.effect.*;
import model.entity.*;
import util.SaverLoader;

public class ConsumableItem extends InventoryItem {
    private int numOfCharges;
    private Effect effect;

    public ConsumableItem() {
        super();
    }

    public ConsumableItem(String name, Effect e) {
        super(name);
        effect = e;
        numOfCharges = 1;
    }

    public ConsumableItem(String name, Effect e, int c) {
        super(name);
        effect = e;
        numOfCharges = c;
    }

    @Override
    public void useOnEntity(Entity e) {
        e.applyEffect(effect);
        if (--numOfCharges == 0) {
            e.getInventory().removeItem(this);
        }
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.numOfCharges = s.pullInt();
        this.effect = s.pullSaveable();
        super.load(s, false);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("ConsumableItem");
        }

        s.push(this.numOfCharges);
        s.push(this.effect, true);
        super.save(s, false);
        
        if (notSuperClass) {
            s.close();
        }
    }
}