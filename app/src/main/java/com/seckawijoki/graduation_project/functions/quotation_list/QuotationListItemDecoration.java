package com.seckawijoki.graduation_project.functions.quotation_list;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class QuotationListItemDecoration extends RecyclerView.ItemDecoration {
  private static final String TAG = "QuotationListItemDecor*";
  private Resources resources          ;
  QuotationListItemDecoration(Context context) {
    resources = context.getResources();
  }
  private final ColorDrawable mDivider = new ColorDrawable(Color.BLACK);
  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);
    float lineSize = resources.getDimension(R.dimen.h_quotation_list_item_divider);
    float paddingLeft = resources.getDimension(R.dimen.w_stock_type)
            + resources.getDimension(R.dimen.m_l_stock_name);
    final int itemCount = parent.getChildCount();
    final int left = (int) (parent.getPaddingLeft() + Math.floor(paddingLeft));
    final int right = parent.getWidth() - parent.getPaddingRight();
    for (int i = 0; i < itemCount; i++) {
      final View child = parent.getChildAt(i);
      if (child == null) return;
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int top = child.getBottom() + params.bottomMargin;
      final int bottom = (int) (top +lineSize);
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
    if (current == -1) return;//holder出现异常时，可能为-1
    if (layoutManager instanceof LinearLayoutManager && !(layoutManager instanceof GridLayoutManager )) {//LinearLayoutManager
      //垂直
      float dividerHeight  = resources.getDimension(R.dimen.h_quotation_list_item_divider);
      Log.d(TAG, "getItemOffsets()\n: dividerHeight = " + dividerHeight);
      int bottom = (int) dividerHeight;
      if (current == lastPosition) {//判断是否为最后一个item
        outRect.set(0, 0, 0, 0);
      } else {
        outRect.set(0, 0, 0, bottom);
      }
    }
  }
}
