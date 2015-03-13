package model.behavior;

import java.io.IOException;
import model.*;
import model.entity.npc.NPC;
import util.*;

public class ShopKeeperBehavior implements UpdateableBehavior {

    public ShopKeeperBehavior(NPC npc, Log logger) {
        
    }

    public void updateBehavior() {

    }

    public void interactWithActor(Actor actor) {

    }

    public void setOwner(NPC owner) {

    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {


        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("ShopKeeperBehavior");
        }



        if (notSuperClass) {
            s.close();
        }
    }
}