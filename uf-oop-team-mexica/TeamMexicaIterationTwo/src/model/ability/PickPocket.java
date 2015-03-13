package model.ability;

import java.io.IOException;
import model.effect.PickPocketEffect;
import model.influence.SingularInfluence;
import model.mapstuff.aoe.AoE;
import model.stat.Stat;
import util.SaverLoader;
import util.Vector;

public class PickPocket extends Ability {
    public PickPocket() { super("PickPocket"); }

    public void prepareToCast(Vector orig, Vector dir, int pow) {
        setShape( new SingularInfluence( orig.add(dir) ) );
        AoE aoe = new AoE( new PickPocketEffect(  ), 1 );
        aoe.setRepresentation( "Star" ); // change later
        setAreaEffect( aoe );
    }

    public void initAbility() {
        setAssociatedStat(Stat.SKILL_PICKPOCKET);
        setCoolDown(20);
        setInitialPower( 1 );
        setManaCost(20);
    }

    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("PickPocket");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

}
