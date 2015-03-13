package model.effect;

import java.io.IOException;
import util.SaverLoader;
import model.entity.Entity;

public class MapSwitchEffect extends Effect {

    private String mapName;

    public MapSwitchEffect(String mapName) {
        super();
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setMapName(String name) {
        this.mapName = name;
    }

    public void applyToEntity(Entity target) {
        // load new map
    }

    public Effect clone() {
        return new MapSwitchEffect(this.getMapName());
    }

    public String getRepresentation() {
        return null;
    }

    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.mapName = s.pullString();
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("MapSwitchEffect");
        }

        s.push(this.mapName);
        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }
}
