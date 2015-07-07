package com.zhang3r.onelevelgame.bitmaps;

import android.content.res.Resources;


public class BitmapLibrary {
    //	red = BitmapFactory.decodeResource(getResources(), R.drawable.redsq);
//	lightblue = BitmapFactory.decodeResource(getResources(), R.drawable.lightbluesq);
    Resources resources;

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

}
