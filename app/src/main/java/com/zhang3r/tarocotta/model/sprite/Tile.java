package com.zhang3r.tarocotta.model.sprite;

import android.graphics.Bitmap;

import com.zhang3r.tarocotta.bitmaps.AnimatedSprite;

/**
 * Created by Zhang3r on 3/17/2015.
 */
public abstract class Tile {
    protected AnimatedSprite sprite;

    protected Bitmap portrait;

    public abstract String getDescription();

    public AnimatedSprite getSprite() {
        return sprite;
    }

    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    public Bitmap getPortrait() {
        return portrait;
    }

    public void setPortrait(Bitmap portrait) {
        this.portrait = portrait;
    }
}
