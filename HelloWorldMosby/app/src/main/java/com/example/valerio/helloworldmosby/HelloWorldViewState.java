package com.example.valerio.helloworldmosby;
/**
 * Created by Valerio Mattioli on 16/05/2016.
 */

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;

/**
 * com.example.valerio.helloworldmosby
 * HelloWorldViewState
 */
public class HelloWorldViewState implements RestorableViewState<HelloWorldView> {
    // Variabili in cui si salverà lo stato della view
    private final String KEY_TEXT = "MyCustomViewState-flag";
    private final String KEY_COLOR = "MyCustomViewState-data";

    public String greetingText;
    public String color;

    @Override public void saveInstanceState(Bundle out) {  // Salvo lo stato della view
        out.putString(KEY_TEXT, greetingText);
        out.putString(KEY_COLOR, color);
    }

    @Override public RestorableViewState restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }

        greetingText = in.getString(KEY_TEXT);
        color = in.getString(KEY_COLOR); // Riprende i dati della view precedentemente salvati
        return this;
    }


    //Azione da fare quando c'è il restoring dell'Activity (Rotazione Schermo)
    @Override public void apply(HelloWorldView view, boolean retained) {
        view.showGreeting(greetingText, color );
    }

    public void setGreetingText(String greetingText){
        this.greetingText = greetingText;
    }

    public void setGreetingColor(String color){
        this.color = color;
    }

}
