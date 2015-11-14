package com.zhang3r.tarocotta.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.mock.MockResources;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ResourceConstant;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.Map;
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
        unit.getStats().setMovePoints(5);
        unit.setAnimation(null);
        army.getUnits().add(unit);
        ResourceConstant.resources = resources;

        Map.getMap().setGrid(new int[50][50]);
    }


    public void testGetMoveTile() {
        unit.setX(10);
        unit.setY(10);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(60, moveTiles.size());
    }

    public void testGetMoveEdge() {
        unit.setX(0);
        unit.setY(0);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(20, moveTiles.size());
    }
    public void testGetMoveFriendlyUnit() {
        unit.setX(10);
        unit.setY(10);
        BaseUnit unit2 = new BaseUnit(IGameConstants.UnitType.ARCHER);
        unit2.getStats().setMovePoints(5);
        unit2.setAnimation(null);
        unit2.setX(11);
        unit2.setY(11);
        army.getUnits().add(unit2);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(59, moveTiles.size());
    }
    public void testGetMoveEnemyUnit() {
        unit.setX(10);
        unit.setY(10);
        BaseUnit unit2 = new BaseUnit(IGameConstants.UnitType.ARCHER);
        unit2.getStats().setMovePoints(5);
        unit2.setAnimation(null);
        unit2.setX(14);
        unit2.setY(10);
        enemyArmy.getUnits().add(unit2);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(58 , moveTiles.size());
    }
}
