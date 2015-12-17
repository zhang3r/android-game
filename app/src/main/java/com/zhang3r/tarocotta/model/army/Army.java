package com.zhang3r.tarocotta.model.army;

import android.util.Log;

import com.zhang3r.tarocotta.constants.IGameConstants.UnitState;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

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
            if(unit.getPosition().getX()==x&&unit.getPosition().getY()==y){
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
                if (unit.getUnitState() ==UnitState.DEAD) {
                    unit.setUnitState(UnitState.DEAD);
                    getUnits().remove(unit);
                    return;
                }
            }
        }
    }

    public boolean hasUnmovedUnits() {
        if (units != null) {
            for (BaseUnit unit : units) {

                if (unit.getUnitState() == UnitState.NORMAL
                        || unit.getUnitState() == UnitState.MOVED) {

                    return true;
                }
            }
        }
        return false;
    }

    public void resetUnitState() {
        if (units != null) {
            for (BaseUnit unit : units) {
                if (unit.getUnitState() != UnitState.DEAD) {
                    unit.setUnitState(UnitState.NORMAL);
                }
            }
        }
    }

    public void setEndTurnState() {
        if (units != null) {
            for (BaseUnit unit : units) {
                if (unit.getUnitState() != UnitState.DEAD) {
                    unit.setUnitState(UnitState.WAIT);
                }
            }
        }
    }

}
