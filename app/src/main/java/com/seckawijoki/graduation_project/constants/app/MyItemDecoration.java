package com.seckawijoki.graduation_project.constants.app;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/26 at 22:18.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
  private static final String TAG = "MyItemDecoration";
  private int orientation = LinearLayoutManager.VERTICAL;
  private final int decoration = 30;//边距大小 px
  private final ColorDrawable mDivider = new ColorDrawable(0xffffffff);
  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);
    int lineSize = 8;
    final int itemCount = parent.getChildCount();
    Log.e("item","---->"+itemCount);
    final int left = parent.getPaddingLeft();
    final int right = parent.getWidth() - parent.getPaddingRight();
    for (int i = 0; i < itemCount; i++) {
      final View child = parent.getChildAt(i);
      if (child == null) return;
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int top = child.getBottom() + params.bottomMargin;
      final int bottom = top +lineSize;
      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(c);
    }
  }

  @Override
  public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDrawOver(c, parent, state);
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    final int lastPosition = state.getItemCount() - 1;//整个RecyclerView最后一个item的position
    final int current = parent.getChildLayoutPosition(view);//获取当前要进行布局的item的position
    Log.e(TAG, "0000---->" + current);
    Log.e(TAG, "0000state.getItemCount()---->" + state.getItemCount());
    Log.e(TAG, "0000getTargetScrollPosition---->" + state.getTargetScrollPosition());
    Log.e(TAG, "0000state---->" + state.toString());
    if (current == -1) return;//holder出现异常时，可能为-1
    if (layoutManager instanceof LinearLayoutManager && !(layoutManager instanceof GridLayoutManager )) {//LinearLayoutManager
      if (orientation == LinearLayoutManager.VERTICAL) {//垂直
        outRect.set(0, 0, 0, decoration);
        if (current == lastPosition) {//判断是否为最后一个item
          outRect.set(0, 0, 0, 0);
        } else {
          outRect.set(0, 0, 0, decoration);
        }
      } else {//水平
        if (current == lastPosition) {//判断是否为最后一个item
          outRect.set(0, 0, 0, 0);
        } else {
          outRect.set(0, 0,decoration,  0);
        }
      }
    }
  }
}
