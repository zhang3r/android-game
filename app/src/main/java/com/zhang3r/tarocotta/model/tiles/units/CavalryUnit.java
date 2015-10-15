package com.zhang3r.tarocotta.model.tiles.units;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.tarocotta.model.tiles.units.Impl.MeleeAttackImpl;

/**
 * Created by Zhang3r on 5/22/2015.
 */
public class CavalryUnit extends BaseUnit {

    public CavalryUnit(){
        super();
        setName("Basic Cavalry Unit" + getUnitId());

        setUnitType(IGameConstants.UnitType.CAV);
        setAttackUtil(new MeleeAttackImpl());
        setMoveUtil(new BasicMoveImpl());
    }

    public static BaseUnit create(int unitId,
                              String name, int x, int y){
        BaseUnit unit = new CavalryUnit();
        unit.setUnitId(unitId);
        unit.setName(name);
        unit.setMaxAttackRange(1);
        unit.setX(x);
        unit.setY(y);
        return unit;
    }

    @Override
    public String getDescription(){
        return "Cavalry is a man on a horse; a fine looking horse.";
    }
}
