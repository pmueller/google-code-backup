package model.mapstuff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.Vector;
import model.Model;
import model.Updateable;
import model.entity.Entity;
import model.influence.RadialInfluence;
import util.Saveable;
import util.SaverLoader;


public class SpawnPoint extends MapStuff implements Updateable, Saveable {
    private int entitiesOnMap;
    private Entity prototype;
    private ArrayList<Entity> entities;
    private ArrayList<Vector> vectorList;
    private int size;

    public SpawnPoint( Entity prototype, int entitiesOnMap, int size ) {
        this.entitiesOnMap = entitiesOnMap;
        this.prototype = prototype;
        this.size = size;

        registerForUpdate();
    }

    @Override
    public void setPosition( Vector v ) {
        super.setPosition( v );

        init( v );
    }

    private void init( Vector v ) {
        vectorList = new ArrayList<Vector>();
        RadialInfluence shape = new RadialInfluence( v, size , true);
        while ( shape.hasNext() ) {
            List<Vector> temp = shape.next();
            for ( Vector vec : temp ) vectorList.add( vec );
        }

        if ( vectorList.isEmpty() ) return;

        if ( entities == null ) {
            entities = new ArrayList<Entity>();

        }
    }

    @Override
    public String getRepresentation() {
        return "";
    }

    public void registerForUpdate() {
        Model.getInstance().registerObserver(this);
    }

    public void update() {

        // add entities to fill it up, or try
        for ( int i = entities.size(); i < entitiesOnMap; i++ ) {
            try {
                Entity e = (Entity)prototype.clone();
                int rand = (int)(vectorList.size()*Math.random());
                if ( e.moveToTile( vectorList.get( rand ) ) ) {
                    // move success
                    entities.add( e );
                } else {
                    // move failed
                    e = null;
                    continue; // don't hang up the thread, bro
                }
            } catch ( CloneNotSupportedException ce ) {
                ce.printStackTrace();
            }
        }

        // check for dead entities, and remove
        Iterator<Entity> iter = entities.iterator();
        while ( iter.hasNext() ) {
            Entity e = iter.next();
            if ( e.isDead() ) {
                e.deadEntity(); // TODO: don't know if this will later be removed
                iter.remove();
            }
        }
    }

    public void unRegisterForUpdate() {
        Model.getInstance().removeObserver(this);
    }

    @Override
    public void load(SaverLoader s, boolean notSuperClass) throws IOException {
        this.entitiesOnMap = s.pullInt();
        this.prototype = s.pullSaveable();
        this.size = s.pullInt();

        super.load(s, false);
        Model.getInstance().registerObserver(this);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    @Override
    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        if (notSuperClass) {
            s.start("SpawnPoint");
        }
        
        s.push(this.entitiesOnMap);
        s.push(this.prototype, true);
        s.push(this.size);
        super.save(s, false);
        
        if (notSuperClass) {
            s.close();
        }
    }
}
