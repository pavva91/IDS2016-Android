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
public class MainActivityFragment extends Fragment {


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_main, container, false);

        Button emergencyBtn = (Button) myView.findViewById(R.id.emergencyBtn); //PORCO DIO DI BOTTONE
        emergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //chiama la main direttamente senza fare l'autenticazione
                Intent intent = new Intent(getActivity(), EmergencyActivity.class);
                startActivity(intent);
            }
        });

        Button noEmergencyBtn = (Button) myView.findViewById(R.id.noEmergencyBtn);
        noEmergencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //chiama la main direttamente senza fare l'autenticazione
                Intent intent = new Intent(getActivity(), NoEmergencyActivity.class);
                startActivity(intent);
            }
        });

        return myView;
    }

}
