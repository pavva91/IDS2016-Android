package com.emergencyescape.presenter;

/**
 * Created by Valerio Mattioli on 09/05/2016.
 */
public interface MessagePresenter {
    // Presenter methods should mostly be callbacks, as the View is reporting events for the
    // Presenter to evaluate

    // Lifecycle events methods
    void onStart();

    // Input events methods
    void onAuthorClicked();
    void onThreeFingersSwipe();
}
