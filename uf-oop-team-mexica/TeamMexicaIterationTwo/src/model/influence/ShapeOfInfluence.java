package model.influence;

import java.util.Iterator;
import java.util.List;
import util.*;

/**
 * Iterator that returns 'levels' of List<Vector>, starting from level 0,
 * which may or may not be the origin Vector (depends on the shape). The next
 * levels (if any) depend on the particular shape. Use SingularInfluence for
 * a shape representing just the origin (if this is really needed at all).
 */
public abstract class ShapeOfInfluence implements Iterator<List<Vector>>, Saveable {

    public void remove() {
        throw new UnsupportedOperationException("Remove not supported.");
    }
}
