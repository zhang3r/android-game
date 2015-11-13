package com.zhang3r.tarocotta.views;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.gameFramework.AnimationThread;

public class MapView extends SurfaceView implements SurfaceHolder.Callback,
        OnGestureListener {

    private static int screenWidth;
    private static int screenHeight;
    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    // private RectF mCurrentViewport = new RectF(IAppConstants.AXIS_X_MIN,
    // IAppConstants.AXIS_Y_MIN, IAppConstants.AXIS_X_MAX,
    // IAppConstants.AXIS_Y_MAX);
    // private Rect mContentRect;
    // private OverScroller overScroller;
    // private RectF mScrollerStartViewport;
    //
    // // EDGE EFFECT OBJS
    // private EdgeEffectCompat mEdgeEffectTop;
    // private EdgeEffectCompat mEdgeEffectBottom;
    // private EdgeEffectCompat mEdgeEffectLeft;
    // private EdgeEffectCompat mEdgeEffectRight;
    //
    // private boolean mEdgeEffectTopActive;
    // private boolean mEdgeEffectBottomActive;
    // private boolean mEdgeEffectLeftActive;
    // private boolean mEdgeEffectRightActive;

    /**
     * The thread that actually draws the animation
     */
    private AnimationThread thread;
    private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            Log.d(ILogConstants.GESTURE_TAG, "Action was SCALE");
            thread.doScale(scaleGestureDetector);
            return true;

        }
    };

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.setFormat(PixelFormat.RGBA_8888);
        holder.addCallback(this);
        mDetector = new GestureDetectorCompat(context, this);
        mScaleGestureDetector = new ScaleGestureDetector(context,
                mScaleGestureListener);
        // create thread only; it's started in surfaceCreated()
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

        screenHeight = size.y;
        thread = new AnimationThread(holder, context, screenWidth,
                screenHeight, this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean retVal = mScaleGestureDetector.onTouchEvent(event);
        retVal = this.mDetector.onTouchEvent(event) || retVal;
        return retVal || super.onTouchEvent(event);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        // releaseEdgeEffects();

        Log.d(ILogConstants.GESTURE_TAG, "Action was GESTURE_DOWN");
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // TODO Auto-generated method stub
        Log.d(ILogConstants.GESTURE_TAG,
                "Action was GESTURE_FLING: " + e1.toString() + e2.toString());

        return false;
    }

    // @Override
    // public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
    // float velocityY) {
    // // TODO Auto-generated method stub
    // Log.d(IStateConstants.GESTURE_TAG,
    // "Action was GESTURE_FLING: " + e1.toString() + e2.toString());
    // fling((int) -velocityX, (int) -velocityY);
    // return false;
    // }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub
        Log.d(ILogConstants.GESTURE_TAG, "Action was GESTURE_LONGPRESS: ");

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        Log.d(ILogConstants.GESTURE_TAG, "Action was GESTURE_SCROLL");
        thread.doScroll(e1, e2, distanceX, distanceY);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub
        Log.d(ILogConstants.GESTURE_TAG, "Action was GESTURE_SHOWPRESS: ");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        Log.d(ILogConstants.GESTURE_TAG, "Action was GESTURE_SINGLE_TAP_UP");
        thread.doUp(e);
        return true;
    }

    //
    // private void fling(int velocityX, int velocityY) {
    // // Initiates the decay phase of any active edge effects
    // // releaseEdgeEffects();
    //
    // // Flings use math in pixels
    // Point surfaceSize = computeScrollSurfaceSize();
    // mScrollerStartViewport.set(mCurrentViewport);
    // int startX = (int) (surfaceSize.x
    // * (mScrollerStartViewport.left - IAppConstants.AXIS_X_MIN) /
    // (IAppConstants.AXIS_X_MAX - IAppConstants.AXIS_X_MIN));
    // int startY = (int) (surfaceSize.y
    // * (IAppConstants.AXIS_Y_MAX - mScrollerStartViewport.bottom) /
    // (IAppConstants.AXIS_Y_MAX - IAppConstants.AXIS_Y_MIN));
    // overScroller.forceFinished(true);
    // overScroller.fling(startX, startY, velocityX, velocityY, 0,
    // surfaceSize.x - mContentRect.width(), 0, surfaceSize.y
    // - mContentRect.height(), mContentRect.width() / 2,
    // mContentRect.height() / 2);
    // // Invalidate to trigger computeScroll()
    // ViewCompat.postInvalidateOnAnimation(this);
    //
    // }
    //
    // private Point computeScrollSurfaceSize() {
    // return new Point(
    // (int) (mContentRect.width()
    // * (IAppConstants.AXIS_X_MAX - IAppConstants.AXIS_X_MIN) /
    // mCurrentViewport
    // .width()),
    // (int) (mContentRect.height()
    // * (IAppConstants.AXIS_Y_MAX - IAppConstants.AXIS_Y_MIN) /
    // mCurrentViewport
    // .height()));
    // }
    //

    /**
     * Obligatory method that belong to the:implements SurfaceHolder.Callback
     */

    /**
     * Callback invoked when the surface dimensions change.
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (thread) {
            if (!(thread.getState() == Thread.State.NEW)) {
                // When game is opened again in the Android OS
                thread = new AnimationThread(holder, this.getContext(),
                        screenWidth, screenHeight, this);

            }
            thread.setRunning(true);
            thread.start();
        }
    }

    /**
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode

        boolean retry = true;
        while (retry) {
            try {
                synchronized (thread) {
                    thread.join();
                    retry = false;
                }
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }

    }

    protected void onResume() {
        // thread.setRunning(true);
    }

    protected void onPause() {
        // thread.setRunning(false);

    }

    public void updateFromFragemnt(String s) {

        thread.buttonEventHandler(s);

    }

    public BaseUnit getUnitedSelected() {
//        BaseUnit unit = thread.getUnitToMove();
//        return unit;
        return null;
    }
}
