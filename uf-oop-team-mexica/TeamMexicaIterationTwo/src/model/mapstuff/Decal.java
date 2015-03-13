package model.mapstuff;

import java.io.IOException;
import util.*;

public class Decal extends MapStuff {

    /* *************************************************************************
     *
     * Saveable Interface Methods
     *
     **************************************************************************/
    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        super.load(s, false);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("Decal");
        }
        super.save(s, false);
        if (notSuperClass) {
            s.close();
        }
    }
}

//========================================================\\
