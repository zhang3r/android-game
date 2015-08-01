package com.zhang3r.onelevelgame.model.tiles.units;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IGameConstants;
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
        setUnitType(IGameConstants.UnitType.ARCHER);
        setAttackUtil(new RangedAttackImpl());
        setMoveUtil(new BasicMoveImpl());
    }
    public static BaseUnit create(int unitId,
                           String name, int x, int y){
        BaseUnit unit = new ArcheryUnit();
        unit.setName(name);
        unit.setMaxAttackRange(3);
        unit.setMinAttackRange(1);
        unit.setX(x);
        unit.setY(y);
        return unit;
    }

    @Override
    public String toString(){
        return "Archers can do damage at a distance. Legend has it: with enough archers you can blockage the Sun";
    }


}
