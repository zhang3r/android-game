package com.zhang3r.tarocotta.model.AI;

import android.content.res.Resources;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.PlainTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.TerrainFactory;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

import junit.framework.TestCase;

/**
 * Created by Zhang3r on 12/23/2015.
 */
public class ShittyAITest extends TestCase {

    AI shittyAI;
    protected void setUp(){
        shittyAI = new ShittyAI();
        TerrainFactory terrainFactory;
        terrainFactory = new TerrainFactory();
        terrainFactory.addTerrain(new BaseTerrain());
        terrainFactory.addTerrain(new BaseTerrain());
    }

    public void testAiMove() throws Exception {
        //setup
        //terrain
        GameMap.getGameMap().getGrid()[0][0]=1;
        GameMap.getGameMap().getGrid()[0][1]=1;
        GameMap.getGameMap().getGrid()[1][0]=1;
        GameMap.getGameMap().getGrid()[1][1]=1;

        BaseUnit friendly = new BaseUnit(IGameConstants.UnitType.FOOT);
        friendly.getStats().setDefense(0);
        friendly.getStats().setAttack(1);
        friendly.getStats().setHitPoints(5);
        friendly.setPosition(new Point(0, 0));
        friendly.setUnitState(IGameConstants.UnitState.NORMAL);
        BaseUnit enemy = new BaseUnit(IGameConstants.UnitType.FOOT);
        enemy.getStats().setDefense(0);
        enemy.getStats().setAttack(10);
        enemy.getStats().setHitPoints(5);
        enemy.setPosition(new Point(1, 0));
        enemy.setUnitState(IGameConstants.UnitState.NORMAL);

        Army fArmy = new Army();
        fArmy.add(friendly);
        Army eArmy = new Army();
        eArmy.add(enemy);

        shittyAI.AiMove(eArmy, fArmy);
        assertNotNull(enemy);
        assertTrue(enemy.getUnitState() == IGameConstants.UnitState.WAIT);
        assertNotNull(friendly);
        assertTrue(friendly.getUnitState()== IGameConstants.UnitState.DEAD);



    }
}