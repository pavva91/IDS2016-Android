package com.example.valerio.helloworldmosby;
/**
 * Created by Valerio Mattioli on 11/05/2016.
 */

import com.example.valerio.helloworldmosby.business_logic.GreetingGeneratorTask;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * com.example.valerio.helloworldmosby
 * HelloWorldPresenter
 * Colui che invoca l'AsyncTask (RxJava)
 */

// The presenter that coordinates HelloWorldView and business logic (GreetingGeneratorTask)
class HelloWorldPresenter extends MvpBasePresenter<HelloWorldView> {

    protected static final String HELLO_COLOR = "RED";
    protected static final String GOODBYE_COLOR = "BLUE";

    // Greeting Task is "business logic"
    private GreetingGeneratorTask greetingTask;
    protected String color;

    private void cancelGreetingTaskIfRunning(){
        if (greetingTask != null){
            greetingTask.cancel(true);
        }
    }

    public void greetHello(){
        cancelGreetingTaskIfRunning();

        color = HELLO_COLOR;

        greetingTask = new GreetingGeneratorTask("Hello", new GreetingGeneratorTask.GreetingTaskListener(){
            public void onGreetingGenerated(String greetingText){
                if (isViewAttached())
                    getView().showGreeting(greetingText,color);
            }
        });
        greetingTask.execute();
    }

    public void greetGoodbye(){
        cancelGreetingTaskIfRunning();

        color = GOODBYE_COLOR;

        greetingTask = new GreetingGeneratorTask("Goodbye", new GreetingGeneratorTask.GreetingTaskListener(){
            public void onGreetingGenerated(String greetingText){
                if (isViewAttached())
                    getView().showGreeting(greetingText, color);
            }
        });
        greetingTask.execute();
    }

    // Called when Activity gets destroyed, so cancel running background task
    public void detachView(boolean retainPresenterInstance){
        super.detachView(retainPresenterInstance);
        if (!retainPresenterInstance){
            cancelGreetingTaskIfRunning();
        }
    }
}