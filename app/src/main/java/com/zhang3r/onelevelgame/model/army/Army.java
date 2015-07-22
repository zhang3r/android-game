package com.zhang3r.onelevelgame.model.army;

import android.util.Log;

import com.zhang3r.onelevelgame.constants.IGameConstants.UnitState;
import com.zhang3r.onelevelgame.constants.ILogConstants;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

import java.util.ArrayList;
import java.util.List;

public class Army {
    private String team;
    private List<BaseUnit> units;

    public static Army create(String team) {
        Army army = new Army();
        army.setTeam(team);
        return army;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public List<BaseUnit> getUnits() {
        return units;
    }

    public void setUnits(List<BaseUnit> units) {
        this.units = units;
    }

    public boolean hasUnitAtLocation(int x, int y){
        for(BaseUnit unit:units){
            if(unit.getX()==x&&unit.getY()==y){
                return true;
            }
        }
        return false;
    }

    public void add(BaseUnit unit) {
        if (units == null) {
            units = new ArrayList<>();
        }
        units.add(unit);
    }

    public void remove(BaseUnit removal) {
        if (units == null) {
            return;
        } else {
            for (BaseUnit unit : units) {
                if (unit.getUnitId() == (removal.getUnitId())) {
                    Log.d(ILogConstants.DEBUG_TAG, "removing unit " + unit.getUnitId());
                    unit.setState(UnitState.DEAD);
                    getUnits().remove(unit);
                    return;
                }
            }
        }
    }

    public boolean hasUnmovedUnits() {
        if (units != null) {
            for (BaseUnit unit : units) {
                Log.d(ILogConstants.DEBUG_TAG,
                        "Unit state is :" + unit.getState());
                if (unit.getState() == UnitState.NORMAL
                        || unit.getState() == UnitState.MOVED) {

                    return true;
                }
            }
        }
        return false;
    }

    public void resetUnitState() {
        if (units != null) {
            for (BaseUnit unit : units) {
                if (unit.getState() != UnitState.DEAD) {
                    unit.setState(UnitState.NORMAL);
                }
            }
        }
    }

    public void setEndTurnState() {
        if (units != null) {
            for (BaseUnit unit : units) {
                if (unit.getState() != UnitState.DEAD) {
                    unit.setState(UnitState.WAIT);
                }
            }
        }
    }

}
