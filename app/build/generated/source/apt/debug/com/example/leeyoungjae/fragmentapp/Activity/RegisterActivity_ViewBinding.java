// Generated code from Butter Knife. Do not modify!
package com.example.leeyoungjae.fragmentapp.Activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.leeyoungjae.fragmentapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131296493;

  private View view2131296318;

  private View view2131296389;

  private View view2131296388;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.user_id = Utils.findRequiredViewAsType(source, R.id.user_id, "field 'user_id'", EditText.class);
    target.user_pw = Utils.findRequiredViewAsType(source, R.id.user_pw, "field 'user_pw'", EditText.class);
    target.user_pw_ag = Utils.findRequiredViewAsType(source, R.id.user_pw_ag, "field 'user_pw_ag'", EditText.class);
    target.gender = Utils.findRequiredViewAsType(source, R.id.gender_rg, "field 'gender'", RadioGroup.class);
    view = Utils.findRequiredView(source, R.id.regist, "field 'signUpBtn' and method 'signUpBtn'");
    target.signUpBtn = Utils.castView(view, R.id.regist, "field 'signUpBtn'", Button.class);
    view2131296493 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.signUpBtn();
      }
    });
    view = Utils.findRequiredView(source, R.id.check_valid, "field 'checkValid' and method 'checkValid'");
    target.checkValid = Utils.castView(view, R.id.check_valid, "field 'checkValid'", Button.class);
    view2131296318 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.checkValid();
      }
    });
    view = Utils.findRequiredView(source, R.id.gender_m, "method 'check_gender'");
    view2131296389 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.check_gender(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.gender_f, "method 'check_gender'");
    view2131296388 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.check_gender(p0, p1);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.user_id = null;
    target.user_pw = null;
    target.user_pw_ag = null;
    target.gender = null;
    target.signUpBtn = null;
    target.checkValid = null;

    view2131296493.setOnClickListener(null);
    view2131296493 = null;
    view2131296318.setOnClickListener(null);
    view2131296318 = null;
    ((CompoundButton) view2131296389).setOnCheckedChangeListener(null);
    view2131296389 = null;
    ((CompoundButton) view2131296388).setOnCheckedChangeListener(null);
    view2131296388 = null;
  }
}
