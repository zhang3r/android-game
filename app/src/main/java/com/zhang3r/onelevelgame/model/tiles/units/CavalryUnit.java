package com.zhang3r.onelevelgame.model.tiles.units;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.MeleeAttackImpl;

/**
 * Created by Zhang3r on 5/22/2015.
 */
public class CavalryUnit extends BaseUnit {

    public CavalryUnit(){
        super();
        setName("Basic Cavalry Unit"+getUnitId());
        setAttackUtil(new MeleeAttackImpl());
        setMoveUtil(new BasicMoveImpl());
    }

    public static BaseUnit create(int unitId,
                              String name, int x, int y){
        BaseUnit unit = new CavalryUnit();
        unit.setName(name);
        unit.setX(x);
        unit.setY(y);
        return unit;
    }
}
