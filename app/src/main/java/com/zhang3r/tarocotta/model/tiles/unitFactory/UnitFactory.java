package com.zhang3r.tarocotta.model.tiles.unitFactory;

import com.zhang3r.tarocotta.constants.IGameConstants.UnitType;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

/**
 * Created by Zhang3r on 6/23/2015.
 */
public interface UnitFactory {

    public BaseUnit createUnit(UnitType unitType);
}
