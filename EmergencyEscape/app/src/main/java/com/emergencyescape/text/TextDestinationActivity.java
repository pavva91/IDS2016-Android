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

public class TextDestinationActivity extends CommonBehaviourActivity<TexterView,TextPresenter> {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txtPartenza) TextView partenzaTextView;

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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setPartenza();

       // this.setQuota();
    }

    @OnClick(R.id.btnDestinazione)
    public void submitDestination(){
        startActivity(new Intent(TextDestinationActivity.this, ItineraryActivity.class));
    }

    private String getPartenza(){
        return getIntent().getStringExtra("aula");
    }

    private void setPartenza(){
        partenzaTextView.setText(getResources().getString(R.string.departure_room) + ": " + this.getPartenza());
    }

    /*
    private String getQuota(){
        return getIntent().getStringExtra("quota");
    }

    private void setQuota(){
        quotaT.setText(getResources().getString(R.string.quote)+ ": " + String.valueOf(this.getQuota()));
    */

}
