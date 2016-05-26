package com.example.valerio.helloworldmosby.business_logic;
/**
 * Created by Valerio Mattioli on 11/05/2016.
 */

import android.os.AsyncTask;

/**
 * com.example.valerio.helloworldmosby
 * GreetingGeneratorTask
 */

// "Business logic" component
public class GreetingGeneratorTask extends AsyncTask<Void, Void, Integer> {

    // Callback - Listener
    public interface GreetingTaskListener{
        void onGreetingGenerated(String greetingText);
    }

    private String baseText;
    private GreetingTaskListener listener;

    public GreetingGeneratorTask(String baseText, GreetingTaskListener listener){
        this.baseText = baseText;
        this.listener = listener;
    }

    // returns a random integer
    @Override
    protected Integer doInBackground(Void... params) {
        return (int) (Math.random() * 100);
    }

    @Override
    protected void onPostExecute(Integer randomInt){
<<<<<<< HEAD
        listener.onGreetingGenerated(baseText + " "+randomInt);
=======
        listener.onGreetingGenerated(baseText + " "  + randomInt);
>>>>>>> valerio
    }
}