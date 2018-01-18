package com.seckawijoki.graduation_project.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/18 at 17:19.
 */

public class FullScreenDialog extends DialogFragment {
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_full_screen);
  }
}
