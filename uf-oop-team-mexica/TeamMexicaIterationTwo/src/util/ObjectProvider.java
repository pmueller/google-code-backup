package util;

import java.util.*;

public class ObjectProvider implements saveableFactoryAcceptor {
    private  HashMap<String, SaveableFactory> map;

    /**
     * Adds the factory to the hash map
     * @param name
     * @param com
     */
    public void addFactory(String name , SaveableFactory com){
        if(map == null)
            map = new HashMap<String, SaveableFactory>();
        map.put(name, com);;

    }

    /**
     * Adds the list of string-factory pairs to the hash map
     * @param al
     */
    public void addFactory(ArrayList<Pair<String,SaveableFactory>> al){
        Pair<String,SaveableFactory> p;
        Iterator<Pair<String,SaveableFactory>> iter = al.iterator();
        while(iter.hasNext()){
            p = iter.next();
            addFactory(p.getLeft(),p.getRight());
        }
    }

    /**
     * Removes the factory for the specified name
     * @param name
     */
    public void removeFactory(String name){
        map.remove(name);
    }

    /**
     * Gets the specified factory from the hash map, and create a new instance
     * @param <T>
     * @param name
     * @return
     */
    public <T> T pullObject(String name){
        T t = (T) map.get(name).construct();
        return t;
    }

    /**
     * Initializes the saveable factory
     */
    public void init(){
        SaveableFactory.init(this);
    }
}
