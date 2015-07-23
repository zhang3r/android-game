package com.zhang3r.onelevelgame.model.tiles.units.Impl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;
import com.zhang3r.onelevelgame.model.tiles.units.Interface.Attack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang3r on 6/12/2015.
 */
public class MeleeAttackImpl implements Attack {

    public MeleeAttackImpl() {
    }


    public List<AnimatedSprite> getUnitAttackTiles(int unitId, int xLength, int yLength, Resources resources, int x, int y, int maxAttackRange, int minAttackRange) {
        int attackRange = maxAttackRange + 1;
        List<AnimatedSprite> spriteList = new ArrayList<>();
        int lowerY = y - attackRange > 0 ? y - attackRange : 0;
        int upperY = y + attackRange > yLength - 1 ? yLength - 1 : y
                + attackRange;
        // upper half
        Bitmap attackSprite = BitmapFactory
                .decodeResource(resources, R.drawable.base_move_tile);
        attackSprite = Bitmap.createScaledBitmap(attackSprite, 50, 50, false);
        for (int z = lowerY, a = 0; z <= y && a <= attackRange; z++, a++) {
            for (int b = 0; b <= attackRange - (y - z); b++) {
                int upper = x + b, lower = x - b;
                if (x + b >= xLength) {
                    upper = xLength - 1;
                }
                if (x - b < 0) {
                    lower = 0;
                }
                if (upper != 0 && lower != 0 && (z != y || upper != x || lower != x)) {
                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, upper, z));

                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, lower, z));
                }
            }

        }
        // lower half
        for (int z = y, a = attackRange; z <= upperY && a >= 0; z++, a--) {
            for (int b = 0; b <= a; b++) {
                int upper = x + b, lower = x - b;
                if (x + b >= xLength) {
                    // upper = map[1].length - 1;
                }
                if (x - b < 0) {
                    lower = 0;
                }
                if (upper != 0 && lower != 0 && (z != y || upper != x || lower != x)) {
                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, upper, z));

                    spriteList.add(AnimatedSprite.create(attackSprite,
                            IAppConstants.SPRITE_HEIGHT,
                            IAppConstants.SPRITE_WIDTH, 1, 1, true, lower, z));
                }
            }

        }
        //spriteList.add(getSprite());

        return spriteList;

    }
}
