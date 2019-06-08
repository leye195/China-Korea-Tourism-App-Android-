// Generated code from Butter Knife. Do not modify!
package com.example.leeyoungjae.fragmentapp.Activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.leeyoungjae.fragmentapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131296534;

  private View view2131296494;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.mEmailView = Utils.findRequiredViewAsType(source, R.id.email, "field 'mEmailView'", AutoCompleteTextView.class);
    target.mPasswordView = Utils.findRequiredViewAsType(source, R.id.password, "field 'mPasswordView'", EditText.class);
    view = Utils.findRequiredView(source, R.id.sign_in_button, "field 'SignInButton' and method 'SignInClicked'");
    target.SignInButton = Utils.castView(view, R.id.sign_in_button, "field 'SignInButton'", Button.class);
    view2131296534 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.SignInClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.register_button, "field 'RegisterButton' and method 'register_Clicked'");
    target.RegisterButton = Utils.castView(view, R.id.register_button, "field 'RegisterButton'", Button.class);
    view2131296494 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.register_Clicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEmailView = null;
    target.mPasswordView = null;
    target.SignInButton = null;
    target.RegisterButton = null;

    view2131296534.setOnClickListener(null);
    view2131296534 = null;
    view2131296494.setOnClickListener(null);
    view2131296494 = null;
  }
}
