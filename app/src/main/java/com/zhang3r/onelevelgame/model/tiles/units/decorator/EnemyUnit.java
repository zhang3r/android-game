package com.zhang3r.onelevelgame.model.tiles.units.decorator;

import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

/**
 * Created by Zhang3r on 8/11/2015.
 */
public class EnemyUnit extends BaseUnit {
    private BaseUnit unit;
    public EnemyUnit(BaseUnit unit){
        this.unit=unit;
    }

    public String toString(){
        return unit.toString()+" This unit will attack you on sight.";
    }
}
