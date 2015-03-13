package model.entity.npc;

import model.*;
import model.behavior.ShopKeeperBehavior;

public class ShopKeeper extends NPC {

    public ShopKeeper(MapGrid mapGrid, Log logger, String name) {
        super(mapGrid, logger, name);
        setBehavior(new ShopKeeperBehavior(this, logger));
        this.setRepresentation("ShopKeeper");
    }
}