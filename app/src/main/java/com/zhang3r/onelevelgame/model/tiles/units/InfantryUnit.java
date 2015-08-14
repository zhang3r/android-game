package com.zhang3r.onelevelgame.model.tiles.units;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IGameConstants;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.BasicMoveImpl;
import com.zhang3r.onelevelgame.model.tiles.units.Impl.MeleeAttackImpl;

/**
 * Created by Zhang3r on 5/22/2015.
 */
public class InfantryUnit extends BaseUnit{

    public InfantryUnit(){
        super();
        setName("Basic Infantry Unit" + getUnitId());
        setStats(statsFactory.createStat(IGameConstants.UnitType.FOOT));
        setAttackUtil(new MeleeAttackImpl());
        setUnitType(IGameConstants.UnitType.FOOT);
        setMoveUtil(new BasicMoveImpl());
    }

    public static BaseUnit create(int unitId,
                           String name, int x, int y){
        BaseUnit unit = new InfantryUnit();
        unit.setName(name);
        unit.setMaxAttackRange(1);
        unit.setX(x);
        unit.setY(y);
        return unit;
    }

    @Override
    public String toString(){
        return "Heavy Infantry are the backbone of any army.";
    }





}
