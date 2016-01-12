package com.zhang3r.tarocotta.model.tiles.units.Impl;

import android.content.res.Resources;
import android.test.mock.MockResources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ResourceConstant;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang3r on 11/16/2015.
 */
public class MeleeAttackImplTest extends TestCase {
    Attack attack;
    Resources resources;
    Army army;
    Army enemyArmy;
    BaseUnit unit;


    public MeleeAttackImplTest() {
        super();
    }
    protected void setUp() {
        attack = new MeleeAttackImpl();
        resources = new MockResources();
        army = new Army();
        army.setUnits(new ArrayList<BaseUnit>());
        enemyArmy = new Army();
        enemyArmy.setUnits(new ArrayList<BaseUnit>());
        unit = new BaseUnit(IGameConstants.UnitType.ARCHER);
        unit.getStats().setMaxAttackRange(1);
        unit.setAnimation(null);
        army.getUnits().add(unit);
        ResourceConstant.resources = resources;

        GameMap.getGameMap().setGrid(new int[50][50]);
    }


    public void testGetUnitAttackTiles() throws Exception {
        unit.getPosition().setX(10);
        unit.getPosition().setY(10);
        List<AnimatedSprite> attackTiles =  attack.getUnitAttackTiles(unit, army, enemyArmy, resources);
        assertNotNull(attackTiles);
        assertEquals(4, attackTiles.size());
    }
}