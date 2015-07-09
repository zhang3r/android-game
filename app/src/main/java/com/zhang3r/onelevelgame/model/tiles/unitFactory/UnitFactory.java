package com.zhang3r.onelevelgame.model.tiles.unitFactory;

import com.zhang3r.onelevelgame.constants.IGameConstants.UnitType;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;

/**
 * Created by Zhang3r on 6/23/2015.
 */
public interface UnitFactory {

    public BaseUnit createUnit(UnitType unitType);
}
