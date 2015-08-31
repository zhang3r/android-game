package com.zhang3r.tarocotta.model.tiles.unitFactory.impl;

import com.zhang3r.tarocotta.constants.IGameConstants.UnitType;
import com.zhang3r.tarocotta.model.tiles.unitFactory.UnitFactory;
import com.zhang3r.tarocotta.model.tiles.units.ArcheryUnit;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.CavalryUnit;
import com.zhang3r.tarocotta.model.tiles.units.InfantryUnit;

/**
 * Created by Zhang3r on 7/8/2015.
 */
public class UnitFactoryImpl implements UnitFactory {
    private static UnitFactoryImpl factoryImpl;

    private UnitFactoryImpl() {

    }

    public static UnitFactory getInstance() {
        if (factoryImpl == null) {
            factoryImpl = new UnitFactoryImpl();
        }
        return factoryImpl;
    }


    public BaseUnit createUnit(UnitType unitType) {
        BaseUnit unit = null;

        switch (unitType) {
            case ARCHER:
                unit = new ArcheryUnit();
                //TODO: modify Archery unit here
                break;
            case CAV:
                unit = new CavalryUnit();
                //TODO: modify Cav unit here
                break;
            case FOOT:
                unit = new InfantryUnit();
                //TODO: modify infantry unit here
                break;
        }
        return unit;

    }
}
