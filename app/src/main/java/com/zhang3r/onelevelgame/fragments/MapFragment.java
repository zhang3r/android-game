package com.zhang3r.onelevelgame.fragments;

import android.app.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhang3r.onelevelgame.R;
import com.zhang3r.onelevelgame.constants.ILogConstants;
import com.zhang3r.onelevelgame.model.tiles.terrain.BaseTerrain;
import com.zhang3r.onelevelgame.model.tiles.units.BaseUnit;
import com.zhang3r.onelevelgame.views.MapView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private static OnMapListener mCallback;

    public static void getUnitSelected(BaseUnit unit) {
        if(unit!=null&&mCallback!=null) {
            mCallback.onUnitSelected(unit);
        }

    }

    public static void getTerrainSelected(BaseTerrain terrain) {
        if(terrain!=null&&mCallback!=null) {
            mCallback.onTerrainSelected(terrain);
        }
    }

    public static void getMessageToUi(String message) {
        if(message!=null&&mCallback!=null) {
            mCallback.getMessageToUi(message);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflator.inflate(R.layout.map_view_group, container, false);
        View view = inflator.inflate(R.layout.fragment_map, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMapListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MapListener");
        }
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }








    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    public void update(String s) {

        MapView view = (MapView) getView().findViewById(R.id.mapView);
        view.updateFromFragemnt(s);
        Log.d(ILogConstants.BUTTON_TAG, s
                + " click transferred to MapFragment!!! woot!!!");

    }

    // Container Activity must implement this interface
    public interface OnMapListener {
        public void onUnitSelected(BaseUnit position);

        public void getMessageToUi(String message);

        void onTerrainSelected(BaseTerrain terrain);
    }



}
