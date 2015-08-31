package com.zhang3r.tarocotta.model.maps;


/**
 * Created by Zhang3r on 7/21/2015.
 */
public class Map {
    private static Map map;
    private int[][] grid;

    private Map(){}

    private Map(int x, int y){
        grid = new int[x][y];
    }

    public static Map getMap(){
        if(map==null){
            map=new Map(5,5);
        }
        return map;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}