package com.emergencyescape.text;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.emergencyescape.itinerary.ItineraryActivity;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextDestinationActivity extends CommonBehaviourActivity<TexterView,TextPresenter> {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txtPartenza) TextView partenzaTextView;
    @BindView(R.id.editDestination) AutoCompleteTextView editDestination;

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
        setContentView(R.layout.activity_destination_text);

        ButterKnife.bind(this);

        fillAutoCompleteTextView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setPartenza();

       // this.setQuota();
    }

    @OnClick(R.id.btnDestinazione)
    public void submitDestination(){
        presenter.setUserDestination(this.getAula());
        startActivity(new Intent(TextDestinationActivity.this, ItineraryActivity.class));
    }

    private String getPartenza(){
        return presenter.getUserDeparture();
    }

    private void setPartenza(){
        partenzaTextView.setText(getResources().getString(R.string.departure_room) + ": " + this.getPartenza());
    }

    private void fillAutoCompleteTextView(){
        ArrayList<String> nodes = presenter.getNodesList();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, nodes);

        editDestination.setThreshold(1);
        editDestination.setAdapter(adapter);
    }

    private String getAula(){
        String destination = editDestination.getText().toString();
        return destination;
    }
}
