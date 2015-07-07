package com.zhang3r.onelevelgame.model.tiles.units.Interface;

import android.content.res.Resources;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.model.army.Army;

import java.util.List;

/**
 * Created by Zhang3r on 6/12/2015.
 */
public interface Move {
    List<AnimatedSprite> getMoveTiles(int xLength, int yLength, int x, int y, Resources resources, int unitId, int movePoints);

    boolean unitMoveUpdate(List<AnimatedSprite> spriteList,
                           Army playerArmy, Army enemyArmy, double eventX, double eventY);




}
