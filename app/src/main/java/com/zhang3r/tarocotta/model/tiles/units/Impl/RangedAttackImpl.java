package com.zhang3r.tarocotta.model.tiles.units.Impl;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;
import com.zhang3r.tarocotta.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.model.tiles.units.Interface.Attack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang3r on 6/14/2015.
 */
public class RangedAttackImpl implements Attack {
    public RangedAttackImpl() {
    }

    public List<AnimatedSprite> getUnitAttackTiles(int unitId, int xLength, int yLength, Resources resources, int x, int y, int maxAttackRange, int minAttackRange) {

        int attackRange = maxAttackRange;
        List<AnimatedSprite> spriteList = new ArrayList<>();

        Bitmap attackSprite = SpriteFactory.getInstance().getTiles(false);
        int lowerX = x - attackRange > 0 ? x - attackRange : 0;
        int upperX = x + attackRange > xLength - 1 ? xLength - 1 : x + attackRange;
        int lowerY = y - attackRange > 0 ? y - attackRange : 0;
        int upperY = y + attackRange > yLength - 1 ? yLength - 1 : y
                + attackRange;
        // upper half


        for (int i = 0; i + y <= upperY; i++) {
            for (int j = attackRange - i; j >= 0; j--) {
                if (j + x <= upperX) {
                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, false, j + x, y + i));

                }
                if (x - j >= lowerX && j > 0) {
                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, false, x - j, y + i));

                }
            }
        }
        for (int i = 1; y - i >= lowerY; i++) {
            for (int j = attackRange - i; j >= 0; j--) {
                if (j + x <= upperX) {
                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, false, j + x, y - i));

                }
                if (x - j >= lowerX && j > 0) {
                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, false, x - j, y - i));

                }
            }

        }
        //spriteList.add(getSprite());
        return spriteList;

    }
}
