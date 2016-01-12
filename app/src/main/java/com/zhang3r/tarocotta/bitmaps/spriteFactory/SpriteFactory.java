package com.zhang3r.tarocotta.bitmaps.spriteFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.constants.IGameConstants;
import com.zhang3r.tarocotta.constants.ResourceConstant;

/**
 * Created by Zhang3r on 7/23/2015.
 */
public class SpriteFactory {
    private static SpriteFactory factory;
    private static LruCache<Integer,Bitmap> spriteCache;

    private SpriteFactory(){}

    public static SpriteFactory getInstance(){
        if(factory==null){
            factory= new SpriteFactory();
            // Get max available VM memory, exceeding this amount will throw an
            // OutOfMemory exception. Stored in kilobytes as LruCache takes an
            // int in its constructor.
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            spriteCache = new LruCache<Integer,Bitmap>(cacheSize){
                @Override
                protected int sizeOf(Integer key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };

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
        if(spriteCache.get(path)!=null){
            return spriteCache.get(path);
        }
        Bitmap pic= BitmapFactory
                .decodeResource(ResourceConstant.resources, path);
        //TODO change
        if(pic!=null) {
            pic = Bitmap.createScaledBitmap(pic, numFrame * IAppConstants.SPRITE_WIDTH, numAnimation*IAppConstants.SPRITE_HEIGHT, false);

        }
        if(pic!=null) {
            spriteCache.put(path, pic);
        }
        return pic;
    }



}
