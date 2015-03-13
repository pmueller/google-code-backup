package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import util.MapVisitor;

public class MapView extends ViewPort {

    //height width of map in tiles
    private int tilesWide;
    private int tilesHigh;

    //the tile representations
    TileView[][] tiles;

    public MapView(int componentWidth, int componentHeight) {
        //set width height based on tilesize
        tilesWide = componentWidth / View.TILE_SIZE;
        tilesHigh = componentHeight / View.TILE_SIZE;

        //set component size and color
        setSize(componentWidth, componentHeight);
        this.setBackground(Color.LIGHT_GRAY);

        tiles = new TileView[tilesWide][tilesHigh];

        //make the map a grid!
        this.setLayout(new GridLayout(tilesHigh, tilesWide));

        //for each tile, make a new tileview
        //which represents the tile graphically
        for (int y = 0; y < tilesHigh; y++) {
            for (int x = 0; x < tilesWide; x++) {
                tiles[x][y] = new TileView(new ArrayList());
                this.add(tiles[x][y]);
            }
        }
    }

    /**
     * update the map with a visitor that goes to the model
     */
    public void update() {
        //make the visitor and send it to the model
        MapVisitor mv = new MapVisitor(tilesWide, tilesHigh);
        View.getInstance().sendMapVisitorToModel(mv);

        //get the map which is a 2d list of string lists
        List<List<List<String>>> map = mv.getMap();

        //for each tile, set the lsit of strings to render on the tileviews
        for (int x = 0; x < tilesWide; x++) {
            for (int y = 0;y < tilesHigh; y++) {
                tiles[x][y].setToRender( map.get(x).get(y) );
            }
        }

        //repaint errthanggggg
        repaint();

    }

}
