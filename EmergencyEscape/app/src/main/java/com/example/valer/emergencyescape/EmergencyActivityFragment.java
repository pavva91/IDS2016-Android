package com.example.valer.emergencyescape;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class EmergencyActivityFragment extends Fragment {

    public EmergencyActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_input_type, container, false);

        Button txtBtn = (Button) myView.findViewById(R.id.txtBtn); //PORCO DIO DI BOTTONE
        txtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //chiama la main direttamente senza fare l'autenticazione
                Intent intent = new Intent(getActivity(), TextActivity.class);
                startActivity(intent);
            }
        });

        Button tapBtn = (Button) myView.findViewById(R.id.tapBtn);
        tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //chiama la main direttamente senza fare l'autenticazione
                Intent intent = new Intent(getActivity(), TapActivity.class);
                startActivity(intent);
            }
        });

        Button qrBtn = (Button) myView.findViewById(R.id.qrBtn);
        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //chiama la main direttamente senza fare l'autenticazione
                Intent intent = new Intent(getActivity(), QrActivity.class);
                startActivity(intent);
            }
        });

        return myView;
    }
}
