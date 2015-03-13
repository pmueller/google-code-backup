package util;

import java.util.ArrayList;
import java.util.List;

public class MapVisitor {

    //state of the visitor
    private util.Vector avatarPos;
    private int tilesWide;
    private int tilesHigh;
    private List<List<List<String>>> map;
    private int cx;
    private int cy;
    
    public MapVisitor(int tilesWide, int tilesHigh) {
        //set the size we're looking for
        this.tilesWide = tilesWide;
        this.tilesHigh = tilesHigh;
        //make a blank map
        map = new ArrayList<List<List<String>>>();
        for(int x = 0; x < tilesWide; ++x) {
            map.add(new ArrayList<List<String>>());
            for(int y = 0; y < tilesHigh; ++y) {
                map.get(x).add(new ArrayList<String>());
            }
        }
    }

    /**
     * called by model before being passed to map,
     * so we know where to focus the map
     * @param v
     */
    public void setAvatarPosition(util.Vector v) {
        this.avatarPos = v;
    }

    /**
     * visit the tile by getting it's list of representations to render
     * @param t
     */
    public void visit(model.Tile t) {
        map.get(cx).set(cy, t.getListOfNames());
    }

    /**
     * visit the map, tile by tile, getting the list of things to render
     * @param mg
     */
    public void visit(model.MapGrid mg) {

        int mapWidth = mg.getTilesWide();
        int mapHeight = mg.getTilesHigh();
        int leftSide = tilesWide / -2;
        int rightSide = -leftSide;
        int top = tilesHigh / -2;
        int bottom = -top;

        cx = -1;
        cy = -1;
        //go tile by tile that is centered around the avatar
        //of outside of model's map bounds, then make it a black tile
        //if it exists, get the list of things to render
        for(int dx = leftSide; dx <= rightSide; ++dx) {
            ++cx;
            for(int dy = top; dy <= bottom; ++dy) {
                ++cy;
                if(avatarPos.x + dx < 0 || avatarPos.x + dx >= mapWidth || avatarPos.y + dy < 0 || avatarPos.y + dy >= mapHeight) {
                    map.get(cx).get(cy).add("Null");

                } else
                    mg.getTileAtPosition(avatarPos.add(new Vector(dx, dy))).accept(this);
            }
            cy = -1;
        }
    }

    /**
     * 
     * @return the map
     */
    public List<List<List<String>>> getMap() {
        return map;
    }
    
}
