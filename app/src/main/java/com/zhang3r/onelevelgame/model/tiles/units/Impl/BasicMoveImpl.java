package com.zhang3r.onelevelgame.model.tiles.units.Impl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;
import com.zhang3r.onelevelgame.constants.ILogConstants;
import com.zhang3r.onelevelgame.model.army.Army;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;
import com.zhang3r.onelevelgame.model.tiles.units.Interface.Move;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zhang3r on 6/12/2015.
 */
public class BasicMoveImpl implements Move {

    public BasicMoveImpl(){}


    public List<AnimatedSprite> getMoveTiles(int xLength, int yLength, int x, int y, Resources resources, int unitId, int movePoints) {
        List<AnimatedSprite> spriteList = new ArrayList<AnimatedSprite>();
        int lowerX = x - movePoints > 0 ? x - movePoints : 0;
        int upperX = x + movePoints > xLength ? xLength : x + movePoints;
        int lowerY = y - movePoints > 0 ? y - movePoints : 0;
        int upperY = y + movePoints > yLength - 1 ? yLength - 1 : y
                + movePoints;
        Bitmap moveTile =BitmapFactory
                .decodeResource(resources, R.drawable.base_move_tile);

        // upper half

        for (int z = lowerY, a = 0; z <= y && a <= movePoints; z++, a++) {
            for (int b = 0; b <= movePoints - (y - z); b++) {
                int upper = x + b, lower = x - b;
                if (x + b >= xLength) {
                    upper = xLength - 1;
                }
                if (x - b < 0) {
                    lower = 0;
                }
                if(upper!=0&&lower!=0) {
                    spriteList.add(AnimatedSprite.create(moveTile ,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, upper, z));

                    spriteList.add(AnimatedSprite.create(moveTile,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, lower, z));
                }
            }

        }
        // lower half
        for (int z = y, a = movePoints; z <= upperY && a >= 0; z++, a--) {
            for (int b = 0; b <= a; b++) {
                int upper = x + b, lower = x - b;
                if (x + b >= xLength) {
                    // upper = map[1].length - 1;
                }
                if (x - b < 0) {
                    lower = 0;
                }
                if(upper!=0&&lower!=0) {
                    spriteList.add(AnimatedSprite.create(moveTile,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, upper, z));

                    spriteList.add(AnimatedSprite.create(moveTile,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, lower, z));
                }
            }

        }
        return spriteList;
    }


    public boolean unitMoveUpdate(List<AnimatedSprite> spriteList, Army playerArmy, Army enemyArmy, double eventX, double eventY) {
            int eventXPos = (int) eventX / IAppConstants.SPRITE_WIDTH;
            int eventYPos = (int) eventY / IAppConstants.SPRITE_HEIGHT;
            for (AnimatedSprite sprite : spriteList) {
                int xPos = sprite.getXPos() / IAppConstants.SPRITE_WIDTH;
                int yPos = sprite.getYPos() / IAppConstants.SPRITE_HEIGHT;
                if (eventXPos == xPos && eventYPos == yPos) {
                    Log.d(ILogConstants.DEBUG_TAG, "unit is moving to X: "
                            + (xPos) + " Y: " + (yPos));
                    // check if x, y isn't another unit
                    synchronized (playerArmy) {
                        synchronized (enemyArmy) {

                            List<BaseUnit> units = new LinkedList<BaseUnit>();
                            units.addAll(playerArmy.getUnits());
                            units.addAll(enemyArmy.getUnits());
                            for (BaseUnit unit : units) {
                                AnimatedSprite animatedSprite = unit.getSprite();
                                Log.d(ILogConstants.DEBUG_TAG,
                                        "there's a unit at X: "
                                                + (animatedSprite.getXPos() / IAppConstants.SPRITE_WIDTH)
                                                + " Y: "
                                                + (animatedSprite.getYPos() / IAppConstants.SPRITE_HEIGHT));
                                if ((animatedSprite.getXPos() / IAppConstants.SPRITE_WIDTH) == xPos
                                        && (animatedSprite.getYPos() / IAppConstants.SPRITE_HEIGHT) == yPos) {
                                    return false;
                                }
                            }
                        }
                    }

                    return true;
                }
            }
        return false;
    }
}
