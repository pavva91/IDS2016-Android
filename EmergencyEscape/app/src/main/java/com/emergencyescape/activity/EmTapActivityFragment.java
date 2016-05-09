package com.emergencyescape.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emergencyescape.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EmTapActivityFragment extends Fragment {

    public EmTapActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emtap, container, false);
    }
}
