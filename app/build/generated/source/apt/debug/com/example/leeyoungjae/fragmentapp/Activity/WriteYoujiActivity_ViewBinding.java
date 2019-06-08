// Generated code from Butter Knife. Do not modify!
package com.example.leeyoungjae.fragmentapp.Activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.leeyoungjae.fragmentapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WriteYoujiActivity_ViewBinding implements Unbinder {
  private WriteYoujiActivity target;

  private View view2131296489;

  private View view2131296490;

  private View view2131296285;

  private View view2131296527;

  @UiThread
  public WriteYoujiActivity_ViewBinding(WriteYoujiActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WriteYoujiActivity_ViewBinding(final WriteYoujiActivity target, View source) {
    this.target = target;

    View view;
    target.iv_photo = Utils.findRequiredViewAsType(source, R.id.head_img, "field 'iv_photo'", ImageView.class);
    target.iv_text = Utils.findRequiredViewAsType(source, R.id.add_head, "field 'iv_text'", TextView.class);
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.radiogroup, "field 'radioGroup'", RadioGroup.class);
    view = Utils.findRequiredView(source, R.id.rbtn1, "field 'rbtn1' and method 'select_place'");
    target.rbtn1 = Utils.castView(view, R.id.rbtn1, "field 'rbtn1'", RadioButton.class);
    view2131296489 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.select_place(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rbtn2, "field 'rbtn2' and method 'select_place'");
    target.rbtn2 = Utils.castView(view, R.id.rbtn2, "field 'rbtn2'", RadioButton.class);
    view2131296490 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.select_place(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.add_sub_photo, "field 'select_subbtn' and method 'onAddSubPicture'");
    target.select_subbtn = Utils.castView(view, R.id.add_sub_photo, "field 'select_subbtn'", Button.class);
    view2131296285 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddSubPicture();
      }
    });
    view = Utils.findRequiredView(source, R.id.send_btn, "field 'sendbtn' and method 'send_clicked2'");
    target.sendbtn = Utils.castView(view, R.id.send_btn, "field 'sendbtn'", FloatingActionButton.class);
    view2131296527 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.send_clicked2();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WriteYoujiActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_photo = null;
    target.iv_text = null;
    target.radioGroup = null;
    target.rbtn1 = null;
    target.rbtn2 = null;
    target.select_subbtn = null;
    target.sendbtn = null;

    ((CompoundButton) view2131296489).setOnCheckedChangeListener(null);
    view2131296489 = null;
    ((CompoundButton) view2131296490).setOnCheckedChangeListener(null);
    view2131296490 = null;
    view2131296285.setOnClickListener(null);
    view2131296285 = null;
    view2131296527.setOnClickListener(null);
    view2131296527 = null;
  }
}
