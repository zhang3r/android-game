package com.zhang3r.tarocotta.model.tiles.statsFactory.impl;

/**
 * Created by Zhang3r on 11/12/2015.
 */
public class Point implements Comparable {
    int x;
    int y;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;

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

    public String toString() {
        return "x: " + x + ", y: " + y;
    }


    @Override
    public int compareTo(Object another) {
        Point anotherPoint = (Point) another;
        if (anotherPoint.getX() != this.getX() || anotherPoint.getY() != this.getY()) {
            return -1;
        }
        return 0;
    }
}
