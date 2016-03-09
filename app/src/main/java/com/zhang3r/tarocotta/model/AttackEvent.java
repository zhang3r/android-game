package com.zhang3r.tarocotta.model;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.TerrainFactory;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

public class AttackEvent {
    BaseUnit attacker;
    BaseUnit defender;

    public AttackEvent(BaseUnit attacker, BaseUnit defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    public void calcuateDMG() {
        //get terrain
        int attackerTerIndex = GameMap.getGameMap().getGrid()[attacker.getPosition().getX()][attacker.getPosition().getY()];
        int defenderTerIndex = GameMap.getGameMap().getGrid()[defender.getPosition().getX()][defender.getPosition().getY()];
        BaseTerrain attackerTerr = TerrainFactory.getTerrain(attackerTerIndex);
        BaseTerrain defenderTerr = TerrainFactory.getTerrain(defenderTerIndex);
        //calculate dmg
        int dmg = attackerDMG(attacker, defender, attackerTerr, defenderTerr);
        int recoil = attackerRecoil(attacker, defender, attackerTerr, defenderTerr);
        dmg = dmg >0 ? dmg: 1;
        recoil = recoil>0 ? recoil : 1;
        //apply dmg
        defender.getStats().setHitPoints(defender.getStats().getHitPoints() - dmg);
        if (defender.getStats().getHitPoints() <= 0) {
            defender.setUnitState(IGameConstants.UnitState.DEAD);
        }
        attacker.getStats().setHitPoints(attacker.getStats().getHitPoints() - recoil);
        if (attacker.getStats().getHitPoints() <= 0) {
            attacker.setUnitState(IGameConstants.UnitState.DEAD);
        }
    }

    private int attackerDMG(BaseUnit attacker, BaseUnit defender, BaseTerrain attackTerrain, BaseTerrain defenderTerrain) {
        int dmg = (attacker.getStats().getAttack() + attackTerrain.getAttack()) - (defender.getStats().getDefense() + defenderTerrain.getDefense());
        return dmg;
    }

    private int attackerRecoil(BaseUnit attacker, BaseUnit defender, BaseTerrain attackTerrain, BaseTerrain defenderTerrain) {
        int dmg = (defender.getStats().getAttack() + defenderTerrain.getAttack()) / 2 - (attacker.getStats().getDefense() + attackTerrain.getDefense());
        return dmg;
    }


}
