package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.entity.Avatar;
import util.*;

import model.mapstuff.*;

public class Tile implements Saveable {
    private Decal decal;
    private Terrain terrain;
    private ArrayList<Interactive> interactives;

    /**
     * Returns List of Interactive objects on Tile, including Terrain
     */
    public Tile() {
        interactives = new ArrayList<Interactive>();
    }

    /**
     *
     * @return a list of all interactive objects on this tile
     */
    public List<Interactive> getInteractives() {
        ArrayList<Interactive> ret = new ArrayList<Interactive>();
        for ( Interactive i : interactives ) {
            ret.add( i );
        }
        ret.add( terrain );
        return ret;
    }

    /**
     *
     * @return
     */
    public List<String> getListOfNames() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(terrain.getOrientation() + "/" + terrain.getRepresentation());

        for(Interactive i : interactives) {
            if(i.getRepresentation() != null)
             ret.add(i.getOrientation() + "/" + i.getRepresentation());
        }

        if (decal != null)
            ret.add( decal.getOrientation() + "/" + decal.getRepresentation() );

        return ret;
    }

    /**
     * Add Interactive object to Tile. Should not add Terrain objects
     * here, use addTerrain() instead.
     */
    public void add( Interactive interactive ) {
        if(interactive instanceof Avatar){
            int n = 5;
        }
        interactives.add( interactive );
    }

    /**
     * Removes the Interactive object specified
     * @param interactive the Interactive object to remove
     */
    public void remove( Interactive interactive ) {
        interactives.remove( interactive );
    }

    /**
     * Adds the specified Terrain
     * @param terrain the Terrain to add
     */
    public void addTerrain( Terrain terrain ) {
        this.terrain = terrain;
    }

    /**
     *
     * @return this Tile's Terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Sets the terrain to null
     */
    public void removeTerrain() {
        terrain = null;
    }

    /**
     * Sets the specified Decal
     * @param decal the Decal to set
     */
    public void addDecal( Decal decal ) {
        this.decal = decal;
    }

    /**
     *
     * @return this Tile's Decal
     */
    public Decal getDecal() {
        return decal;
    }

    /**
     * Sets the Decal to null
     */
    public void removeDecal() {
        decal = null;
    }

    /**
     * Accept method for MapVisitor
     * @param v the MapVisitor
     */
    public void accept(MapVisitor v) {
        v.visit(this);
    }

    /**
     *
     * @param s
     * @param notSuperClass
     * @throws IOException
     */
    public void load(SaverLoader s, boolean notSuperClass) throws IOException{
        this.decal = s.pullSaveable();
        this.terrain = s.pullSaveable();
        Interactive[] iter = s.pullSaveableArray(new Interactive[0]);
        for(int i = 0;i<iter.length;i++)
            iter[i].registerToTile(this);
        if (notSuperClass) {
            s.assertEnd();
        }
    }

    /**
     *
     * @param s
     * @param b
     * @throws IOException
     */
    public void save(SaverLoader s, boolean b) throws IOException
    {
        if (b) {
            s.start("Tile");
        }

        s.push(this.decal, true);
        s.push(this.terrain, true);
        s.push((ArrayList<Interactive>) interactives);
        if (b) {
            s.close();
        }
    }
}
