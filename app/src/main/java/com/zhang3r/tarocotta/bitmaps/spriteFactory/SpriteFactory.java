package com.zhang3r.tarocotta.bitmaps.spriteFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ResourceConstant;

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
//        switch(unitType){
//            case ARCHER:
//                if(isEnemy){
//
//                }
//                return getSprite(R.drawable.archer,2);
//
//            case FOOT:
//                if(isEnemy){
//                    return getSprite(R.drawable.beet_infantry_tile,2);
//                }
//                return getSprite(R.drawable.infantry,2);
//            case CAV:
//                if(isEnemy){
//
//                }
//                return getSprite(R.drawable.cavalry,2);
//
//        }

        return getSprite(R.drawable.test_tile,5,4);
    }
    public Bitmap getTerrain(int terrain){
        switch(terrain){
            case 0:
                return getSprite(R.drawable.grass_tile,1,1);

            case 1:
                return getSprite(R.drawable.tree_tile,1,1);
            case 2:
                return getSprite(R.drawable.rock_tile,1,1);
        }

        return null;
    }
    public Bitmap getTiles(boolean isEnemy){
        return getSprite(R.drawable.base_move_tile,1,1);
    }

    private Bitmap getSprite(int path, int numFrame, int numAnimation){
        Bitmap pic= BitmapFactory
                .decodeResource(ResourceConstant.resources, path);
        //TODO change
        if(pic!=null) {
            pic = Bitmap.createScaledBitmap(pic, numFrame * IAppConstants.SPRITE_WIDTH, numAnimation*IAppConstants.SPRITE_HEIGHT, false);

        }
        return pic;
    }



}
