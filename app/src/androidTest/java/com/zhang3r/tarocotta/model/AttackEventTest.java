package com.zhang3r.tarocotta.model;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * Created by Zhang3r on 12/18/2015.
 */
public class AttackEventTest extends TestCase {
    BaseTerrain attackerTera;
    BaseTerrain defenderTera;
    BaseUnit attacker;
    BaseUnit defender;
    public AttackEventTest() {
        super();
    }
    protected void setUp() {
        attackerTera = new BaseTerrain();
        defenderTera = new BaseTerrain();
        attacker = new BaseUnit(IGameConstants.UnitType.FOOT);
        defender = new BaseUnit(IGameConstants.UnitType.FOOT);
        //value setting
        attackerTera.setAttack(5);
        attackerTera.setDefense(1);
        attacker.getStats().setAttack(10);
        attacker.getStats().setDefense(2);
        defenderTera.setDefense(4);
        defenderTera.setAttack(3);
        defender.getStats().setAttack(7);
        defender.getStats().setDefense(7);
    }

    public void testAttackerDMG() throws Exception {
        AttackEvent ae = new AttackEvent(attacker, defender);
        Method attackDMG = AttackEvent.class.getDeclaredMethod("attackerDMG", BaseUnit.class, BaseUnit.class, BaseTerrain.class, BaseTerrain.class);
        attackDMG.setAccessible(true);
        Object dmg = attackDMG.invoke(ae, attacker, defender, attackerTera, defenderTera);
        assertNotNull(dmg);
        assertEquals(dmg, 4);


    }
    public void testAttackerRecoil() throws Exception {
        AttackEvent ae = new AttackEvent(attacker, defender);
        Method recoil = AttackEvent.class.getDeclaredMethod("attackerRecoil", BaseUnit.class, BaseUnit.class, BaseTerrain.class, BaseTerrain.class);
        recoil.setAccessible(true);
        Object dmg = recoil.invoke(ae, attacker, defender, attackerTera, defenderTera);
        assertNotNull(dmg);
        assertEquals(dmg, 2);
    }
}