package com.zhang3r.onelevelgame.model.AI;

/**
 * Created by Zhang3r on 5/6/2015.
 */

import android.content.res.Resources;
import android.util.Log;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;
import com.zhang3r.onelevelgame.constants.IGameConstants;
import com.zhang3r.onelevelgame.constants.ILogConstants;
import com.zhang3r.onelevelgame.model.AttackEvent;
import com.zhang3r.onelevelgame.model.army.Army;
import com.zhang3r.onelevelgame.model.maps.Map;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

import java.util.List;

public class ShittyAI implements AI {
    public ShittyAI() {

    }

    public void AiMove(Army army, Army enemyArmy) {
        for (BaseUnit unit : army.getUnits()) {
            //setting unit selected
            unit.setUnitSelected(true);
            //find closest unit using manhattan distance
            BaseUnit shortestUnit = pathFinding(unit, enemyArmy);
            int shortestDistance = calcDistance(unit.getX(), unit.getY(), shortestUnit);
            //if closet unit is outside of attack range, move
            if (shortestDistance > unit.getMaxAttackRange()) {
                int dX = 0;
                int dY = 0;
                // get valid moves
                List<AnimatedSprite> moveTiles = unit.getUnitMoveTiles(Map.getMap().getGrid()[0].length, Map.getMap().getGrid().length, army, enemyArmy, Resources.getSystem());
                for (AnimatedSprite tile : moveTiles) {
                    int distance = calcDistance(tile.getXPos() / IAppConstants.SPRITE_WIDTH, tile.getYPos() / IAppConstants.SPRITE_HEIGHT, shortestUnit);
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        dX = tile.getXPos();
                        dY = tile.getYPos();
                    }
                }
                Log.d(ILogConstants.DEBUG_TAG, "AI Move x:" + dX + " y: " + dY);
                if (unit.unitMoveUpdate(moveTiles, army, enemyArmy, dX, dY)) {
                    unit.setState(IGameConstants.UnitState.MOVED);
                }
                //update shortest distance
                shortestDistance -= Math.abs(dX) + Math.abs(dY);
            }
            //check if enemy is in attack range
            // if enemy is in attack range attack
            if (shortestDistance <= unit.getMaxAttackRange()) {
                AttackEvent.attack(unit, shortestUnit, army, enemyArmy);
            }
            unit.setState(IGameConstants.UnitState.WAIT);
        }
        //all units has moved
        //end turn;

    }


    /**
     * returns the closest enemyUnit to current unit
     *
     * @param unit
     * @param enemyArmy
     * @return
     */
    private BaseUnit pathFinding(BaseUnit unit, Army enemyArmy) {
        //TODO upgrade to A*
        BaseUnit closest = null;
        int distance = Integer.MAX_VALUE;
        for (BaseUnit enemyUnit : enemyArmy.getUnits()) {
            if (closest == null) {
                closest = enemyUnit;
            }
            int tempDistance = calcDistance(unit.getX(), unit.getY(), enemyUnit);
            if (tempDistance < distance) {
                distance = tempDistance;
                closest = enemyUnit;
            }

        }

        return closest;
    }

    /**
     * calculates the difference between the two units
     * using Manhattan distance because fk diagonals
     *
     * @param unit2
     * @return
     */
    private int calcDistance(int x, int y, BaseUnit unit2) {
        int x1 = x;
        int y1 = y;
        int x2 = unit2.getX();
        int y2 = unit2.getY();
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }
}
