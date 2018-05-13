package com.seckawijoki.graduation_project.functions.transaction;

import com.seckawijoki.graduation_project.db.client.UserTransaction;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/26 at 13:22.
 */

public interface OnTransactionClickListener {
  void onStockClick(long stockTableId);
  void onFollowToBuyOrSell(UserTransaction transaction);
}
