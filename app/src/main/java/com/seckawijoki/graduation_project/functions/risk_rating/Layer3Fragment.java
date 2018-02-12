package com.seckawijoki.graduation_project.functions.risk_rating;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ToastUtils;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/22.
 */

public class Layer3Fragment extends Fragment {
  private static Fragment fragment = new Layer3Fragment();
  private OnFragmentChangeListener listener;
  public static Fragment getInstance(OnFragmentChangeListener listener){
    if (fragment == null){
      fragment = new Layer3Fragment();
    }
    fragment.setArguments(new Bundle());
    ((Layer3Fragment)fragment).listener = listener;
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_layer3, container, false);
    view.findViewById(R.id.tv_layer_three).setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                Bundle arguments = new Bundle();
                arguments.putInt(RiskRatingKey.RISK_RATING_THREE, 3);
                if (arguments.getInt(RiskRatingKey.RISK_RATING_ONE) < 0) {
                  ToastUtils.show(getActivity().getApplicationContext(),
                          "Layer 1 haven't been chose!");
                  listener.onFragmentChange(0, arguments);
                } else if (arguments.getInt(RiskRatingKey.RISK_RATING_TWO) < 0){
                  ToastUtils.show(getActivity().getApplicationContext(),
                          "Layer 2 haven't been chose!");
                  listener.onFragmentChange(1, arguments);
                } else {
                  fragment.getActivity().startActivity(new Intent(IntentAction.MAIN));
                  fragment.getActivity().finish();
                }
              }
            });
    return view;
  }
}
