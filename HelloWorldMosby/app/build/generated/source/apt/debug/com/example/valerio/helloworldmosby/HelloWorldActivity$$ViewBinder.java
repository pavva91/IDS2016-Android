// Generated code from Butter Knife. Do not modify!
package com.example.valerio.helloworldmosby;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HelloWorldActivity$$ViewBinder<T extends HelloWorldActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492975, "field 'greetingTextView'");
    target.greetingTextView = finder.castView(view, 2131492975, "field 'greetingTextView'");
    view = finder.findRequiredView(source, 2131492976, "method 'onHelloButtonClicked'");
    unbinder.view2131492976 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onHelloButtonClicked();
      }
    });
    view = finder.findRequiredView(source, 2131492977, "method 'onGoodbyeButtonClicked'");
    unbinder.view2131492977 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onGoodbyeButtonClicked();
      }
    });
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends HelloWorldActivity> implements Unbinder {
    private T target;

    View view2131492976;

    View view2131492977;

    protected InnerUnbinder(T target) {
      this.target = target;
    }

    @Override
    public final void unbind() {
      if (target == null) throw new IllegalStateException("Bindings already cleared.");
      unbind(target);
      target = null;
    }

    protected void unbind(T target) {
      target.greetingTextView = null;
      view2131492976.setOnClickListener(null);
      view2131492977.setOnClickListener(null);
    }
  }
}
