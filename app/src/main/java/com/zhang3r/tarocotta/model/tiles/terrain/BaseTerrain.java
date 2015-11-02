package com.zhang3r.tarocotta.model.tiles.terrain;

import com.zhang3r.tarocotta.model.sprite.Tile;

public class BaseTerrain extends Tile {
    private int x;
    private int y;
    private int terrainId;
    private String name;
    private int defense;
    private int movement;
    private int evasion;

    public BaseTerrain(){}

    public int getTerrainId() {
        return terrainId;
    }

    public void setTerrainId(int terrainId) {
        this.terrainId = terrainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString(){
        return "base terrain, provides no special abilities";
    }


    @Override
    public String getDescription() {
        return "base terrain, provides no special abilities";
    }
}
