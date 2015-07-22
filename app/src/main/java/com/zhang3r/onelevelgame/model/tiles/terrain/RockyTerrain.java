package com.zhang3r.onelevelgame.model.tiles.terrain;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;

/**
 * Created by Zhang3r on 5/5/2015.
 */
public class RockyTerrain extends BaseTerrain {

    public RockyTerrain(Resources resources, int x, int y){
        super();
        this.setDefense(-1);
        this.setEvasion(-1);
        this.setMovement(1);
        this.setName("rockey");
        this.setTerrainId(3);

        AnimatedSprite a = new AnimatedSprite();
        Bitmap unitBitmap = BitmapFactory.decodeResource(resources,
                R.drawable.uncrossable_land_tile);
        unitBitmap = Bitmap.createScaledBitmap(unitBitmap, 50, 50, false);
        a.Initialize( unitBitmap, IAppConstants.SPRITE_HEIGHT,
                IAppConstants.SPRITE_WIDTH, 1, 1, true);
        a.setXPos(x * IAppConstants.SPRITE_WIDTH);
        a.setYPos(y * IAppConstants.SPRITE_HEIGHT);
        this.setSprite(a);
        this.setX(x);
        this.setY(y);
    }
}
