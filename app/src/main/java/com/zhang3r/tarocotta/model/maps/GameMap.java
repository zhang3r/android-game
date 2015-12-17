package com.zhang3r.tarocotta.model.maps;


/**
 * Created by Zhang3r on 7/21/2015.
 */
public class GameMap {
    private static GameMap gameMap;
    private int[][] grid;

    private GameMap(){}

    private GameMap(int x, int y){
        grid = new int[x][y];
    }

    public static GameMap getGameMap(){
        if(gameMap ==null){
            gameMap =new GameMap(5,5);
        }
        return gameMap;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}