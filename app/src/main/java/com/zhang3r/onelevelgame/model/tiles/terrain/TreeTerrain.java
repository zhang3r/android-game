package com.zhang3r.onelevelgame.model.tiles.terrain;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.bitmaps.spriteFactory.SpriteFactory;
import com.zhang3r.onelevelgame.constants.IAppConstants;

/**
 * Created by Zhang3r on 5/5/2015.
 */
public class TreeTerrain extends BaseTerrain {

    public TreeTerrain(Resources resources, int x, int y){
        super();
        this.setDefense(2);
        this.setMovement(1);
        this.setName("tree");
        this.setTerrainId(2);

        AnimatedSprite a = new AnimatedSprite();
        Bitmap unitBitmap = SpriteFactory.getInstance(null).getTerrain(1);
        a.Initialize(unitBitmap, IAppConstants.SPRITE_HEIGHT,
                IAppConstants.SPRITE_WIDTH, 1, 1, true);
        a.setXPos(x * IAppConstants.SPRITE_WIDTH);
        a.setYPos(y * IAppConstants.SPRITE_HEIGHT);
        this.setSprite(a);
        this.setX(x);
        this.setY(y);
    }
    @Override
    public String toString(){
        return "forest: increase defense. cool off in the shade";
    }
}
