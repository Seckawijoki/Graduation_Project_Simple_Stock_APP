package com.seckawijoki.graduation_project.functions.assets;

import com.seckawijoki.graduation_project.db.client.UserTransaction;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/26 at 16:39.
 */

public interface OnFollowToBuyOrSellListener {
  void onFollowToBuyOrSell(UserTransaction transaction);
}
