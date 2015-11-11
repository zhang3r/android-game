package com.zhang3r.tarocotta.model.tiles.units.Impl;

/**
 * Created by Zhang3r on 11/11/2015.
 */
public class MoveTarget {
    int targetX;
    int targetY;

    public MoveTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    public boolean hasReachedTarget(int x, int y) {
        return x != targetX || y != targetY;
    }
}
