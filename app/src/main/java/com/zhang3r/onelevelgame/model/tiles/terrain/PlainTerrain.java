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
public class PlainTerrain extends BaseTerrain{

    public PlainTerrain(Resources resources, int x, int y){
        super();
        this.setDefense(0);
        this.setEvasion(0);
        this.setMovement(1);
        this.setName("plain");
        this.setTerrainId(1);
        // int bitmapNum=map[y][x];
        AnimatedSprite a = new AnimatedSprite();
        Bitmap unitBitmap =  SpriteFactory.getInstance().getTerrain(0);
//                BitmapFactory.decodeResource(resources,
//                R.drawable.base_land_tile);
//        unitBitmap = Bitmap.createScaledBitmap(unitBitmap, 100, 50, false);
        a.Initialize( unitBitmap, IAppConstants.SPRITE_HEIGHT,
                IAppConstants.SPRITE_WIDTH, 2, 2, true);
        a.setXPos(x * IAppConstants.SPRITE_WIDTH);
        a.setYPos(y * IAppConstants.SPRITE_HEIGHT);
        this.setSprite(a);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public String toString(){
        return "plain: no special effects. I wonder if I can build a house here";
    }
}
