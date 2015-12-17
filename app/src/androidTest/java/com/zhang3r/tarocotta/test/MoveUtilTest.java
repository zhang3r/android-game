package com.zhang3r.tarocotta.test;

import android.content.res.Resources;
import android.test.mock.MockResources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ResourceConstant;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        GameMap.getGameMap().setGrid(new int[50][50]);
    }


    public void testGetMoveTile() {
        unit.getPosition().setX(10);
        unit.getPosition().setY(10);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(60, moveTiles.size());
    }

    public void testGetMoveEdge() {
        unit.getPosition().setX(0);
        unit.getPosition().setY(0);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(20, moveTiles.size());
    }
    public void testGetMoveFriendlyUnit() {
        unit.getPosition().setX(10);
        unit.getPosition().setY(10);
        BaseUnit unit2 = new BaseUnit(IGameConstants.UnitType.ARCHER);
        unit2.getStats().setMovePoints(5);
        unit2.setAnimation(null);
        unit2.getPosition().setX(11);
        unit2.getPosition().setY(11);
        army.getUnits().add(unit2);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(59, moveTiles.size());
    }
    public void testGetMoveEnemyUnit() {
        unit.getPosition().setX(10);
        unit.getPosition().setY(10);
        BaseUnit unit2 = new BaseUnit(IGameConstants.UnitType.ARCHER);
        unit2.getStats().setMovePoints(5);
        unit2.setAnimation(null);
        unit2.getPosition().setX(14);
        unit2.getPosition().setY(10);
        enemyArmy.getUnits().add(unit2);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        assertNotNull(moveTiles);
        assertEquals(58 , moveTiles.size());
    }

    public void testDijkstra(){
        //gets the edge
        unit.getPosition().setX(0);
        unit.getPosition().setY(0);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        // get dijkstras
        try {
            Method dijstra = BasicMoveImpl.class.getDeclaredMethod("dijkstras", List.class, Point.class);

            dijstra.setAccessible(true);
            Object map = dijstra.invoke(move, moveTiles, unit.getPosition());
            assertNotNull(map);
            assertEquals(20, ((Map) map).keySet().size());
            assertNotNull(((Map) map).get(((Map) map).keySet().iterator().next()));
        }catch(Exception e){

            throw new Error("path finding failure "+e.getCause().toString());
        }

    }
    public void testPathFinder(){

        unit.getPosition().setX(0);
        unit.getPosition().setY(0);
        Point dest = new Point (2,2);
        List<AnimatedSprite> moveTiles = move.getMoveTiles(unit, army, enemyArmy, resources);
        try {
            //dijkstras
            Method dijstra = BasicMoveImpl.class.getDeclaredMethod("dijkstras", List.class, Point.class);
            dijstra.setAccessible(true);
            Object map = dijstra.invoke(move, moveTiles, unit.getPosition());
            //path finding
            Method pathfinding = BasicMoveImpl.class.getDeclaredMethod("findPath", Map.class, Point.class, Point.class);
            pathfinding.setAccessible(true);
            Object path = pathfinding.invoke(move, (Map)map, dest, unit.getPosition());
            assertNotNull(path);
            assertEquals(4, ((List) path).size());
        }catch(Exception e){

            throw new Error("path finding failure "+e.getCause().toString());
        }
    }


}
