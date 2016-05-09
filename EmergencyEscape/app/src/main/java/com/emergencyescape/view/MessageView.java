package com.emergencyescape.view;

/**
 * Created by Valerio Mattioli on 09/05/2016.
 */
public interface MessageView {
    // View methods should be directives, as the View is just executing orders from the
    // Presenter.

    // Methods for updating the view
    void setMessageBody(String body);
    void setAuthorName(String name);
    void showTranslationButton(boolean shouldShow);

    // Navigation methods
    void goToUserProfile(User user);
}
    