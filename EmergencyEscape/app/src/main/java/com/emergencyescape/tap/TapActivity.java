package com.emergencyescape.tap;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


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
    @BindView(R.id.floorListView) ListView floorListView;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        ButterKnife.bind(this);

        populateFloorListView(getFloorList());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public List<String> getFloorList(){
        return presenter.getFloorList();
    }

    public void populateFloorListView(List<String> floorList){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                floorList);
        floorListView.setAdapter(adapter);
    }

    @OnItemClick(R.id.floorListView)
    public void floorListViewItemClick(int position){
        Intent intentToStart = new Intent(this, MapActivity.class);
        intentToStart.putExtra("floor",floorListView.getItemAtPosition(position).toString());
        intentToStart.putExtra("id",R.drawable.q145);
        intentToStart.putExtra("emergencyState",getEmergencyState());
        startActivity(intentToStart);
    }

    private Boolean getEmergencyState(){
        Boolean emergencyState = getIntent().getBooleanExtra("emergencyState",true);
        return emergencyState;
    }
}