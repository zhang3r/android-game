package com.zhang3r.tarocotta.model.tiles.statsFactory.impl;

import com.zhang3r.tarocotta.constants.IGameConstants;

import junit.framework.TestCase;

/**
 * Created by Zhang3r on 11/1/2015.
 */
public class StatsFactoryTest extends TestCase {

    public void testCreateStat() throws Exception {
        Stats stat = StatsFactoryImpl.createStat(IGameConstants.UnitType.ARCHER);
        assertEquals(stat.getAnimations(),2);
    }
}