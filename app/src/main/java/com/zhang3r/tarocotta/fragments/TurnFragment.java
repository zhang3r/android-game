package com.zhang3r.tarocotta.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang3r.tarocotta.R;

/**
 * Created by Zhang3r on 12/27/2015.
 */
public class TurnFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflator.inflate(R.layout.fragment_turn, container, false);
        return view;
    }

    public void updateTurn(int n){
        View view = (View) getView().findViewById(R.id.turnView);
        TextView t = (TextView) view.findViewById(R.id.turnText);
        t.setText(getString(R.string.turnText)+n);
    }
}
