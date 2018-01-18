package com.seckawijoki.graduation_project.functions.quotations_old;

import com.seckawijoki.graduation_project.db.Quotation;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/24.
 */

interface QuotationsContract {
  interface View   {
    void initiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayUpdateQuotationList(List<Quotation> quotationList);
    interface ActionCallback {
      void onRequestUpdateQuotationList();
    }
  }

  interface Model   {
    void initiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestUpdateQuotationList();
    interface DataCallback {
      void onDisplayUpdateQuotationList(List<Quotation> quotationList);
    }
  }

  interface Presenter   {
    void initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
  }
}
