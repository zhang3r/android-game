package com.zhang3r.onelevelgame.model.AI;

/**
 * Created by Zhang3r on 5/6/2015.
 */

import com.zhang3r.onelevelgame.constants.IGameConstants;
import com.zhang3r.onelevelgame.model.AttackEvent;
import com.zhang3r.onelevelgame.model.army.Army;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

public class ShittyAI implements AI {
    public ShittyAI(){

    }
    public void AiMove(Army army, Army enemyArmy){
        for(BaseUnit unit: army.getUnits()){
            //find closest unit using A* and manhattan distance as heuristic
            BaseUnit shortestUnit = pathFinding(unit, enemyArmy);
            int shortestDistance =calcDistance(unit, shortestUnit);
            //if closet unit is outside of attack range, move
            if(shortestDistance>unit.getMaxAttackRange()){
                int dX = (((shortestUnit.getX()-unit.getX())>>31)&1)*Math.min(unit.getMovePoints()/2 ,(shortestDistance - unit.getMaxAttackRange()));
                int dY = (((shortestUnit.getY()-unit.getY())>>31)&1)*unit.getMovePoints()-dX;
                //unit.unitMoveUpdate()
                //unit.unitMoveUpdate(dX, dY);
                //update shortest distance
                shortestDistance -= Math.abs(dX)+Math.abs(dY);
            }
            //check if enemy is in attack range
            // if enemy is in attack range attack
            if(shortestDistance<=unit.getMaxAttackRange()){
                AttackEvent.attack(unit,shortestUnit,army, enemyArmy);
            }
            unit.setState(IGameConstants.UnitState.WAIT);
        }
        //all units has moved
        //end turn;

    }

    /**
     * returns the closest enemyUnit to current unit
     * @param unit
     * @param enemyArmy
     * @return
     */
    private BaseUnit pathFinding(BaseUnit unit, Army enemyArmy){
        //TODO upgrade to A*
        BaseUnit closest = null;
        int distance = Integer.MAX_VALUE;
        for(BaseUnit enemyUnit:enemyArmy.getUnits()){
            if(closest==null){
                closest = enemyUnit;
            }
            int tempDistance = calcDistance(unit, enemyUnit);
            if(tempDistance<distance){
                distance = tempDistance;
                closest = enemyUnit;
            }

        }

        return closest;
    }

    /**
     * calculates the difference between the two units
     * using Manhattan distance because fk diagonals
     * @param unit1
     * @param unit2
     * @return
     */
    private int calcDistance(BaseUnit unit1, BaseUnit unit2){
        int x1 = unit1.getX();
        int y1 = unit1.getY();
        int x2 = unit2.getX();
        int y2 = unit2.getY();
        return Math.abs(x2-x1)+Math.abs(y2-y1);
    }
}
