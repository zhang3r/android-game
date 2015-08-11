package com.zhang3r.onelevelgame.bitmaps.spriteFactory;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;
import com.zhang3r.onelevelgame.constants.IAppConstants;
import com.zhang3r.onelevelgame.constants.IGameConstants;
import com.zhang3r.onelevelgame.constants.ResourceConstant;

/**
 * Created by Zhang3r on 7/23/2015.
 */
public class SpriteFactory {
    private static SpriteFactory factory;

    private SpriteFactory(){}

    public static SpriteFactory getInstance(){
        if(factory==null){
            factory= new SpriteFactory();

        }
        return factory;
    }

    public  Bitmap getUnit(IGameConstants.UnitType unitType, boolean isEnemy){
        switch(unitType){
            case ARCHER:
                if(isEnemy){

                }
                return getSprite(R.drawable.archer,2);

            case FOOT:
                if(isEnemy){
                    return getSprite(R.drawable.beet_infantry_tile,2);
                }
                return getSprite(R.drawable.infantry,2);
            case CAV:
                if(isEnemy){

                }
                return getSprite(R.drawable.cavalry,2);
        }

        return null;
    }
    public Bitmap getTerrain(int terrain){
        switch(terrain){
            case 0:
                return getSprite(R.drawable.grass_tile,2);

            case 1:
                return getSprite(R.drawable.tree_tile,2);
            case 2:
                return getSprite(R.drawable.rock_tile,2);
        }

        return null;
    }
    public Bitmap getTiles(boolean isEnemy){
        return getSprite(R.drawable.base_move_tile,1);
    }

    private Bitmap getSprite(int path, int frame){
        Bitmap pic= BitmapFactory
                .decodeResource(ResourceConstant.resources, path);
        //TODO change

        pic = Bitmap.createScaledBitmap(pic,frame* IAppConstants.SPRITE_WIDTH, IAppConstants.BITMAP_HEIGHT, false);
        
        return pic;
    }



}
