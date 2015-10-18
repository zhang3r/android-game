package com.zhang3r.tarocotta.model.tiles.units.decorator;

import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

import junit.framework.TestCase;

/**
 * Created by Zhang3r on 10/14/2015.
 */
public class EnemyUnitTest extends TestCase {
    BaseUnit unit;
    public void setUp(){
        unit = new BaseUnit() {
            @Override
            public String getDescription() {
                return "";
            }
        };
    }
    public void testEnemyUnit(){
        EnemyUnit eunit= new EnemyUnit(unit);

        assertEquals(" This unit will attack you on sight.", eunit.getDescription());
        assertNotNull(eunit.getUnit());
    }
}