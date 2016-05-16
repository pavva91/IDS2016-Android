package com.example.valerio.helloworldmosby;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

public class HelloWorldActivity extends MvpViewStateActivity<HelloWorldView, HelloWorldPresenter>
        implements HelloWorldView {

    //ELEMENTI DEL LAYOUT SU CUI ANDRO' A LAVORARE:
    @BindView(R.id.greetingTextView) TextView greetingTextView;



    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_hello_world); // Associo layout .xml

        ButterKnife.bind(this);  // ButterKnife statement
    }

    @Override
    public HelloWorldViewState createViewState() { // Associo la ViewState all'Activity
        return new HelloWorldViewState();
    }

    @Override
    public void onNewViewStateInstance() { // Comportamento all'apertura (quando ancora non vi sono state interazioni)
        //presenter.greetHello();
    }

    @Override // Associo Presenter all'Activity
    public HelloWorldPresenter createPresenter(){
        return new HelloWorldPresenter();
    }

    @Override
    public void showGreeting(String greetingText, String color){

        HelloWorldViewState vs = ((HelloWorldViewState) viewState); // Creo la ViewState

        vs.setGreetingText(greetingText); // Salvo i valori nella ViewState
        vs.setGreetingColor(color);

        // STAMPO SULLA VIEW:
        if (color == "RED") {
            greetingTextView.setTextColor(Color.parseColor(color));
        }
        else if (color == "BLUE") {
            greetingTextView.setTextColor(Color.parseColor(color));
        }

        greetingTextView.setText(greetingText);
        // FINE STAMPA SULLA VIEW
    }

    // EVENTI INTERAZIONI UTENTE
    @OnClick(R.id.helloButton)
    public void onHelloButtonClicked(){
        presenter.greetHello();
    }

    @OnClick(R.id.goodbyeButton)
    public void onGoodbyeButtonClicked(){
        presenter.greetGoodbye();
    }
    // FINE EVENTI INTERAZIONI UTENTE






}