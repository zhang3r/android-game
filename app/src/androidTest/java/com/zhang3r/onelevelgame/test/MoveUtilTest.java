package com.zhang3r.onelevelgame.test;

import android.content.res.Resources;
import android.test.mock.MockResources;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.onelevelgame.model.tiles.units.Interface.Move;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Zhang3r on 7/8/2015.
 */
public class MoveUtilTest extends TestCase {
    Move move;
    Resources resources;

    public MoveUtilTest() {
        super();
    }
    protected void setUp() {
        move = new BasicMoveImpl();
        resources = new MockResources();
    }

    public void testGetMoveTile() {
        List<AnimatedSprite> moveTiles = move.getMoveTiles(10, 10, 15, 15, resources, 1, 3);
        assertNotNull(moveTiles);
        assertEquals(20, moveTiles.size());
    }
}
