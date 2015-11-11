package com.zhang3r.tarocotta.model.tiles.units.Interface;

import android.content.res.Resources;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;

import java.util.List;

/**
 * Created by Zhang3r on 6/12/2015.
 */
public interface Move {
    List<AnimatedSprite> getMoveTiles(BaseUnit unit, Army army, Army enemyArmy, Resources resources);

    boolean unitMoveUpdate(List<AnimatedSprite> spriteList,
                           Army playerArmy, Army enemyArmy, double eventX, double eventY);


}
