package com.example.valerio.helloworldmosby;
/**
 * Created by Valerio Mattioli on 11/05/2016.
 */

import com.example.valerio.helloworldmosby.business_logic.GreetingGeneratorTask;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * com.example.valerio.helloworldmosby
 * HelloWorldPresenter
 */

// The presenter that coordinates HelloWorldView and business logic (GreetingGeneratorTask)
class HelloWorldPresenter extends MvpBasePresenter<HelloWorldView> {

    // Greeting Task is "business logic"
    private GreetingGeneratorTask greetingTask;

    private void cancelGreetingTaskIfRunning(){
        if (greetingTask != null){
            greetingTask.cancel(true);
        }
    }

    public void greetHello(){
        cancelGreetingTaskIfRunning();

        greetingTask = new GreetingGeneratorTask("Hello", new GreetingGeneratorTask.GreetingTaskListener(){
            public void onGreetingGenerated(String greetingText){
                if (isViewAttached())
                    getView().showHello(greetingText);
            }
        });
        greetingTask.execute();
    }

    public void greetGoodbye(){
        cancelGreetingTaskIfRunning();

        greetingTask = new GreetingGeneratorTask("Goodbye", new GreetingGeneratorTask.GreetingTaskListener(){
            public void onGreetingGenerated(String greetingText){
                if (isViewAttached())
                    getView().showGoodbye(greetingText);
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