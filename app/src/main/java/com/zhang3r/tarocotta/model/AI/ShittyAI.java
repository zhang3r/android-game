package com.zhang3r.tarocotta.model.AI;

/**
 * Created by Zhang3r on 5/6/2015.
 */

import android.content.res.Resources;
import android.util.Log;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.constants.ResourceConstant;
import com.zhang3r.tarocotta.model.AttackEvent;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.GameMap;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

import java.util.List;

public class ShittyAI implements AI {
    public ShittyAI() {

    }

    public void AiMove(Army army, Army enemyArmy) {
        for (BaseUnit unit : army.getUnits()) {
            //setting unit selected

            //find closest unit using manhattan distance
            BaseUnit shortestUnit = pathFinding(unit, enemyArmy);
            if (shortestUnit != null) {
                int shortestDistance = calcDistance(unit.getPosition().getX(), unit.getPosition().getY(), shortestUnit);
                //if closet unit is outside of attack range, move
                if (shortestDistance > unit.getStats().getMaxAttackRange()) {
                    int dX = unit.getPosition().getX()* IAppConstants.SPRITE_WIDTH;
                    int dY = unit.getPosition().getY()*IAppConstants.SPRITE_WIDTH;
                    Log.d(ILogConstants.DEBUG_TAG, "AI " + unit.getId() + " original x:" + dX + " y: " + dY);
                    // get valid moves
                    List<AnimatedSprite> moveTiles = unit.getMoveUtil().getMoveTiles(unit,army,enemyArmy, ResourceConstant.resources);
                    for (AnimatedSprite tile : moveTiles) {
                        int distance = calcDistance((tile.getXPos() / IAppConstants.SPRITE_WIDTH), (tile.getYPos() / IAppConstants.SPRITE_WIDTH), shortestUnit);
                        if (distance < shortestDistance) {
                            shortestDistance = distance;
                            dX = tile.getXPos() ;
                            dY = tile.getYPos() ;

                        }
                    }

                        Log.d(ILogConstants.DEBUG_TAG, "AI "+unit.getId()+" Move x:" + dX + " y: " + dY);
                        unit.getAnimation().setXPos(unit.getPosition().getX() * IAppConstants.SPRITE_WIDTH + dX);
                        unit.getAnimation().setYPos(unit.getPosition().getY() * IAppConstants.SPRITE_HEIGHT + dY);
                        unit.getPosition().setX((dX / IAppConstants.SPRITE_WIDTH));
                        unit.getPosition().setY((dY / IAppConstants.SPRITE_HEIGHT));
                        unit.setUnitState(IGameConstants.UnitState.MOVED);

                    //update shortest distance
                    shortestDistance =calcDistance(unit.getPosition().getX(), unit.getPosition().getY(), shortestUnit);
                }
                //check if enemy is in attack range
                // if enemy is in attack range attack
                unit.setUnitState(IGameConstants.UnitState.WAIT);
                if (shortestDistance <= unit.getStats().getMaxAttackRange()) {

                    AttackEvent ae = new AttackEvent(unit, shortestUnit);
                    ae.calcuateDMG();
                }

            }
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
            int tempDistance = calcDistance(unit.getPosition().getX(), unit.getPosition().getY(), enemyUnit);
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
        int x2 = unit2.getPosition().getX();
        int y2 = unit2.getPosition().getY();
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }
}
