package com.zhang3r.tarocotta.model.tiles.statsFactory;

import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Stats;

/**
 * Created by Zhang3r on 8/13/2015.
 */
public interface StatsFactory {

    public Stats createStat( IGameConstants.UnitType unitType);
}
