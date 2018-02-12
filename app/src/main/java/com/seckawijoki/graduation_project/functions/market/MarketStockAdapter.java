package com.seckawijoki.graduation_project.functions.market;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 13:43.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.client.MarketStock;

import java.util.List;

class MarketStockAdapter extends RecyclerView.Adapter<MarketStockAdapter.ViewHolder> {
  private Context context;
  private List<MarketStock> marketStockList;
//  private static final String TAG = "MarketStockAdapter";
  MarketStockAdapter(Context context) {
    this.context = context;
  }
  MarketStockAdapter setMarketStockList(List<MarketStock> marketStockList) {
//    Log.i(TAG, "setMarketStockList()\n: ");
    this.marketStockList = marketStockList;
    return this;
  }
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//    Log.i(TAG, "onCreateViewHolder()\n: ");
    View view = LayoutInflater.from(context).inflate(R.layout.list_item_market_stock, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
//    Log.d(TAG, "onBindViewHolder()\n: position = " + position);
    holder.bind(context, marketStockList.get(position));
  }

  @Override
  public int getItemCount() {
    return marketStockList == null ? 0 : marketStockList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ViewGroup layout;
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

    void bind(Context context, MarketStock marketStock) {
      double currentPrice = marketStock.getCurrentPrice();
      double currentPoint = marketStock.getCurrentPoint();
      double fluctuationRate = marketStock.getFluctuationRate();
      tvStockName.setText(marketStock.getStockName());
      tvCurrentPrice.setText(
              String.format(context.getString(R.string.format_stock_price), currentPrice));
      if (fluctuationRate > 0){
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_red));
        tvCurrentPoint.setText(
                String.format(context.getString(R.string.format_stock_point_positive), currentPoint));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate_positive), fluctuationRate));
      } else if (fluctuationRate < 0){
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_green));
        tvCurrentPoint.setText(
                String.format(context.getString(R.string.format_stock_point), currentPoint));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate), fluctuationRate));
      } else {
        layout.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_grey));
        tvCurrentPoint.setText(
                String.format(context.getString(R.string.format_stock_point), currentPoint));
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_fluctuation_rate), fluctuationRate));
      }
    }

    @Override
    public void onClick(View v) {

    }
  }
}