package com.seckawijoki.graduation_project.constants.app;

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

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/26 at 22:18.
 */

public class UnderlineDecoration extends RecyclerView.ItemDecoration {
  private int orientation = LinearLayoutManager.VERTICAL;
  private int lineSize = 1;//边距大小 px
  private int paddingLeft, paddingRight, paddingTop, paddingBottom;
  private Drawable drawable = new ColorDrawable(Color.BLACK);
  private UnderlineDecoration(){}
  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);
    final int itemCount = parent.getChildCount();
    final int left = parent.getPaddingLeft() + this.paddingLeft;
    final int right = parent.getWidth() - parent.getPaddingRight() - this.paddingRight;
    for (int i = 0; i < itemCount; i++) {
      final View child = parent.getChildAt(i);
      if (child == null) return;
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int top = child.getBottom() + params.bottomMargin + this.paddingTop;
      final int bottom = top +lineSize + this.paddingBottom;
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
    super.getItemOffsets(outRect, view, parent, state);
    final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    final int lastPosition = state.getItemCount() - 1;//整个RecyclerView最后一个item的position
    final int current = parent.getChildLayoutPosition(view);//获取当前要进行布局的item的position
    if (current == -1) return;//holder出现异常时，可能为-1
    if (layoutManager instanceof LinearLayoutManager && !(layoutManager instanceof GridLayoutManager )) {//LinearLayoutManager
      if (orientation == LinearLayoutManager.VERTICAL) {//垂直
        final int left = parent.getPaddingLeft() + this.paddingLeft;
        final int right = parent.getWidth() - parent.getPaddingRight() - this.paddingRight;
        if (current == lastPosition) {//判断是否为最后一个item
          outRect.set(0, 0, 0, 0);
//          outRect.set(left, 0, right, 0);
        } else {
          outRect.set(0, 0, 0, lineSize);
//          outRect.set(left, 0, right, lineSize);
        }
      } else {//水平
        if (current == lastPosition) {//判断是否为最后一个item
          outRect.set(0, 0, 0, 0);
        } else {
          outRect.set(0, 0, lineSize,  0);
        }
      }
    }
  }

  public static class Builder {
    private Context context;
    private Resources resources;
    private UnderlineDecoration itemDecoration = new UnderlineDecoration();
    public Builder(Context context) {
      this.context = context;
      resources = context.getResources();
    }
    public Builder setPaddingLeft(float left){
      itemDecoration.paddingLeft = (int) left;
      return this;
    }
    public Builder setPaddingLeftRes(int resId) {
      itemDecoration.paddingLeft = (int) resources.getDimension(resId);
      return this;
    }

    public Builder setPaddingRightRes(int resId) {
      itemDecoration.paddingRight = (int) resources.getDimension(resId);
      return this;
    }

    public Builder setPaddingTopRes(int resId) {
      itemDecoration.paddingTop = (int) resources.getDimension(resId);
      return this;
    }

    public Builder setPaddingBottomRes(int resId) {
      itemDecoration.paddingBottom = (int) resources.getDimension(resId);
      return this;
    }
    public Builder setOrientation(int orientation){
      itemDecoration.orientation = orientation;
      return this;
    }
    public Builder setLineSizeRes(int resId){
      itemDecoration.lineSize = (int) resources.getDimension(resId);
      return this;
    }
    public Builder setLineSize(float lineSize){
      itemDecoration.lineSize = (int) lineSize;
      return this;
    }
    public Builder setColorRes(int resId){
     itemDecoration.drawable = new ColorDrawable(
             ContextCompat.getColor(context, resId)
     );
      return this;
    }
    public Builder setColor(int color){
      itemDecoration.drawable = new ColorDrawable(color);
      return this;
    }
    public UnderlineDecoration build(){
      return itemDecoration;
    }
  }
}
