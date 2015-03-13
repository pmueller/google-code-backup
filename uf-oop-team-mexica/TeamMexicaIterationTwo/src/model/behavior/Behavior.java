package model.behavior;

import model.Actor;
import model.entity.npc.NPC;
import util.*;

public interface Behavior extends Saveable {

    public void interactWithActor(Actor actor);

    public void setOwner(NPC owner);
}
