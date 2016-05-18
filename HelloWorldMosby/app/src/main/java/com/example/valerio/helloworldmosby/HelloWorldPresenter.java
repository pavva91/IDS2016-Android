package com.example.valerio.helloworldmosby;
/**
 * Created by Valerio Mattioli on 11/05/2016.
 */

import android.accounts.Account;

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


    /*
    ESEMPIO DI JavaRx
     */
    public void doLogin(AuthCredentials credentials) {

        if (isViewAttached()) {
            getView().showLoading();
        }

        // Kind of "callback"
        cancelSubscription();
        subscriber = new Subscriber<Account>() {
            @Override public void onCompleted() {
                if (isViewAttached()) {
                    getView().loginSuccessful();
                }
            }

            @Override public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError();
                }
            }

            @Override public void onNext(Account account) {
                eventBus.post(new LoginSuccessfulEvent(account));
            }
        };

        // do the login
        accountManager.doLogin(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}