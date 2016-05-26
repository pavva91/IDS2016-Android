package com.example.valerio.helloworldmosby;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
<<<<<<< HEAD
import com.hannesdorfmann.mosby.mvp.MvpActivity;;
=======
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
>>>>>>> valerio
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

<<<<<<< HEAD
public class HelloWorldActivity extends MvpActivity<HelloWorldView, HelloWorldPresenter>
        implements HelloWorldView {

    @BindView(R.id.greetingTextView) TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_hello_world);

        ButterKnife.bind(this);
    }

    @Override // Called internally by Mosby
=======
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
>>>>>>> valerio
    public HelloWorldPresenter createPresenter(){
        return new HelloWorldPresenter();
    }

<<<<<<< HEAD
=======
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
>>>>>>> valerio
    @OnClick(R.id.helloButton)
    public void onHelloButtonClicked(){
        presenter.greetHello();
    }

    @OnClick(R.id.goodbyeButton)
    public void onGoodbyeButtonClicked(){
        presenter.greetGoodbye();
    }
<<<<<<< HEAD

    @Override
    public void showHello(String greetingText){
        greetingTextView.setTextColor(Color.RED);
        greetingTextView.setText(greetingText);
    }

    @Override
    public void showGoodbye(String greetingText){
        greetingTextView.setTextColor(Color.BLUE);
        greetingTextView.setText(greetingText);
    }
=======
    // FINE EVENTI INTERAZIONI UTENTE






>>>>>>> valerio
}