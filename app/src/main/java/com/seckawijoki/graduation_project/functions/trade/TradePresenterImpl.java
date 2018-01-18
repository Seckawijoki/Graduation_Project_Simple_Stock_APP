package com.seckawijoki.graduation_project.functions.trade;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:57.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

final class TradePresenterImpl implements TradeContract.Presenter,
        TradeContract.View.ActionCallback,
        TradeContract.Model.DataCallback {
  private TradeContract.View view;
  private TradeContract.Model model;

  @Override
  public TradeContract.Presenter setView(TradeContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public TradeContract.Presenter setView(Fragment fragment) {
    this.view = new TradeViewImpl(fragment);
    return this;
  }


  @Override
  public TradeContract.Presenter setModel(Activity activity) {
    this.model = new TradeModelImpl();
    return this;
  }

  @Override
  public TradeContract.Presenter initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.onPresenterInitiate();
    model.onViewInitiate();
    return this;
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }
}