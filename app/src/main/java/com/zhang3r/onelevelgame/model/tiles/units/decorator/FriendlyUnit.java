package com.zhang3r.onelevelgame.model.tiles.units.decorator;

import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

/**
 * Created by Zhang3r on 8/11/2015.
 */
public class FriendlyUnit extends BaseUnit{
    private BaseUnit unit;

    public FriendlyUnit(BaseUnit unit){
        this.unit=unit;
    }

    @Override
    public String toString(){
        return unit.toString()+" This unit is friendly to your forces.";
    }
}
