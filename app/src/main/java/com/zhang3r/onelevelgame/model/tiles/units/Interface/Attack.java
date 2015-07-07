package com.zhang3r.onelevelgame.model.tiles.units.Interface;

import android.content.res.Resources;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;

import java.util.List;

/**
 * Created by Zhang3r on 6/12/2015.
 */
public interface Attack {

    List<AnimatedSprite> getUnitAttackTiles(int unitId,int xLength, int yLength,
                                            Resources resources, int x, int y, int maxAttackRange, int minAttackRange);

}
