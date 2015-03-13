package model;

import util.Vector;
import model.effect.Effect;

public interface Actor {

    public void applyEffect(Effect eff);

    public Inventory getInventory();

    public Vector getPosition();
}
