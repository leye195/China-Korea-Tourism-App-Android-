// Generated code from Butter Knife. Do not modify!
package com.example.leeyoungjae.fragmentapp.Activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.leeyoungjae.fragmentapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class KRIntroActivity_ViewBinding implements Unbinder {
  private KRIntroActivity target;

  private View view2131296462;

  private View view2131296335;

  private View view2131296463;

  private View view2131296336;

  private View view2131296464;

  private View view2131296337;

  private View view2131296465;

  private View view2131296338;

  private View view2131296466;

  private View view2131296339;

  private View view2131296467;

  private View view2131296340;

  @UiThread
  public KRIntroActivity_ViewBinding(KRIntroActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public KRIntroActivity_ViewBinding(final KRIntroActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.open_hide, "field 'img1' and method 'onOpen_Hide'");
    target.img1 = Utils.castView(view, R.id.open_hide, "field 'img1'", ImageView.class);
    view2131296462 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.content_txt1, "field 'txt1' and method 'onOpen_Hide'");
    target.txt1 = Utils.castView(view, R.id.content_txt1, "field 'txt1'", LinearLayout.class);
    view2131296335 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.open_hide2, "field 'img2' and method 'onOpen_Hide2'");
    target.img2 = Utils.castView(view, R.id.open_hide2, "field 'img2'", ImageView.class);
    view2131296463 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide2(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.content_txt2, "field 'txt2' and method 'onOpen_Hide2'");
    target.txt2 = Utils.castView(view, R.id.content_txt2, "field 'txt2'", LinearLayout.class);
    view2131296336 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide2(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.open_hide3, "field 'img3' and method 'onOpen_Hide3'");
    target.img3 = Utils.castView(view, R.id.open_hide3, "field 'img3'", ImageView.class);
    view2131296464 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide3(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.content_txt3, "field 'txt3' and method 'onOpen_Hide3'");
    target.txt3 = Utils.castView(view, R.id.content_txt3, "field 'txt3'", LinearLayout.class);
    view2131296337 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide3(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.open_hide4, "field 'img4' and method 'onOpen_Hide4'");
    target.img4 = Utils.castView(view, R.id.open_hide4, "field 'img4'", ImageView.class);
    view2131296465 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide4(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.content_txt4, "field 'txt4' and method 'onOpen_Hide4'");
    target.txt4 = Utils.castView(view, R.id.content_txt4, "field 'txt4'", LinearLayout.class);
    view2131296338 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide4(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.open_hide5, "field 'img5' and method 'onOpen_Hide5'");
    target.img5 = Utils.castView(view, R.id.open_hide5, "field 'img5'", ImageView.class);
    view2131296466 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide5(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.content_txt5, "field 'txt5' and method 'onOpen_Hide5'");
    target.txt5 = Utils.castView(view, R.id.content_txt5, "field 'txt5'", LinearLayout.class);
    view2131296339 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide5(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.open_hide6, "field 'img6' and method 'onOpen_Hide6'");
    target.img6 = Utils.castView(view, R.id.open_hide6, "field 'img6'", ImageView.class);
    view2131296467 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide6(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.content_txt6, "field 'txt6' and method 'onOpen_Hide6'");
    target.txt6 = Utils.castView(view, R.id.content_txt6, "field 'txt6'", LinearLayout.class);
    view2131296340 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOpen_Hide6(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    KRIntroActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img1 = null;
    target.txt1 = null;
    target.img2 = null;
    target.txt2 = null;
    target.img3 = null;
    target.txt3 = null;
    target.img4 = null;
    target.txt4 = null;
    target.img5 = null;
    target.txt5 = null;
    target.img6 = null;
    target.txt6 = null;

    view2131296462.setOnClickListener(null);
    view2131296462 = null;
    view2131296335.setOnClickListener(null);
    view2131296335 = null;
    view2131296463.setOnClickListener(null);
    view2131296463 = null;
    view2131296336.setOnClickListener(null);
    view2131296336 = null;
    view2131296464.setOnClickListener(null);
    view2131296464 = null;
    view2131296337.setOnClickListener(null);
    view2131296337 = null;
    view2131296465.setOnClickListener(null);
    view2131296465 = null;
    view2131296338.setOnClickListener(null);
    view2131296338 = null;
    view2131296466.setOnClickListener(null);
    view2131296466 = null;
    view2131296339.setOnClickListener(null);
    view2131296339 = null;
    view2131296467.setOnClickListener(null);
    view2131296467 = null;
    view2131296340.setOnClickListener(null);
    view2131296340 = null;
  }
}
