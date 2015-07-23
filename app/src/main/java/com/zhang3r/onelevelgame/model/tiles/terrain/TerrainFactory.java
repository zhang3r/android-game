package com.zhang3r.onelevelgame.model.tiles.terrain;

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

    public BaseTerrain getTerrain(int index){
        return terrains.get(index);
    }



}
