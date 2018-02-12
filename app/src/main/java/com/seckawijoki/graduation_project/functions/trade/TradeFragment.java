package com.seckawijoki.graduation_project.functions.trade;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:57.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

public class TradeFragment extends Fragment {
  private static final String TAG = "TradeFragment";
  private TradeContract.Presenter presenter;
  public TradeContract.Presenter getPresenter() {
    return presenter;
  }
  public static TradeFragment newInstance() {
    Bundle args = new Bundle();
    TradeFragment fragment = new TradeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_trade, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new TradePresenterImpl()
            .setModel(getActivity())
            .setView(this)
            .initiate();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.i(TAG, "onActivityResult()\n: ");
    if (requestCode == ActivityRequestCode.SEARCH && resultCode == Activity.RESULT_OK){
      GlobalVariableTools.setTradeStockTableId(getActivity(),
              data.getLongExtra(IntentKey.STOCK_TABLE_ID, 1));
      presenter.getModel().requestRefreshQuotation();
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
    presenter = null;
  }

}