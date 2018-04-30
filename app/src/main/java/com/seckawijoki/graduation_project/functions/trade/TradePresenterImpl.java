package com.seckawijoki.graduation_project.functions.trade;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:57.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.seckawijoki.graduation_project.db.Stock;

import java.io.File;

final class TradePresenterImpl implements TradeContract.Presenter,
        TradeContract.View.ActionCallback,
        TradeContract.Model.DataCallback{
  private TradeContract.View view;
  private TradeContract.Model model;
  private Activity activity;
  @Override
  public TradeContract.Presenter setView(TradeContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public TradeContract.View getView() {
    return view;
  }

  @Override
  public TradeContract.Presenter setView(Fragment fragment) {
    this.view = new TradeViewImpl(fragment);
    return this;
  }


  @Override
  public TradeContract.Presenter setModel(Activity activity) {
    this.activity = activity;
    this.model = new TradeModelImpl(activity);
    return this;
  }

  @Override
  public TradeContract.Model getModel() {
    return model;
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

  @Override
  public void onRequestRefreshQuotation() {
    model.requestRefreshQuotation();
  }

  @Override
  public void onRequestRefreshQuotation(long stockTableId) {
    model.requestRefreshQuotation(stockTableId);
  }

  @Override
  public void onRequestBuying(double tradePrice, int tradeCount) {
    model.requestBuying(tradePrice, tradeCount);
  }

  @Override
  public void onRequestSelling(double tradePrice, int tradeCount) {
    model.requestSelling(tradePrice, tradeCount);
  }

  @Override
  public void onRequestKLineChart(int kLineType) {
    new Thread(()->model.requestKLineChart(kLineType)).start();
  }

  @Override
  public void onDisplayRefreshQuotation(Stock stock) {
    view.displayRefreshQuotation(stock);
  }

  @Override
  public void onDisplayKLineChart(File chartFile) {
    activity.runOnUiThread(() -> view.displayKLineChart(chartFile));
  }

}