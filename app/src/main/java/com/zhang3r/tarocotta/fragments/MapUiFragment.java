package com.zhang3r.tarocotta.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.zhang3r.tarocotta.Gesture.OnSwipeTouchListener;
import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.constants.IButtonConstants;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.model.ViewTransferDTO;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.views.MapUiView;

public class MapUiFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    OnButtonClickListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflator.inflate(R.layout.fragment_map_ui, container, false);
        Button attack = (Button) view.findViewById(R.id.btn_attack);
        attack.setOnClickListener(this);

        Button wait = (Button) view.findViewById(R.id.btn_wait);
        wait.setOnClickListener(this);
        Button cancel = (Button) view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);
        Button endTurn = (Button) view.findViewById(R.id.btn_endTurn);
        endTurn.setOnClickListener(this);
//
//
//        final ViewFlipper vf = (ViewFlipper) view.findViewById(R.id.viewFlipper);
//        Button left = (Button) view.findViewById(R.id.buttonLeft);
//        Button right = (Button) view.findViewById(R.id.buttonRight);
//        Button left2 = (Button) view.findViewById(R.id.buttonLeft2);
//        Button right2 = (Button) view.findViewById(R.id.buttonRight2);
//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vf.showPrevious();
//            }
//        });
//        right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vf.showNext();
//            }
//        });
//        left2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vf.showPrevious();
//            }
//        });
//        right2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vf.showNext();
//            }
//        });
//
//
//        vf.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
//            public void onSwipeTop() {
//                Log.d(ILogConstants.GESTURE_TAG, "SWIP TOP");
//            }
//
//            public void onSwipeRight() {
//                Log.d(ILogConstants.GESTURE_TAG, "SWIP RIGHT");
//                vf.showNext();
//            }
//
//            public void onSwipeLeft() {
//                Log.d(ILogConstants.GESTURE_TAG, "SWIP Left");
//                vf.showPrevious();
//            }
//
//            public void onSwipeBottom() {
//                Log.d(ILogConstants.GESTURE_TAG, "SWIP DOWN");
//
//            }
//
//        });
           return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_attack:

                mCallback.onButtonClicked(IButtonConstants.attack);
                break;

            case R.id.btn_wait:

                mCallback.onButtonClicked(IButtonConstants.wait);
                break;

            case R.id.btn_cancel:
                mCallback.onButtonClicked(IButtonConstants.cancel);
                break;
            case R.id.btn_endTurn:

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

    public void updateUI(final ViewTransferDTO dto) {
        //TODO


    }





    // Container Activity must implement this interface
    public interface OnButtonClickListener {
        public void onButtonClicked(String buttonId);
    }


    public void update(final BaseUnit unit, View view){
        /************************************ LABEL POPULATION **************************************/
//        TextView attackStatText = (TextView) view.findViewById(R.id.attackStatText);
//        TextView attackRangeStatText = (TextView) view.findViewById(R.id.attackRangeStatText);
//        TextView defenseStatText = (TextView) view.findViewById(R.id.defenseStatText);
//        TextView moveStatText = (TextView) view.findViewById(R.id.movePointStatText);
//        TextView hpBarText = (TextView) view.findViewById(R.id.progressBarText);
//        TextView unitName = (TextView) view.findViewById(R.id.unitName);
//        TextView unitStatus = (TextView) view.findViewById(R.id.unitStatusText);
//        TextView unitType = (TextView) view.findViewById(R.id.unitTypeText);
//        ImageView image = (ImageView) view.findViewById(R.id.unitImage);
//        ProgressBar hpBar = (ProgressBar) view.findViewById(R.id.HPBar);
//        TextView description = (TextView) view.findViewById(R.id.descriptionText);
//
//        if (unit == null){
//            attackStatText.setText("");
//            attackRangeStatText.setText("");
//            defenseStatText.setText("");
//            moveStatText.setText("");
//            hpBarText.setText("");
//            hpBar.setProgress(0);
//            unitName.setText("");
//            unitType.setText("");
//            unitStatus.setText("");
//            description.setText("");
//            image.setImageBitmap(null);
//        }else {
////            attackStatText.setText("" + unit.getStats().getAttack());
////            attackRangeStatText.setText("" + unit.getMaxAttackRange());
////            defenseStatText.setText("" + unit.getDefense());
////            moveStatText.setText("" + unit.getMovePoints());
////            hpBarText.setText("" + unit.getHitPoints() + "/" + unit.getMaxHP());
////            hpBar.setProgress((unit.getHitPoints() * 100) / unit.getMaxHP());
////            unitName.setText(unit.getName());
////            image.setImageBitmap(unit.getSprite().getAnimation());
////            unitType.setText(unit.getUnitType().toString().toLowerCase());
////            unitStatus.setText(unit.getState().toString().toLowerCase());
////            description.setText(unit.toString());
//        }

    }
}

