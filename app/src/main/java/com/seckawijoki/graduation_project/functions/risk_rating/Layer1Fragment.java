package com.seckawijoki.graduation_project.functions.risk_rating;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/22.
 */

public class Layer1Fragment extends Fragment {
  private static final String TAG = "Layer1Fragment";
  private static Fragment fragment = new Layer1Fragment();
  private OnFragmentChangeListener listener;

  public static Fragment getInstance(OnFragmentChangeListener listener) {
    if (fragment == null) {
      fragment = new Layer1Fragment();
    }
    fragment.setArguments(new Bundle());
    ((Layer1Fragment)fragment).listener = listener;
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_layer1, container, false);
    view.findViewById(R.id.tv_layer_one).setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                Bundle arguments = new Bundle();
                arguments.putInt(RiskRatingKey.RISK_RATING_ONE, 1);
                listener.onFragmentChange(1, arguments);
              }
            });
    return view;
  }

}
