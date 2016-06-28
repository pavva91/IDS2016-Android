package com.emergencyescape.text;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

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
        // TODO: Aggiungere validation input utente
        String editDepartureText = this.getAula();
        if (editDepartureText.equals("error")||editDepartureText.equals("")){
            Toast.makeText(this, getResources().getString(R.string.wrong_text_input), Toast.LENGTH_SHORT).show();
            aulaPartenzaTextView.setText("");
        }else {
            Intent intentToStart;
            if (this.getEmergencyState()) {
                intentToStart = new Intent(TextDepartureActivity.this, ItineraryActivity.class);
            } else {
                intentToStart = new Intent(TextDepartureActivity.this, TextDestinationActivity.class);
            }
            presenter.setUserDeparture(this.getAula());
            startActivity(intentToStart);
        }
    }

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }

    private String getAula(){
        aulaPartenzaTextView.performValidation();
        String departure = aulaPartenzaTextView.getText().toString();
        return departure;
    }

    private void fillAutoCompleteTextView(){
        ArrayList<String> nodes = presenter.getNodesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_multichoice, nodes);

        // TODO: Diminuire font select_dialog_multichoice
        aulaPartenzaTextView.setThreshold(1);
        aulaPartenzaTextView.setAdapter(adapter);
        aulaPartenzaTextView.setValidator(new AutoCompleteTextView.Validator(){
            private ArrayList<String> nodesList = presenter.getNodesList();
            @Override
            public boolean isValid (CharSequence text){
                //some logic here returns true or false based on if the text is validated

                for (String node : nodesList){
                    if(node.equals(text.toString())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public CharSequence fixText (CharSequence invalidText){
                boolean equalIgnoreCase = false;
                String newText = invalidText.toString();
                for (String node : nodesList){
                    if(node.equalsIgnoreCase(invalidText.toString())) {
                        newText = node;
                        equalIgnoreCase = true;
                        break;
                    }
                }
                if (!equalIgnoreCase) {
                    newText = "error";
                }
                return newText;
            }
        });
    }


}
