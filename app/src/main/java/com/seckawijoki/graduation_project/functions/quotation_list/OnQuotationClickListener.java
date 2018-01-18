package com.seckawijoki.graduation_project.functions.quotation_list;

import com.seckawijoki.graduation_project.db.Stock;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/15 at 18:12.
 */

public interface OnQuotationClickListener {
  void onQuotationClick(long stockTableId);
  void onQuotationLongClick(Stock stock);
}
