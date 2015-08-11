package com.zhang3r.onelevelgame.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.zhang3r.onelevelgame.Gesture.OnSwipeTouchListener;
import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.constants.IButtonConstants;
import com.zhang3r.onelevelgame.constants.ILogConstants;
import com.zhang3r.onelevelgame.model.tiles.terrain.BaseTerrain;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;
import com.zhang3r.onelevelgame.model.tiles.units.InfantryUnit;
import com.zhang3r.onelevelgame.views.MapUiView;

public class MapUiFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    OnButtonClickListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflator.inflate(R.layout.fragment_map_ui, container, false);
        Button attack = (Button) view.findViewById(R.id.attack);
        attack.setOnClickListener(this);

        Button wait = (Button) view.findViewById(R.id.wait);
        wait.setOnClickListener(this);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        Button endTurn = (Button) view.findViewById(R.id.endTurn);
        endTurn.setOnClickListener(this);


        final ViewFlipper vf = (ViewFlipper) view.findViewById(R.id.viewFlipper);
        Button left = (Button) view.findViewById(R.id.buttonLeft);
        Button right = (Button) view.findViewById(R.id.buttonRight);
        Button left2 = (Button) view.findViewById(R.id.buttonLeft2);
        Button right2 = (Button) view.findViewById(R.id.buttonRight2);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.showPrevious();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.showNext();
            }
        });
        left2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.showPrevious();
            }
        });
        right2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vf.showNext();
            }
        });


        vf.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            public void onSwipeTop() {
                Log.d(ILogConstants.GESTURE_TAG, "SWIP TOP");
            }

            public void onSwipeRight() {
                Log.d(ILogConstants.GESTURE_TAG, "SWIP RIGHT");
                vf.showNext();
            }

            public void onSwipeLeft() {
                Log.d(ILogConstants.GESTURE_TAG, "SWIP Left");
                vf.showPrevious();
            }

            public void onSwipeBottom() {
                Log.d(ILogConstants.GESTURE_TAG, "SWIP DOWN");

            }

        });
           return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attack:

                mCallback.onButtonClicked(IButtonConstants.attack);
                break;

            case R.id.wait:

                mCallback.onButtonClicked(IButtonConstants.wait);
                break;

            case R.id.cancel:
                mCallback.onButtonClicked(IButtonConstants.cancel);
                break;
            case R.id.endTurn:

                mCallback.onButtonClicked(IButtonConstants.endTurn);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnButtonClickListener");
        }
    }

    public void updateUnitSelected(final BaseUnit unit) {
        // update unit info
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This code will always run on the UI thread, therefore is safe
                // to modify UI elements.
                MapUiView view = (MapUiView) getView()
                        .findViewById(R.id.uiView);
//                TextView name = (TextView) view.findViewById(R.id.unitName);
//                name.setText("Name: " + unit.getName());
//                TextView hp = (TextView) view.findViewById(R.id.hitPoints);
//                hp.setText("HP: " + unit.getHitPoints());

                update(unit, view);

            }
        });

    }

    public void updateMessage(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This code will always run on the UI thread, therefore is safe
                // to modify UI elements.
                MapUiView view = (MapUiView) getView()
                        .findViewById(R.id.uiView);
//                TextView actionText = (TextView) view
//                        .findViewById(R.id.unitAction);
//                actionText.setText(message);
            }
        });
    }

    public void updateTerrainSelected(final BaseTerrain terrain) {
        // update unit info
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This code will always run on the UI thread, therefore is safe
                // to modify UI elements.
                MapUiView view = (MapUiView) getView()
                        .findViewById(R.id.uiView);
//                TextView name = (TextView) view.findViewById(R.id.terrainName);
//                name.setText("terrain Name: " + terrain.getName());
//                TextView hp = (TextView) view
//                        .findViewById(R.id.terrainMovement);
//                hp.setText("Movement hinderance: " + terrain.getMovement());
//
//                TextView mp = (TextView) view
//                        .findViewById(R.id.terrainDefenseBonus);
//                mp.setText("defensive bonus: " + terrain.getDefense());
                //update(null, view);

            }
        });

    }



    // Container Activity must implement this interface
    public interface OnButtonClickListener {
        public void onButtonClicked(String buttonId);
    }


    public void update(final BaseUnit unit, View view){
        /************************************ LABEL POPULATION **************************************/
        TextView attackStatText = (TextView) view.findViewById(R.id.attackStatText);
        TextView attackRangeStatText = (TextView) view.findViewById(R.id.attackRangeStatText);
        TextView defenseStatText = (TextView) view.findViewById(R.id.defenseStatText);
        TextView moveStatText = (TextView) view.findViewById(R.id.movePointStatText);
        TextView hpBarText = (TextView) view.findViewById(R.id.progressBarText);
        TextView unitName = (TextView) view.findViewById(R.id.unitName);
        TextView unitStatus = (TextView) view.findViewById(R.id.unitStatusText);
        TextView unitType = (TextView) view.findViewById(R.id.unitTypeText);

        ProgressBar hpBar = (ProgressBar) view.findViewById(R.id.HPBar);
        TextView description = (TextView) view.findViewById(R.id.descriptionText);

        if (unit == null){
            attackStatText.setText("");
            attackRangeStatText.setText("");
            defenseStatText.setText("");
            moveStatText.setText("");
            hpBarText.setText("");
            hpBar.setProgress(0);
            unitName.setText("");
            unitType.setText("");
            unitStatus.setText("");
            description.setText("");
        }else {
            attackStatText.setText("" + unit.getAttack());
            attackRangeStatText.setText("" + unit.getMaxAttackRange());
            defenseStatText.setText("" + unit.getDefense());
            moveStatText.setText("" + unit.getMovePoints());
            hpBarText.setText("" + unit.getHitPoints() + "/" + unit.getMaxHP());
            hpBar.setProgress((unit.getHitPoints() * 100) / unit.getMaxHP());
            unitName.setText(unit.getName());
            unitType.setText(unit.getUnitType().toString().toLowerCase());
            unitStatus.setText(unit.getState().toString().toLowerCase());
            description.setText(unit.toString());
        }

    }
}

