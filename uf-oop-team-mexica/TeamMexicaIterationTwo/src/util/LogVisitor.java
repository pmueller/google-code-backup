package util;

import java.util.ArrayList;
import java.util.List;

public class LogVisitor {

    /**
     * strings to add to log
     */
    private List<String> updates;

    /**
     * visit the log, get the new entries
     * @param l
     */
    public void visit(model.Log l) {
        //get any new entries then clear the log
        updates = new ArrayList<String>();
        for (String x : l.getLog()) {
            updates.add(x);
        }
        l.clear();
    }

    /**
     *
     * @return the updates
     */
    public List<String> getLogUpdates() {
        return updates;
    }
}
