package com.zhang3r.tarocotta.terminate;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

/**
 * Created by Zhang3r on 8/1/2015.
 */
public class AllUnitsDestroyed implements TerminateCondition{
    private String terminateString = "";

    public AllUnitsDestroyed(){}
    public boolean isWin(Army army){
        for(BaseUnit u: army.getUnits()){
            if(u.getUnitState()!= IGameConstants.UnitState.DEAD){
                return false;
            }

        }
        terminateString = "You have vanquished the enemy! You have won the day!";
        return true;
    }

    public boolean isLose(Army army){
        for(BaseUnit u: army.getUnits()){
            if(u.getUnitState()!= IGameConstants.UnitState.DEAD){
                return false;
            }

        }
        terminateString = "You have lost all of your units! Shameful Display!";
        return true;

    }

    public String getTerminateString(){
        return terminateString;
    }

}
