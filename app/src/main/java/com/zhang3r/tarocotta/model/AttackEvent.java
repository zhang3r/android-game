package com.zhang3r.tarocotta.model;

import android.util.Log;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.terrain.TerrainFactory;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Impl.RangedAttackImpl;

public class AttackEvent {
    BaseUnit attacker;
    BaseUnit defender;
    public AttackEvent(BaseUnit attacker, BaseUnit defender) {
        this.attacker = attacker;
        this.defender = defender;
    }
    public void calcuateDMG(){
        //get terrain
        int attackerTerIndex =GameMap.getGameMap().getGrid()[attacker.getPosition().getX()][attacker.getPosition().getY()];
        int defenderTerIndex =GameMap.getGameMap().getGrid()[defender.getPosition().getX()][defender.getPosition().getY()];
        BaseTerrain attackerTerr = null;
        BaseTerrain defenderTerr = null;
        //calculate dmg
        int dmg = attackerDMG(attacker, defender, attackerTerr, defenderTerr );
        int recoil = attackerDMG(attacker, defender, attackerTerr, defenderTerr );
        //apply dmg
        defender.getStats().setHitPoints(defender.getStats().getHitPoints() - dmg);
        if(defender.getStats().getHitPoints()<=0){
            defender.setUnitState(IGameConstants.UnitState.DEAD);
        }
        attacker.getStats().setHitPoints(attacker.getStats().getHitPoints() - recoil);
        if(attacker.getStats().getHitPoints()<=0){
            attacker.setUnitState(IGameConstants.UnitState.DEAD);
        }
    }

    private int attackerDMG(BaseUnit attacker, BaseUnit defender, BaseTerrain attackTerrain, BaseTerrain defenderTerrain){
        return 0;
    }
    private int attackerRecoil(BaseUnit attacker, BaseUnit defender, BaseTerrain attackTerrain, BaseTerrain defenderTerrain){
        return 0;
    }


}
