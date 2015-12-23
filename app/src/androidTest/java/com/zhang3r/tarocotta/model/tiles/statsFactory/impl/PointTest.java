package com.zhang3r.tarocotta.model.tiles.statsFactory.impl;

import junit.framework.TestCase;

/**
 * Created by Zhang3r on 12/17/2015.
 */
public class PointTest extends TestCase {

    public PointTest() {
        super();
    }

    public void testPointCompare(){
        Point a = new Point(0,0);
        Point b = new Point(1,2);
        Point c = new Point(0,0);

        assertTrue(c!=a);
        assertTrue(c.compareTo(a)==0);
        assertTrue(c.compareTo(b)!=0);
    }

}