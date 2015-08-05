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
                return getSprite(R.drawable.bow);

            case FOOT:
                if(isEnemy){

                }
                return getSprite(R.drawable.infantry_tile);
            case CAV:
                if(isEnemy){

                }
                return getSprite(R.drawable.cavalry);
        }

        return null;
    }
    public Bitmap getTerrain(int terrain){
        switch(terrain){
            case 0:
                return getSprite(R.drawable.grass_tile);

            case 1:
                return getSprite(R.drawable.tree_tile);
            case 2:
                return getSprite(R.drawable.rock_tile);
        }

        return null;
    }
    public Bitmap getTiles(boolean isEnemy){
        return getSprite(R.drawable.base_move_tile);
    }

    private Bitmap getSprite(int path){
        Bitmap pic= BitmapFactory
                .decodeResource(ResourceConstant.resources, path);
        //TODO change

        pic = Bitmap.createScaledBitmap(pic, IAppConstants.BITMAP_WIDTH, IAppConstants.BITMAP_HEIGHT, false);
        
        return pic;
    }



}
