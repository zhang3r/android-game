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
public class FriendlyUnit implements IAllegiance {
    private BaseUnit unit;
    public FriendlyUnit(BaseUnit unit){
        this.unit=unit;
    }

    @Override
    public String getDescription() {
        return unit.getDescription()+" This unit is loyal to your cause.";
    }

    public BaseUnit getUnit() {
        return unit;
    }
}
