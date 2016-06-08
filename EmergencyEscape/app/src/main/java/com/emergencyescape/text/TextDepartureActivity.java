package com.emergencyescape.text;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.emergencyescape.itinerary.ItineraryActivity;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextDepartureActivity extends CommonBehaviourActivity<TexterView,TextDeparturePresenter> {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editPartenza) AutoCompleteTextView aulaPartenzaTextView;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link TextDeparturePresenter} for this view
     */
    @NonNull
    @Override
    public TextDeparturePresenter createPresenter() {
        return new TextDeparturePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure_text);

        ButterKnife.bind(this);

        fillAutoCompleteTextView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @OnClick(R.id.btnPartenza)
    public void submitDeparture(){
        Intent intentToStart;
        if (this.getEmergencyState()) {
            intentToStart = new Intent(TextDepartureActivity.this, ItineraryActivity.class);
        }else{
            intentToStart = new Intent(TextDepartureActivity.this,TextDestinationActivity.class);
        }
        presenter.setUserDeparture(this.getAula());
        startActivity(intentToStart);
    }

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }

    private String getAula(){
        return aulaPartenzaTextView.getText().toString();
    }

    private void fillAutoCompleteTextView(){
        ArrayList<String> nodes = presenter.getNodesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_multichoice, nodes);

        aulaPartenzaTextView.setThreshold(1);
        aulaPartenzaTextView.setAdapter(adapter);
    }


}
