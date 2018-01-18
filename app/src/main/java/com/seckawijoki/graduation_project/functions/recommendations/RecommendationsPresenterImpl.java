package com.seckawijoki.graduation_project.functions.recommendations;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class RecommendationsPresenterImpl implements RecommendationsContract.Presenter,
        RecommendationsContract.View.ActionCallback,
        RecommendationsContract.Model.DataCallback {
  private RecommendationsContract.View view;
  private RecommendationsContract.Model model;

  @Override
  public void setView(RecommendationsContract.View view) {
    this.view = view;
  }

  @Override
  public void setModel(RecommendationsContract.Model model) {
    this.model = model;
  }

  @Override
  public void initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    view.initiate();
    model.initiate();
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }
}