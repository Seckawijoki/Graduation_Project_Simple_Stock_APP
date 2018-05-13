package com.seckawijoki.graduation_project.constants.app;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/26 at 22:06.
 */

public class MyItemAnimator extends SimpleItemAnimator {
  @Override
  public boolean animateRemove(RecyclerView.ViewHolder holder) {
    return false;
  }

  @Override
  public boolean animateAdd(RecyclerView.ViewHolder holder) {
    return false;
  }

  @Override
  public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
    return false;
  }

  @Override
  public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
    return false;
  }

  @Override
  public void runPendingAnimations() {

  }

  @Override
  public void endAnimation(RecyclerView.ViewHolder item) {

  }

  @Override
  public void endAnimations() {

  }

  @Override
  public boolean isRunning() {
    return false;
  }
}
