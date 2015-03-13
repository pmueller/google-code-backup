package model;

import java.io.*;
import java.util.*;
import util.Vector;
import util.*;

public class MapGrid implements Saveable {

    private ArrayList<ArrayList<Tile>> map;
    //added data member for the starting point
    private Vector startingPoint;
    public MapGrid(){
        
    }

    /**
     * Creates a MapGrid of the specified x/y size, and generates a default map
     * @param x the width of the map
     * @param y the height of the map
     */
    public MapGrid(int x, int y) {
        newMap(x, y);
        DefaultMapGenerator.generateMap(map);
        this.startingPoint = new Vector(0,0);
    }

    /**
     * Overwrites over the old MapGrid's map double-nested array
     * @param x the width of the map
     * @param y the height of the map
     * @return
     */
    public ArrayList<ArrayList<Tile>> newMap(int x, int y) {
        map = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i != x; ++i) {
            ArrayList<Tile> temp = new ArrayList<Tile>();
            for (int j = 0; j != y; j++) {
                temp.add(new Tile());
            }
            map.add(temp);
        }
        return map;
    }

    /**
     *
     * @return the width of the map
     */
    public int getTilesWide() {
        return map.size();
    }

    /**
     *
     * @return the height of the map
     */
    public int getTilesHigh() {
        return map.get(0).size();
    }

    /**
     * Sets the starting point
     * @param x
     * @param y
     */
    public void setStartingPoint(int x, int y){
        this.startingPoint = new Vector(x,y);
    }

    /**
     *
     * @return the startingPoint
     */
    public Vector getStartingPoint(){
        return this.startingPoint;
    }

    /**
     *
     * @param v the position
     * @return a tile at the specified position, or null if out of bounds
     */
    public Tile getTileAtPosition(Vector v) {
        try {
            Tile t = map.get(v.x).get(v.y);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns null, maybe delete method?
     */
    public ArrayList<Tile> getBestRect(Vector topLeft, Vector bottomRight) {

        return null;
    }

    /**
     * MapVisitor accept method
     * @param v
     */
    public void accept(MapVisitor v) {
        v.visit(this);
    }

    public void load(SaverLoader sl, boolean notSuperClass) throws IOException {
        

        int i_m = sl.pullInt();
        int j_m = sl.pullInt();
        map = new ArrayList<ArrayList<Tile>>(i_m);
        for (int i = 0; i < i_m; i++) {
            map.add(i, new ArrayList<Tile>(j_m));
            for (int j = 0; j < j_m; j++) {
                Tile t = sl.<Tile>pullSaveable();
                ArrayList<Tile> ls = map.get(i);
                map.get(i).add(j, t);
            }
        }
        
        this.startingPoint = sl.pullSaveable();
        if (notSuperClass) {
            sl.assertEnd();
        }
    }

    public void save(SaverLoader s, boolean notSuperClass) throws IOException {
        /*
         *MapGrid is unique, it doesn't implement s.start();
         * This is due to fact map grid isn't pulled form the file
         * a new mapgrid is created, then loaded in the model.
         */

        
        s.push(map.size());
        s.push(map.get(0).size());
        for (int i = 0; i != map.size(); ++i) {
            for (int j = 0; j != map.get(i).size(); j++) {
                s.push(map.get(i).get(j), true);
            }
        }
        s.push(this.startingPoint, true);

        if (notSuperClass) {
            s.close();
        }
    }

    public void edit(int i, util.Vector v) {
        if (i == 0) {
            map.get(v.x).get(v.y).addTerrain(new model.mapstuff.Terrain(true, null, "Grass"));
        }
        if (i == 1) {
            map.get(v.x).get(v.y).addTerrain(new model.mapstuff.Terrain(false, null, "Water"));
        }
        if (i == 2) {
            map.get(v.x).get(v.y).addTerrain(new model.mapstuff.Terrain(true, new model.effect.SlowingEffect(-20), "Mud"));
        }
        if (i == 3) {
            map.get(v.x).get(v.y).addTerrain(new model.mapstuff.Terrain(false, null, "Mountain"));
        }
    }
}
