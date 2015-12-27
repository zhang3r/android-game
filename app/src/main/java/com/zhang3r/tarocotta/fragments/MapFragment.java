package com.zhang3r.tarocotta.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhang3r.tarocotta.R;
import com.zhang3r.tarocotta.constants.ILogConstants;
import com.zhang3r.tarocotta.model.ViewTransferDTO;
import com.zhang3r.tarocotta.model.tiles.terrain.BaseTerrain;
import com.zhang3r.tarocotta.model.tiles.units.BaseUnit;
import com.zhang3r.tarocotta.views.MapView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p/>
 * to handle interaction events.
 * Use the {@link MapFragment#} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private static OnMapListener mCallback;


    public static void onDTOUpdate(ViewTransferDTO dto) {
        if (dto != null && mCallback != null) {
            mCallback.onDTOUpdate(dto);
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

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void update(String s) {

        MapView view = (MapView) getView().findViewById(R.id.mapView);
        view.updateFromFragemnt(s);
        Log.d(ILogConstants.BUTTON_TAG, s
                + " click transferred to MapFragment!!! woot!!!");

    }

    // Container Activity must implement this interface
    public interface OnMapListener {
        public void onDTOUpdate(ViewTransferDTO dto);
    }


}
