package com.seckawijoki.graduation_project.functions.quotation_list;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/13 at 19:43.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.QuotationListSortType;
import com.seckawijoki.graduation_project.constants.server.StockType;
import com.seckawijoki.graduation_project.db.Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuotationListAdapter extends RecyclerView.Adapter<QuotationListAdapter.ViewHolder> {
  private static final String TAG = "QuotationListAdapter";
  private Context context;
  private List<Stock> originalList;
  private List<Stock> stockList;
  private OnQuotationClickListener listener;

  public QuotationListAdapter(Context context) {
    this.context = context;
  }

  public QuotationListAdapter(Context context, List<Stock> stockList) {
    this.context = context;
    this.stockList = stockList;
    originalList = new ArrayList<>(stockList);
  }

  QuotationListAdapter setFavoriteStockTop(Stock stock) {
    stockList.remove(stock);
    stockList.add(0, stock);
    return this;
  }

  QuotationListAdapter deleteFavoriteStock(Stock stock) {
    stockList.remove(stock);
    return this;
  }

  QuotationListAdapter setSpecialAttention(Stock stock) {
    int position = stockList.indexOf(stock);
    stockList.remove(stock);
    stockList.add(position, stock);
    return this;
  }

  public QuotationListAdapter sort(int sortType, boolean descendant) {
    Comparator comparator;
    switch ( sortType ) {
      default:
        comparator = new StockComparator();
        break;
      case QuotationListSortType.STOCK_NAME:
        comparator = new StockNameComparator();
        break;
      case QuotationListSortType.CURRENT_PRICE:
        comparator = new CurrentPriceComparator();
        break;
      case QuotationListSortType.FLUCTUATION_RATE:
        comparator = new FluctuationRateComparator();
        break;
    }
    if ( stockList != null ) {
      Collections.sort(stockList, comparator);
    }
    return this;
  }

  public QuotationListAdapter setOnQuotationClickListener(OnQuotationClickListener listener) {
    this.listener = listener;
    return this;
  }

  public QuotationListAdapter setStockList(List<Stock> stockList) {
    /*
    if ( this.stockList == null ) {
      this.stockList = stockList;
    } else {
      this.stockList.clear();
      this.stockList.addAll(stockList);
    }
    */
    this.stockList = stockList;
//    Log.w(TAG, "setStockList()\n: stockList = " + stockList);
    originalList = new ArrayList<>(stockList);
    return this;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.list_item_stock, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if ( stockList != null ) {
      Stock stock = stockList.get(position);
      if ( stock == null ) {
        return;
      }
//      Log.d(TAG, "onBindViewHolder(): stockList.size() = " + stockList.size());
      holder.bind(context, stock);
      holder.layout.setOnClickListener(v -> {
//        new Handler().post(() -> {
        listener.onQuotationClick(stock.getStockTableId());
//        });
      });
      holder.layout.setOnLongClickListener(v -> {
//        new Handler().post(() -> {
        listener.onQuotationLongClick(stock);
//        });
        return true;
      });
    }
  }

  @Override
  public int getItemCount() {
    return stockList != null ? stockList.size() : 0;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    View layout;
    private TextView tvType;
    private TextView tvName;
    private TextView tvId;
    private ImageView imgStar;
    private TextView tvCurrentPrice;
    private TextView tvFluctuationRate;

    ViewHolder(View view) {
      super(view);
      layout = view.findViewById(R.id.layout_list_item_stock);
      tvType = view.findViewById(R.id.tv_stock_type);
      tvName = view.findViewById(R.id.tv_stock_name);
      tvId = view.findViewById(R.id.tv_stock_id);
      imgStar = view.findViewById(R.id.img_star_with_special_attention);
      tvCurrentPrice = view.findViewById(R.id.tv_stock_current_price);
      tvFluctuationRate = view.findViewById(R.id.tv_fluctuation_rate);
    }

    void bind(Context context, Stock stock) {
      if ( stock == null ) {
        return;
      }
      switch ( stock.getStockType() ) {
        case StockType.SZ:
          tvType.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_type_sz));
          tvType.setText(R.string.label_sz);
          break;
        case StockType.SH:
          tvType.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_type_sh));
          tvType.setText(R.string.label_sh);
          break;
      }
      tvName.setText(stock.getStockName());
      tvId.setText(stock.getStockId());
      imgStar.setVisibility(stock.isSpecialAttention() ? View.VISIBLE : View.INVISIBLE);
      double fluctuation = stock.getFluctuationRate();
      if ( fluctuation < 0 ) {
        tvCurrentPrice.setTextColor(
                ContextCompat.getColor(context, R.color.tc_stock_green));
        tvFluctuationRate.setBackgroundColor(
                ContextCompat.getColor(context, R.color.bg_stock_fluctuation_rate_green));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate), fluctuation));
      } else if (fluctuation > 0){
        tvCurrentPrice.setTextColor(
                ContextCompat.getColor(context, R.color.tc_stock_red));
        tvFluctuationRate.setBackgroundColor(
                ContextCompat.getColor(context, R.color.bg_stock_fluctuation_rate_red));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate_positive), fluctuation));
      } else {
        tvCurrentPrice.setTextColor(
                ContextCompat.getColor(context, R.color.tc_stock_grey));
        tvFluctuationRate.setBackgroundColor(
                ContextCompat.getColor(context, R.color.bg_stock_fluctuation_rate_grey));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate), fluctuation));
      }
      tvCurrentPrice.setText(String.format(context.getString(R.string.format_stock_price), stock.getCurrentPrice()));
    }
  }

  private class StockNameComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock o1, Stock o2) {
      //// TODO: 2017/12/18
      return o1.getStockName().compareTo(o2.getStockName());
    }
  }

  private class CurrentPriceComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock o1, Stock o2) {
      return o1.getCurrentPrice() > o2.getCurrentPrice() ?
              -1 : o1.getCurrentPrice() < o2.getCurrentPrice() ?
              1 : 0;
    }
  }

  private class FluctuationRateComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock o1, Stock o2) {
      return o1.getFluctuationRate() > o2.getFluctuationRate() ?
              -1 : o1.getFluctuationRate() < o2.getFluctuationRate() ?
              1 : 0;
    }
  }
}