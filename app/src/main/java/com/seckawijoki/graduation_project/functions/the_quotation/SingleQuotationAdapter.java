package com.seckawijoki.graduation_project.functions.the_quotation;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 23:45.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seckawijoki.graduation_project.functions.stock_comment.StockCommentFragment;
import com.seckawijoki.graduation_project.functions.stock_news.StockNewsFragment;
import com.seckawijoki.graduation_project.functions.quotation_details.QuotationDetailsFragment;

class SingleQuotationAdapter extends FragmentPagerAdapter {
  private QuotationDetailsFragment quotationDetailsFragment;
  private long stockTableId;
  SingleQuotationAdapter(FragmentManager fm, long stockTableId) {
    super(fm);
    this.stockTableId = stockTableId;
  }

  @Override
  public int getCount() {
    return 3;
  }

  @Override
  public Fragment getItem(int position) {
    Fragment fragment;
    switch ( position ){
      default:case 0:
        if (quotationDetailsFragment == null){
          quotationDetailsFragment = QuotationDetailsFragment.newInstance(stockTableId);
        }
        fragment = quotationDetailsFragment;break;
      case 1:
        fragment = StockNewsFragment.newInstance();break;
      case 2:
        fragment = StockCommentFragment.newInstance();break;
    }
    return fragment;
  }
}