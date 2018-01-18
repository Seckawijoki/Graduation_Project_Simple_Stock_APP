package com.seckawijoki.graduation_project.functions.quotations_old;

import com.seckawijoki.graduation_project.db.Quotation;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

class QuotationsPresenterImpl implements QuotationsContract.Presenter,
        QuotationsContract.View.ActionCallback,
        QuotationsContract.Model.DataCallback {
  private QuotationsContract.View view;
  private QuotationsContract.Model model;

  @Override
  public QuotationsContract.Presenter setView(QuotationsContract.View view) {
    this.view = view;
    return this;
  }

  @Override
  public QuotationsContract.Presenter setModel(QuotationsContract.Model model) {
    this.model = model;
    return this;
  }

  @Override
  public void initiate() {
    view.setActionCallback(this);
    model.setDataCallback(this);
    model.initiate();
    view.initiate();
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onRequestUpdateQuotationList() {
    model.requestUpdateQuotationList();
  }

  @Override
  public void onDisplayUpdateQuotationList(List<Quotation> quotationList) {
    view.displayUpdateQuotationList(quotationList);
  }
}