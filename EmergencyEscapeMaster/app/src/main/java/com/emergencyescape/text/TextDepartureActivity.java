package com.emergencyescape.text;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.emergencyescape.itinerary.ItineraryActivity;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextDepartureActivity extends CommonBehaviourActivity<TexterView,TextPresenter> {

    // Refactoring attuato:
    // - Old Class: EmTextActivity
    // - Merge con NoemTextActivityOld
    // - Verifica emergenza tramite boolean (emergencyState)


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editPartenza)
    TextView aulaPartenzaTextView;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link TextPresenter} for this view
     */
    @NonNull
    @Override
    public TextPresenter createPresenter() {
        return new TextPresenter();
    }

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
