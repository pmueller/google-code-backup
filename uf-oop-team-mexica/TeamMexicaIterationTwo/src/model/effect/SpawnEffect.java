package model.effect;

import java.io.IOException;
import model.entity.Entity;
import util.SaverLoader;

public class SpawnEffect extends Effect {

    @Override
    public void applyToEntity(Entity target) {

    }

    @Override
    public String getRepresentation() {
        return null;
    }

    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("SpawnEffect");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

}
