package com.zhang3r.tarocotta.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.fragments.MapFragment;
import com.zhang3r.tarocotta.fragments.MapUiFragment;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see com.zhang3r.tarocotta.util.SystemUiHider
 */
public class FullscreenActivity extends FragmentActivity implements
        MapFragment.OnMapListener, MapUiFragment.OnButtonClickListener {

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        // setContentView(R.layout.activity_fullscreen);

        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.

//        ViewGroup vg = new MapViewGroup(this);
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

        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls

    }


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
        if (map != null && ui != null) {
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
        if (map != null && ui != null) {
            map.onResume();
            ui.onResume();
        } else {
            Log.d(ILogConstants.SYSTEM_ERROR_TAG,
                    "fragment not found we are fucked");
        }

    }

}
