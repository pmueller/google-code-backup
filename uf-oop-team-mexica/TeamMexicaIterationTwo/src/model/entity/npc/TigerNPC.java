package model.entity.npc;

import java.io.IOException;

import model.*;
import model.ability.SmasherAttackAbility;
import model.behavior.TigerBehavior;
import util.SaverLoader;
import model.stat.Stat;

public class TigerNPC extends NPC {

    /**
     * Constructor: calls super for initial setup, then sets the behavior to
     * a new TigerBehavior, and adds initial experience and gp stats
     * @param mapGrid
     * @param logger
     * @param name
     */
    public TigerNPC(MapGrid mapGrid, Log logger, String name) {
        super(mapGrid, logger, name);

        setBehavior(new TigerBehavior(this, logger));
        this.setRepresentation("Tiger");
        this.getStats().addToBase(Stat.STAT_EXPERIENCE, 100);
        this.getStats().addToBase(Stat.STAT_GP, 20);
        this.addAbility(new SmasherAttackAbility());
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("TigerNPC");
        }
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new TigerNPC( Model.getInstance().getMap(), Model.getInstance().getLog(), getName() );
    }

}
