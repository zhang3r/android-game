package com.zhang3r.onelevelgame.model.tiles.units;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.RangedAttackImpl;


/**
 * Created by Zhang3r on 5/22/2015.
 */
public class ArcheryUnit extends BaseUnit{

    public ArcheryUnit(){
        super();
        //TODO: import stats elsewhere
        setName("Basic Archery Unit" + getUnitId());
        setAttackUtil(new RangedAttackImpl());
        setMoveUtil(new BasicMoveImpl());
    }
    public static BaseUnit create(int unitId,
                           String name, int x, int y){
        BaseUnit unit = new ArcheryUnit();
        unit.setName(name);
        unit.setX(x);
        unit.setY(y);
        return unit;
    }


}
