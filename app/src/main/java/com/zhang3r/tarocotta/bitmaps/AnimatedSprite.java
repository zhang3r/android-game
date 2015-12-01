package com.zhang3r.tarocotta.bitmaps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.zhang3r.tarocotta.constants.IAppConstants;
import com.zhang3r.tarocotta.model.tiles.statsFactory.impl.Point;

public class AnimatedSprite {
    public boolean dispose;
    private Bitmap animation;
    private int xPos;
    private int yPos;
    private Point point;
    private Rect sRectangle;
    private int fps;
    private int numFrames;
    private int currentFrame;
    private long frameTimer;
    private int spriteHeight;
    private int spriteWidth;
    private boolean loop;
    private int currentAnimation;

    public AnimatedSprite() {
        sRectangle = new Rect(0, 0, 0, 0);
        frameTimer = 0;
        currentFrame = 0;
        currentAnimation=0;
        xPos = 80;
        yPos = 200;
        dispose = false;
    }

    public static AnimatedSprite create( Bitmap bitmap, int height,
                                        int width, int fps, int frameCount, boolean loop, int x, int y) {
        AnimatedSprite a = new AnimatedSprite();
        a.Initialize(bitmap, height, width, fps, frameCount, loop);
        a.setPoint(new Point(x,y));
        a.setXPos(x * width);
        a.setYPos(y * height);
        return a;
    }

    public void Initialize( Bitmap bitmap, int height, int width,
                           int fps, int frameCount, boolean loop) {

        this.animation = bitmap;
        this.spriteHeight = IAppConstants.SPRITE_HEIGHT;
        this.spriteWidth = IAppConstants.SPRITE_WIDTH;
        this.sRectangle.top = 0;
        this.sRectangle.bottom = IAppConstants.SPRITE_HEIGHT;
        this.sRectangle.left = 0;
        this.sRectangle.right = spriteWidth;
        this.fps = 1000 / fps;
        this.numFrames = frameCount;
        this.loop = loop;
    }
    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void Update(long gameTime) {
        if (gameTime > frameTimer + fps) {
            frameTimer = gameTime;
            ++currentFrame;
            if (currentFrame >= numFrames) {
                currentFrame = 0;
                if (!loop) {
                    dispose = true;
                }
            }
            sRectangle.left = currentFrame * IAppConstants.SPRITE_WIDTH;
            sRectangle.right = sRectangle.left + IAppConstants.SPRITE_WIDTH;
            sRectangle.top = currentAnimation * IAppConstants.SPRITE_HEIGHT;
            sRectangle.bottom = sRectangle.top + IAppConstants.SPRITE_HEIGHT;
        }
    }

    public void draw(Canvas canvas, float left, float top, float right,
                     float bottom) {
        Rect dest = new Rect((int) (getXPos() + left), (int) (getYPos() + top),
                (int) (getXPos() + spriteWidth + left), (int) (getYPos()
                + spriteHeight + top));
        if ((dest.left >= 0 - spriteWidth && dest.right >= right)
                && (dest.top >= 0 - spriteHeight && dest.bottom >= bottom)) {
            canvas.drawBitmap(animation, sRectangle, dest, null);
        }

    }

    public Bitmap getAnimation() {
        return animation;
    }

    public void setAnimation(Bitmap animation) {
        this.animation = animation;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public Rect getsRectangle() {
        return sRectangle;
    }

    public void setsRectangle(Rect sRectangle) {
        this.sRectangle = sRectangle;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public long getFrameTimer() {
        return frameTimer;
    }

    public void setFrameTimer(long frameTimer) {
        this.frameTimer = frameTimer;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isDispose() {
        return dispose;
    }

    public void setDispose(boolean dispose) {
        this.dispose = dispose;
    }


}
