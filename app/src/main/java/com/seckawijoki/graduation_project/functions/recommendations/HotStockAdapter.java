package com.seckawijoki.graduation_project.functions.recommendations;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 13:43.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.functions.quotation_list.OnQuotationClickListener;

import java.util.List;

class HotStockAdapter extends RecyclerView.Adapter<HotStockAdapter.ViewHolder> {
  private Context context;
  private List<Stock> stockList;
  private OnQuotationClickListener onQuotationClickListener;
  HotStockAdapter(Context context) {
    this.context = context;
  }

  public HotStockAdapter setOnQuotationClickListener(OnQuotationClickListener listener) {
    this.onQuotationClickListener = listener;
    return this;
  }

  HotStockAdapter setStockList(List<Stock> stockList) {
//    Log.i(TAG, "setStockList()\n: ");
    this.stockList = stockList;
    return this;
  }
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//    Log.i(TAG, "onCreateViewHolder()\n: ");
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_market_stock, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
//    Log.d(TAG, "onBindViewHolder()\n: position = " + position);
    Stock stock;
    holder.bind(context, stock = stockList.get(position));
    long stockTableId = stock.getStockTableId();
    holder.layout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onQuotationClickListener.onQuotationClick(stockTableId);
      }
    });
  }

  @Override
  public int getItemCount() {
    return stockList == null ? 0 : stockList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    View layout;
    private TextView tvStockName;
    private TextView tvCurrentPrice;
    private TextView tvCurrentPoint;
    private TextView tvFluctuationRate;
    ViewHolder(View view) {
      super(view);
      layout = view.findViewById(R.id.layout_list_item_market_stock);
      tvStockName = view.findViewById(R.id.tv_stock_name);
      tvCurrentPrice = view.findViewById(R.id.tv_stock_current_price);
      tvCurrentPoint = view.findViewById(R.id.tv_stock_current_point);
      tvFluctuationRate = view.findViewById(R.id.tv_fluctuation_rate);
    }

    void bind(Context context, Stock stock) {
      double currentPrice = stock.getCurrentPrice();
      double currentPoint = stock.getCurrentPoint();
      double fluctuationRate = stock.getFluctuationRate();
      tvStockName.setText(stock.getStockName());
      tvCurrentPrice.setText(
              String.format(context.getString(R.string.format_stock_price), currentPrice));
      if (fluctuationRate > 0){
        tvCurrentPoint.setText(
                String.format(context.getString(R.string.format_stock_point_positive), currentPoint));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate_positive), fluctuationRate));
        tvCurrentPoint.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
        tvFluctuationRate.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
        layout.setBackgroundResource(R.drawable.shape_bg_hot_stock_red);
      } else if (fluctuationRate < 0){
        tvCurrentPoint.setText(
                String.format(context.getString(R.string.format_stock_point), currentPoint));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate), fluctuationRate));
        tvCurrentPoint.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
        tvFluctuationRate.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
        layout.setBackgroundResource(R.drawable.shape_bg_hot_stock_green);
      } else {
        tvCurrentPoint.setText(
                String.format(context.getString(R.string.format_stock_point), currentPoint));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate), fluctuationRate));
        tvCurrentPoint.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_grey));
        tvFluctuationRate.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_grey));
        layout.setBackgroundResource(R.drawable.shape_bg_hot_stock_grey);
      }
    }
  }
}