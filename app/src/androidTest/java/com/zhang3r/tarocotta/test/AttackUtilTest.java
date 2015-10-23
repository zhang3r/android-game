package com.zhang3r.tarocotta.test;

import android.content.res.Resources;
import android.test.mock.MockResources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.model.tiles.units.Impl.MeleeAttackImpl;
import com.zhang3r.tarocotta.model.tiles.units.Impl.RangedAttackImpl;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Zhang3r on 7/8/2015.
 */
public class AttackUtilTest extends TestCase {
    Attack meleeAttack;
    Attack rangedAttack;
    Resources resource;
    public AttackUtilTest() {
        super();
    }
    protected void setUp() {
        meleeAttack = new MeleeAttackImpl();
        rangedAttack = new RangedAttackImpl();
        resource = new MockResources();
    }


    /**
     * This should test bounds.
     */

    public void testGetMeleeAttackTiles() {
        /*
         * 1. test for not null
         * 2. test for attackutil
         * 3. test if attack returns a Attack event
         * 4. test the contents of attack event
         */
//        List<AnimatedSprite> attackTiles = meleeAttack.getUnitAttackTiles(1, 10, 10, resource, 15, 15, 1, 0);
//        assertNotNull(attackTiles);
//        assertEquals(6, attackTiles.size());


    }


    public void testGetRangedAttackTiles() {
        /*
         * 1. test for not null
         * 2. test for attackutil
         * 3. test if attack returns a Attack event
         * 4. test the contents of attack event if a unit has been killed.
         */

//        List<AnimatedSprite> attackTiles = rangedAttack.getUnitAttackTiles(1, 10, 10, resource, 15, 15, 3, 1);
//        assertNotNull(attackTiles);
//        assertEquals(12,attackTiles.size());

    }
}
