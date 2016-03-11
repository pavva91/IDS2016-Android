package com.example.valer.emergencyescape;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class TextNoEmergencyActivityFragment extends Fragment {

    public TextNoEmergencyActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_text, container, false);

/*
        Button myButton = (Button) myView.findViewById(R.id.btnPartenza);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("EditText", "Cliccato stocazzo porcamadonna non fa");

                EditText myEdit = (EditText) v.findViewById(R.id.editPartenza);

                String partenza = myEdit.getText().toString();

                Log.v("EditText", partenza);

                Intent intent = new Intent(getActivity(), DestinationActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,partenza);
                startActivity(intent);
            }
        });*/

        Button tapBtn = (Button) myView.findViewById(R.id.btnPartenza);
        tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DestinationActivity.class);
                startActivity(intent);
            }
        });


        return myView;
    }
}
