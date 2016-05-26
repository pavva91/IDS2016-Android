package com.emergencyescape.presenter;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by Valerio Mattioli on 09/05/2016.
 *
 * IMPORTANTE: Nel Presenter ci deve essere la Business logic puramente in Java
 * senza nessun riferimento alle classi del framework Android
 */
public abstract class BasePresenter<M, V> {
    protected M model;
    private WeakReference<V> view;

    public void setModel(M model) {
        resetState();
        this.model = model;
        if (setupDone()) {
            updateView();
        }
    }

    protected void resetState() {
    }

    public void bindView(@NonNull V view) {
        this.view = new WeakReference<>(view);
        if (setupDone()) {
            updateView();
        }
    }

    public void unbindView() {
        this.view = null;
    }

    protected V view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    protected abstract void updateView();

    protected boolean setupDone() {
        return view() != null && model != null;
    }
}
