package com.emergencyescape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

public class NoemTextActivityOld extends CommonBehaviourActivity {


    public void sendInfo(View view) {
        Intent intent = new Intent(this, DestinationActivity.class);
        EditText editText = (EditText) findViewById(R.id.editPartenza);
        String message = editText.getText().toString();
        intent.putExtra("aula", message);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noemtext);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }







}
