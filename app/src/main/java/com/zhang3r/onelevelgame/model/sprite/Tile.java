package com.zhang3r.onelevelgame.model.sprite;

import com.zhang3r.onelevelgame.bitmaps.AnimatedSprite;

/**
 * Created by Zhang3r on 3/17/2015.
 */
public abstract class Tile {
    protected AnimatedSprite sprite;

    public AnimatedSprite getSprite() {
        return sprite;
    }

    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }
}
