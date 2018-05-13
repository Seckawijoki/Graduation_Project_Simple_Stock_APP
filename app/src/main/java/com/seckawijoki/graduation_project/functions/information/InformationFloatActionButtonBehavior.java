package com.seckawijoki.graduation_project.functions.information;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/8 at 21:38.
 */

public class InformationFloatActionButtonBehavior extends FloatingActionButton.Behavior {
  public InformationFloatActionButtonBehavior(Context context, AttributeSet attrs) {
    super();
  }
  @Override
  public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                     @NonNull FloatingActionButton child,
                                     @NonNull View directTargetChild, @NonNull View target,
                                     int axes,
                                     int type) {
    return axes == ViewCompat.SCROLL_AXIS_HORIZONTAL ||
            super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
  }

  @Override
  public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                             @NonNull FloatingActionButton child,
                             @NonNull View target,
                             int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                             int type) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    if (dxConsumed > 0 && child.getVisibility() == View.VISIBLE){
      child.hide();
    } else if (dxConsumed < 0 && child.getVisibility() != View.VISIBLE){
      child.show();
    }
  }
}
