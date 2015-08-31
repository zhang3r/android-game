package com.zhang3r.tarocotta.terminate;

import com.zhang3r.tarocotta.model.army.Army;

/**
 * Created by Zhang3r on 8/1/2015.
 */
public class AllUnitsDestroyed implements TerminateCondition{
    private String terminateString = "";

    public AllUnitsDestroyed(){}
    public boolean isWin(Army army){
        if(army.getUnits().size()==0){
            terminateString = "You have vanquished the enemy! You have won the day!";
            return true;
        }
        return false;
    }

    public boolean isLose(Army army){
        if(army.getUnits().size()==0){
            terminateString = "You have lost all of your units! Shameful Display!";
            return true;
        }
        return false;
    }

    public String getTerminateString(){
        return terminateString;
    }

}
