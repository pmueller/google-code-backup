package model.ability;

import util.*;
import java.io.IOException;
import model.effect.SleepEffect;
import model.influence.*;
import model.mapstuff.aoe.AoE;
import util.SaverLoader;

public class SleepAbility extends Ability {

    public SleepAbility() {
        super("Sleep");
    }

    public void initAbility() {

        AoE sleepAoE = new AoE(new SleepEffect(100), 3);
        setInitialPower(1);
        sleepAoE.setRepresentation("Sleep");
        setAreaEffect(sleepAoE);
        setMaxRange(10);
        setRateOfPropagation(1);
        setAssociatedStat("Enchantment");
        setManaCost(300);

        //  this.setShape( new LinearInfluence( new Vector(5,5), Vector.EAST, 5 ) );

        //setShape(new LinearInfluence());
        setHitsOrigin(false);
        // origin, direction, and power will be set when the ability is used
        
    }

    @Override
    public void prepareToCast(Vector origin, Vector direction, int power) {
        //super.prepareToCast(origin, direction, power);
        this.setShape(new RadialInfluence(origin, 3, false));
        this.setInitialPower(power);
        //this.setShape( new LinearInfluence( origin, direction, 5 ) );
        // this.setShape( new AngularInfluence( origin, direction, 3 ));
    }

    public Ability clone() {
        return new SleepAbility();
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("SleepAbility");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
