package com.seckawijoki.graduation_project.functions.personal_info;

import com.seckawijoki.graduation_project.db.client.User;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/20 at 23:08.
 */

public interface OnPersonalInfoListener {
  void onPersonalInfoClick(int position, User user);
}
