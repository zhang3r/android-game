package com.zhang3r.tarocotta.model.tiles.terrain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang3r on 7/22/2015.
 */
public class TerrainFactory {
    private static List<BaseTerrain> terrains;

    public TerrainFactory(){
        terrains = new ArrayList<>();
    }

    public void addTerrain(BaseTerrain terrain){
        terrains.add(terrain);
    }

    public static BaseTerrain getTerrain(int index){
        return terrains.get(index);
    }
    public int getSize(){
        return terrains.size();
    }



}
