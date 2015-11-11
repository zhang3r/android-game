package com.zhang3r.tarocotta.model.tiles.units.Impl;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.model.army.Army;
import com.zhang3r.tarocotta.model.maps.Map;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Move;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zhang3r on 6/12/2015.
 */
public class BasicMoveImpl implements Move {

    public BasicMoveImpl() {
    }


    public List<AnimatedSprite> getMoveTiles(BaseUnit unit, Army army, Army enemyArmy, Resources resources) {
        int movePoints = unit.getStats().getMovePoints();
        int x = unit.getX();
        int y = unit.getY();
        int mapLengthY = Map.getMap().getGrid().length;
        int mapLengthX = Map.getMap().getGrid()[0].length;

        List<AnimatedSprite> spriteList = new ArrayList<>();
        int lowerX = x - movePoints > 0 ? x - movePoints : 0;
        int upperX = x + movePoints > mapLengthX ? mapLengthX : x + movePoints;
        int lowerY = y - movePoints > 0 ? y - movePoints : 0;
        int upperY = y + movePoints > mapLengthY - 1 ? mapLengthY - 1 : y
                + movePoints;
        //make a grid of possible moves

        Bitmap moveTile = SpriteFactory.getInstance().getTiles(false);
        // upper half
        synchronized (army) {
            synchronized (enemyArmy) {
                for (int i = 0; i + y <= upperY; i++) {
                    for (int j = movePoints - i; j >= 0; j--) {
                        if (j + x <= upperX) {
                            if (!army.hasUnitAtLocation(j + x, i+y) && !enemyArmy.hasUnitAtLocation(j + x, i+y)) {
                                spriteList.add(AnimatedSprite.create(moveTile,
                                        IAppConstants.SPRITE_HEIGHT,
                                        IAppConstants.SPRITE_WIDTH, 1, 1, false, j + x, y + i));

                            }
                        }
                        if (x - j >= lowerX && j > 0) {
                            if (!army.hasUnitAtLocation(x-j, i+y) && !enemyArmy.hasUnitAtLocation(x-j, i+y)) {
                                spriteList.add(AnimatedSprite.create(moveTile,
                                        IAppConstants.SPRITE_HEIGHT,
                                        IAppConstants.SPRITE_WIDTH, 1, 1, false, x - j, y + i));

                            }
                        }
                    }
                }
                for (int i = 1; y - i >= lowerY; i++) {
                    for (int j = movePoints - i; j >= 0; j--) {
                        if (j + x <= upperX) {
                            if (!army.hasUnitAtLocation(j + x, y-i) && !enemyArmy.hasUnitAtLocation(j + x, y-i)) {
                                spriteList.add(AnimatedSprite.create(moveTile,
                                        IAppConstants.SPRITE_HEIGHT,
                                        IAppConstants.SPRITE_WIDTH, 1, 1, false, j + x, y - i));

                            }
                        }
                        if (x - j >= lowerX && j > 0) {
                            if (!army.hasUnitAtLocation(x-j, y-i) && !enemyArmy.hasUnitAtLocation(x-j, y-i)) {
                                spriteList.add(AnimatedSprite.create(moveTile,
                                        IAppConstants.SPRITE_HEIGHT,
                                        IAppConstants.SPRITE_WIDTH, 1, 1, false, x - j, y - i));

                            }
                        }
                    }
                }
            }
        }
        return spriteList;
    }

    /**
     * return true when we have reached our target
     * @param spriteList
     * @param playerArmy
     * @param enemyArmy
     * @param eventX
     * @param eventY
     * @return
     */
    public boolean unitMoveUpdate(List<AnimatedSprite> spriteList, Army playerArmy, Army enemyArmy, double eventX, double eventY) {
        return false;
    }

    //Todo: add impassible terrain

    /**
     * finds the x and y to update for the path.
     * @param target
     * @param playerArmy
     * @param enemeyArmy
     * @return
     */
    public int[] findNextUpdate(MoveTarget target, Army playerArmy, Army enemeyArmy ){
        return null;
    }

}
