package model;

import java.util.ArrayList;

import model.mapstuff.Terrain;

public class DefaultMapGenerator {

    /**
     * Given a map, generates and adds grass terrain to all tiles
     * @param map
     */
    static void generateMap(ArrayList<ArrayList<Tile>> map) {
        for (int i = 0; i != map.size(); ++i) {
            for (int j = 0; j != map.get(i).size(); ++j) {
                map.get(i).get(j).addTerrain(new Terrain(true, null, "Grass"));
            }
        }
    }
}
