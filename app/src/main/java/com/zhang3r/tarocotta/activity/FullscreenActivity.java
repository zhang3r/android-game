package com.zhang3r.tarocotta.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.fragments.MapFragment;
import com.zhang3r.tarocotta.fragments.MapUiFragment;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.util.SystemUiHider;
import com.zhang3r.tarocotta.views.MapViewGroup;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see com.zhang3r.tarocotta.util.SystemUiHider
 */
public class FullscreenActivity extends FragmentActivity implements
        MapFragment.OnMapListener, MapUiFragment.OnButtonClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        // setContentView(R.layout.activity_fullscreen);
        ViewGroup vg = new MapViewGroup(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        MapFragment mapFragment = new MapFragment();
        MapUiFragment mapUiFragment = new MapUiFragment();
        mapUiFragment.setMenuVisibility(true);
        fragmentTransaction.add(R.id.fullscreen_content, mapFragment,
                "mapFragment");
        fragmentTransaction.add(R.id.fullscreen_content, mapUiFragment,
                "mapUiFragment").commit();

        // final View controlsView =
        // findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        //DIALOG



        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        contentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
//        mSystemUiHider = SystemUiHider.getInstance(this, contentView,
//                HIDER_FLAGS);
//        mSystemUiHider.setup();
//        mSystemUiHider
//                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
//                    // Cached values.
//                    int mControlsHeight;
//                    int mShortAnimTime;
//
//                    @Override
//                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//                    public void onVisibilityChange(boolean visible) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//                            // If the ViewPropertyAnimator API is available
//                            // (Honeycomb MR2 and later), use it to animate the
//                            // in-layout UI controls at the bottom of the
//                            // screen.
//                            if (mControlsHeight == 0) {
//                                // mControlsHeight = controlsView.getHeight();
//                            }
//                            if (mShortAnimTime == 0) {
//                                mShortAnimTime = getResources().getInteger(
//                                        android.R.integer.config_shortAnimTime);
//                            }
//                            // controlsView
//                            // .animate()
//                            // .translationY(visible ? 0 : mControlsHeight)
//                            // .setDuration(mShortAnimTime);
//                        } else {
//                            // If the ViewPropertyAnimator APIs aren't
//                            // available, simply show or hide the in-layout UI
//                            // controls.
//                            // controlsView.setVisibility(visible ? View.VISIBLE
//                            // : View.GONE);
//                        }
//
//                        if (visible && AUTO_HIDE) {
//                            // Schedule a hide().
//                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
//                        }
//                    }
//                });

        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        // findViewById(R.id.dummy_button).setOnTouchListener(
        // mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls

    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */


    @Override
    public void onButtonClicked(String buttonId) {
        // TODO Auto-generated method stub
        MapFragment map = (MapFragment) getSupportFragmentManager()
                .findFragmentByTag("mapFragment");

        if (map != null) {
            map.update(buttonId);
        } else {
            Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                    "fragment not found we are fucked");
        }

    }

    @Override
    public void onUnitSelected(BaseUnit unit) {
        MapUiFragment ui = (MapUiFragment) getSupportFragmentManager()
                .findFragmentByTag("mapUiFragment");
        if (ui != null) {
            ui.updateUnitSelected(unit);
        } else {
            Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                    "fragment not found we are fucked");
        }

        // TODO Auto-generated method stub

    }

    @Override
    public void onTerrainSelected(BaseTerrain terrain) {
        MapUiFragment ui = (MapUiFragment) getSupportFragmentManager()
                .findFragmentByTag("mapUiFragment");
        if (ui != null) {
            ui.updateTerrainSelected(terrain);
        } else {
            Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                    "fragment not found we are fucked");
        }

        // TODO Auto-generated method stub

    }

    @Override
    public void getMessageToUi(String message) {
        MapUiFragment ui = (MapUiFragment) getSupportFragmentManager()
                .findFragmentByTag("mapUiFragment");
        if (ui != null) {
            ui.updateMessage(message);
        } else {
            Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                    "fragment not found we are fucked");
        }

    }

    @Override
    protected void onPause() {
        MapFragment map = (MapFragment) getSupportFragmentManager()
                .findFragmentByTag("mapFragment");
        MapUiFragment ui = (MapUiFragment) getSupportFragmentManager()
                .findFragmentByTag("mapUiFragment");
        if (map != null&&ui!=null) {
            map.onPause();
            ui.onPause();
        } else {
            Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                    "fragment not found we are fucked");
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MapFragment map = (MapFragment) getSupportFragmentManager()
                .findFragmentByTag("mapFragment");
        MapUiFragment ui = (MapUiFragment) getSupportFragmentManager()
                .findFragmentByTag("mapUiFragment");
        if (map != null&&ui!=null) {
            map.onResume();
            ui.onResume();
        } else {
            Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                    "fragment not found we are fucked");
        }

    }

}
