package model.stat.modifier;

import java.io.IOException;
import util.SaverLoader;

public class StatModifierConstant extends StatModifier {

    /**
     *
     * @param name	
     * @param val	Magnitude of this modifier's effect per game clock cycle.
     * @param dur	Pass "-1" to denote permanent modification
     */
    public StatModifierConstant(String name, int val, Integer dur) {
        super(name, val, dur);
    }

    /**
     * Since this is a constant modifier, there is no calculation to be done
     * @param baseValue
     * @return the value, no calculation necessary
     */
    public int calculate(int baseValue) {
        return this.value;
    }

    public StatModifierConstant clone() {
        return new StatModifierConstant(new String(this.getName()), this.value, this.duration);
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("StatModifierConstant");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
