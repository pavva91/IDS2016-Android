package com.example.valerio.helloworldmosby;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Valerio Mattioli on 11/05/2016.
 */
// View interface
interface HelloWorldView extends MvpView {

    // displays "Hello" greeting text in red text color
    void showHello(String greetingText);

    // displays "Goodbye" greeting text in blue text color
    void showGoodbye(String greetingText);
}