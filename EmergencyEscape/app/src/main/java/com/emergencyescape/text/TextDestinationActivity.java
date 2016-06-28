package com.emergencyescape.text;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.emergencyescape.itinerary.ItineraryActivity;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextDestinationActivity extends CommonBehaviourActivity<TexterView,TextDestinationPresenter> {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txtPartenza) TextView partenzaTextView;
    @BindView(R.id.editDestination) AutoCompleteTextView editDestination;

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link TextDeparturePresenter} for this view
     */
    @NonNull
    @Override
    public TextDestinationPresenter createPresenter() {
        return new TextDestinationPresenter();
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
    }

    @OnClick(R.id.btnDestinazione)
    public void submitDestination(){
        String editDestinationText = this.getAula();
        if (editDestinationText.equals("error")||editDestinationText.equals("")){
            Toast.makeText(this, getResources().getString(R.string.wrong_text_input), Toast.LENGTH_SHORT).show();
            editDestination.setText("");
        }else {
            presenter.setUserDestination(editDestinationText);
            startActivity(new Intent(TextDestinationActivity.this, ItineraryActivity.class).putExtra("emergencyState", false));
        }
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
        editDestination.setValidator(new AutoCompleteTextView.Validator(){
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

    private String getAula(){
        editDestination.performValidation();
        String destination = editDestination.getText().toString();
        return destination;
    }

}
