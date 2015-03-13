package util;

import java.io.IOException;

public interface Saveable {

    public void load(SaverLoader s, boolean notSuperClass) throws IOException;

    public void save(SaverLoader s, boolean notSuperClass) throws IOException;
}
