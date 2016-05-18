package com.emergencyescape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextDepartureActivity extends CommonBehaviourActivity {

    // Refactoring attuato:
    // - Old Class: EmTextActivity
    // - Merge con NoemTextActivityOld
    // - Verifica emergenza tramite boolean (emergencyState)


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editPartenza) TextView aulaPartenzaTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure_text);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @OnClick(R.id.btnPartenza)
    public void submitDeparture(){
        if (this.getEmergencyState()) {
            startActivity(new Intent(TextDepartureActivity.this, ItineraryActivity.class));
        }else{
            startActivity(new Intent(TextDepartureActivity.this,TextDestinationActivity.class).putExtra("aula",this.getAula()));
        }
    }

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }

    private String getAula(){
        String aulaPartenza = aulaPartenzaTextView.getText().toString();
        return aulaPartenza;
    }
}
