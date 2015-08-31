package com.zhang3r.tarocotta.model.tiles.statsFactory.impl;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.tiles.statsFactory.StatsFactory;

/**
 * Created by Zhang3r on 8/13/2015.
 */
public class StatsFactoryImpl implements StatsFactory {

    @Override
    public Stats createStat( IGameConstants.UnitType unitType) {
        Stats stat= new Stats();
        //TODO: read from file
        switch (unitType) {
            case ARCHER:
                stat.setHitPoints(20);
                stat.setMaxHP(20);
                stat.setMinAttackRange(1);
                stat.setMaxAttackRange(3);
                stat.setMovePoints(4);
                stat.setAttack(10);
                stat.setDefense(2);

                break;
            case CAV:
                stat.setHitPoints(25);
                stat.setMaxHP(25);
                stat.setMinAttackRange(0);
                stat.setMaxAttackRange(1);
                stat.setMovePoints(6);
                stat.setAttack(14);
                stat.setDefense(3);
                break;
            case FOOT:
                stat.setHitPoints(35);
                stat.setMaxHP(35);
                stat.setMinAttackRange(0);
                stat.setMaxAttackRange(1);
                stat.setMovePoints(3);
                stat.setAttack(8);
                stat.setDefense(6);
                break;
        }
        return stat;
    }
}
