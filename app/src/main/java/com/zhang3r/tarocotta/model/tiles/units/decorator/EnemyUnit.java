package com.zhang3r.tarocotta.model.tiles.units.decorator;

import android.content.res.Resources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import java.util.List;

/**
 * Created by Zhang3r on 8/11/2015.
 */
public class EnemyUnit implements IAllegiance {
    private BaseUnit unit;
    public EnemyUnit(BaseUnit unit){
        this.unit=unit;
    }

    @Override
    public String getDescription() {
//        return unit.getDescription()+" This unit will attack you on sight.";
        return "";
    }

    public BaseUnit getUnit() {
        return unit;
    }
}

