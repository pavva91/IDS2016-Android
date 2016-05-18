package com.emergencyescape.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.emergencyescape.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }



}
