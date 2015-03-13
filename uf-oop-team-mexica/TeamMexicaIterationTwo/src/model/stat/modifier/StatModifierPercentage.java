package model.stat.modifier;

import java.io.IOException;
import util.SaverLoader;

public class StatModifierPercentage extends StatModifier {

    public StatModifierPercentage(String string, int percentage, int i) {
        super(string, percentage, i);
    }

    /**
     * Calculates the modifier's effect on the base value
     * @param baseValue
     * @return the calculated value
     */
    public int calculate(int baseValue) {
        // returns a percentage of baseValue
        return baseValue * this.value / 100;
    }

    public StatModifierPercentage clone() {
        return new StatModifierPercentage(this.getName(), new Integer(this.value), new Integer(this.duration));
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("StatModifierPercentage");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
