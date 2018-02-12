package com.seckawijoki.graduation_project.functions.quotation_details;

import android.app.Activity;
import android.graphics.Bitmap;

import com.seckawijoki.graduation_project.db.QuotationDetails;
import com.seckawijoki.graduation_project.db.Stock;

import java.io.File;
import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:50.
 */

public interface QuotationDetailsContract {
  interface View {
    void initiate();
    void destroy();
    void setActionCallback(ActionCallback callback);
    void displayUpdateStockList(List<Stock> stockList);
    void displayQuotationDetails(QuotationDetails details);
    void displayAddFavoriteStock(boolean successful);
    void displayDeleteFavoriteStock(boolean successful);
    void displayKLine(File chartFile);
    interface ActionCallback {
      void onRequestUpdateStockList();
      void onRequestQuotationDetails();
      void onRequestKLine(int type);
      void onRequestAddFavoriteStock();
      void onRequestDeleteFavoriteStock();
    }
  }

  interface Model {
    void initiate();
    void destroy();
    void setDataCallback(DataCallback callback);
    void requestUpdateStockList();
    void requestQuotationDetails();
    void requestAddFavoriteStock();
    void requestDeleteFavoriteStock();
    void requestKLineChart(int type);
    interface DataCallback {
      void onDisplayUpdateStockList(List<Stock> stockList);
      void onDisplayQuotationDetails(QuotationDetails details);
      void onDisplayKLineChart(File chartFile);
      void onDisplayAddFavoriteStock(boolean successful);
      void onDisplayDeleteFavoriteStock(boolean successful);
    }
  }

  interface Presenter {
    Presenter initiate();
    void destroy();
    Presenter setView(View view);
    Presenter setModel(Model model);
    Presenter setModel(Activity activity);
  }
}
