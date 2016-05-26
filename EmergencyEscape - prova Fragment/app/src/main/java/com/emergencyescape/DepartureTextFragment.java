package com.emergencyescape;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class DepartureTextFragment extends Fragment implements View.OnClickListener {

    public DepartureTextFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_departure_text, container, false);
    }


    /*TODO: Fare onclick sul button e mandare a chi di dovere (verifica se emergency o no)
    @Override
    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState) {
        View myView = inflater.inflate(R.layout.fragment_1, container, false);
        myButton = (Button) myView.findViewById(R.id.myButton);
        myButton.setOnClickListener(this);
        return myView;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        ButterKnife.bind(this, view);

    }

    @OnClick(R.id.btnPartenza)
    public void submit(View view) {
        // TODO submit data to server...
        getActivity().startActivity(new Intent(getActivity(), ItineraryActivity.class));
    }

    */

    @Override
    public void onClick(View v) {

    }
}
