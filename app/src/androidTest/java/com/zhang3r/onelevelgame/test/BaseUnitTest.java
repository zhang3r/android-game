package com.zhang3r.onelevelgame.test;


import com.zhang3r.onelevelgame.constants.IGameConstants;
import com.zhang3r.onelevelgame.constants.IGameConstants.UnitType;
import com.zhang3r.onelevelgame.model.tiles.unitFactory.UnitFactory;
import com.zhang3r.onelevelgame.model.tiles.unitFactory.impl.UnitFactoryImpl;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

import junit.framework.TestCase;

/**
 * Created by Zhang3r on 7/8/2015.
 */
public class BaseUnitTest extends TestCase {
    UnitFactory factory;

    protected void setUp() {
        factory = UnitFactoryImpl.getInstance();
    }

    public void unitCreationTest() {
        BaseUnit unit = factory.createUnit(UnitType.ARCHER);
        //TESTS
        /*
         * 1. test for unit not null
         * 2. test for unitType
         * 3. test for attack util
         * 4. test for move util
         */
        assertNotNull(unit);
        assertEquals(unit.getUnitType(), UnitType.ARCHER);
        assertEquals(unit.getState(), IGameConstants.UnitState.NORMAL);
        assertNotNull(unit.getAttackUtil());
        assertNotNull(unit.getMoveUtil());

    }



}
