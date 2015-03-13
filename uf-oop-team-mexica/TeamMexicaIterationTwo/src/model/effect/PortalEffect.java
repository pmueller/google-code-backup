package model.effect;

import java.io.IOException;
import util.Vector;
import model.entity.Entity;
import util.SaverLoader;

public class PortalEffect extends Effect {

    private Vector destination;

    public PortalEffect(Vector delta) {
        super();

        destination = delta;
    }

    public Vector getDestination() {
        return this.destination;
    }

    public void setDestination(Vector delta) {
        this.destination = delta;
    }

    public void applyToEntity(Entity target) {
        target.moveToTile(this.destination);
    }

    public Effect clone() {
        return new PortalEffect(this.getDestination());
    }

    public String getRepresentation() {
        return "Star";
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.destination = s.pullSaveable();
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("PortalEffect");
        }

        s.push(this.destination, true);
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
