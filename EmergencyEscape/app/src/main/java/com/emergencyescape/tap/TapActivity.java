package com.emergencyescape.tap;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TapActivity extends CommonBehaviourActivity<TapView,TapPresenter> {
    /**
     * Instantiate a presenter instance
     *
     * @return The {@link MvpPresenter} for this view
     */
    @NonNull
    @Override
    public TapPresenter createPresenter() {
        return new TapPresenter();
    }

    @BindView(R.id.toolbar) Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noemtap);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    



    public void onClick(View v)
    {

        // TODO: rendere questa lista dinamica e fare riferimento al Model usando ButterKnife

        int id = v.getId();

        if (id == R.id.q145)
        {
            Intent in = new Intent(this, MapTapActivity.class);
            in.putExtra("id", R.drawable.q145);
            in.putExtra("quota", "q145");
            startActivity(in);
        }

        if (id == R.id.q150)
        {
            Intent in = new Intent(this, MapTapActivity.class);
            in.putExtra("id", R.drawable.q150);
            in.putExtra("quota", "q150");
            startActivity(in);
        }

        if (id == R.id.q155)
        {
            Intent in = new Intent(this, MapTapActivity.class);
            in.putExtra("id", R.drawable.q155);
            in.putExtra("quota", "q155");
            startActivity(in);
        }

    }





    
    
}