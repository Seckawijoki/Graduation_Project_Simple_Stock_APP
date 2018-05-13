package com.seckawijoki.graduation_project.functions.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/26 at 22:18.
 */

public class SettingsDecoration extends RecyclerView.ItemDecoration {
  private int orientation = LinearLayoutManager.VERTICAL;
  private int lineSize;//边距大小 px
  private int paddingLeft;
  private int paddingRight;
  private int paddingTop;
  private int paddingBottom;
  private Drawable drawable;

  public SettingsDecoration(Context context) {
    Resources resources = context.getResources();
    lineSize = (int) resources.getDimension(R.dimen.h_settings_list_item_divider);
    drawable = new ColorDrawable(ContextCompat.getColor(context, R.color.bg_settings_divider));
    paddingLeft = (int) resources.getDimension(R.dimen.p_l_r_layout_settings_list_item);
    paddingRight = (int) resources.getDimension(R.dimen.p_l_r_layout_settings_list_item);
  }

  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);
    final int itemCount = parent.getChildCount();
    final int left = parent.getPaddingLeft() + this.paddingLeft;
    final int right = parent.getWidth() - parent.getPaddingRight() - this.paddingRight;
    for ( int i = 0 ; i < itemCount ; i++ ) {
      final View child = parent.getChildAt(i);
      if ( child == null ) return;
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int top = child.getBottom() + params.bottomMargin + this.paddingTop;
      final int bottom = top + lineSize + this.paddingBottom;
      drawable.setBounds(left, top, right, bottom);
      drawable.draw(c);
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
    if ( current == -1 ) return;//holder出现异常时，可能为-1
    if ( layoutManager instanceof LinearLayoutManager &&
            !( layoutManager instanceof GridLayoutManager ) ) {//LinearLayoutManager
      if ( orientation == LinearLayoutManager.VERTICAL ) {//垂直
        if ( current == lastPosition ) {//判断是否为最后一个item
          outRect.set(0, 0, 0, 0);
        } else {
          outRect.set(0, 0, 0, lineSize);
        }
      }
    }
  }
}
