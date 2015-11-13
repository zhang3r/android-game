package com.zhang3r.tarocotta.test;

import android.content.res.Resources;
import android.test.mock.MockResources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang3r on 7/8/2015.
 */
public class MoveUtilTest extends TestCase {
    Move move;
    Resources resources;
    Army army;
    Army enemyArmy;
    BaseUnit unit;

    public MoveUtilTest() {
        super();
    }
    protected void setUp() {
        move = new BasicMoveImpl();
        resources = new MockResources();
        army = new Army();
        army.setUnits(new ArrayList<BaseUnit>());
        enemyArmy = new Army();
        enemyArmy.setUnits(new ArrayList<BaseUnit>());
        unit = new BaseUnit(IGameConstants.UnitType.ARCHER);
        unit.setX(10);
        unit.setY(10);
        unit.getStats().setMovePoints(5);
    }


    public void testGetMoveTile() {

        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(20, moveTiles.size());
    }
}
