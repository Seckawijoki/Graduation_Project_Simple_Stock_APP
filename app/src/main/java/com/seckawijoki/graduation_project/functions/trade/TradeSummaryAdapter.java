package com.seckawijoki.graduation_project.functions.trade;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/4 at 10:00.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;

import java.text.DecimalFormat;

public class TradeSummaryAdapter extends RecyclerView.Adapter<TradeSummaryAdapter.ViewHolder> {
  private Context context;
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###.###");
  private double tradePrice;
  private int tradeCount;
  TradeSummaryAdapter setTradeCount(int tradeCount) {
    this.tradeCount = tradeCount;
    return this;
  }
  TradeSummaryAdapter setTradePrice(double tradePrice) {
    this.tradePrice = tradePrice;
    return this;
  }
  TradeSummaryAdapter(Context context) {
    this.context = context;
    tradeCount = context.getResources().getInteger(R.integer.int_default_trade_count);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.list_item_trade_summary, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    switch ( position ) {
      case TradeSummaryType.ORDER_OF_MAGNITUDE:
        holder.tvLabel.setText(R.string.label_order_of_magnitude);
        holder.tvValue.setText(DECIMAL_FORMAT.format(tradeCount * tradePrice));
        holder.tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_trade_summary_value_blue));
        break;
      case TradeSummaryType.PURCHASING_POWER:
        holder.tvLabel.setText(R.string.label_purchasing_power);
        holder.tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_trade_summary_value_orange));
        break;
      case TradeSummaryType.MAX_AVAILABLE_BUYING_COUNT:
        holder.tvLabel.setText(R.string.label_max_available_buying_count);
        holder.tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_trade_summary_value_blue));
        break;
      case TradeSummaryType.MAX_AVAILABLE_SELLING_COUNT:
        holder.tvLabel.setText(R.string.label_max_available_selling_count);
        holder.tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_trade_summary_value_orange));
        break;
    }
  }

  @Override
  public int getItemCount() {
    return TradeSummaryType.count;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvLabel;
    TextView tvValue;

    ViewHolder(View view) {
      super(view);
      tvLabel = view.findViewById(R.id.tv_trade_summary_label);
      tvValue = view.findViewById(R.id.tv_trade_summary_value);
    }
  }

  interface TradeSummaryType {
    int count = 4;
    int ORDER_OF_MAGNITUDE = 0;
    int PURCHASING_POWER = 1;
    int MAX_AVAILABLE_BUYING_COUNT = 2;
    int MAX_AVAILABLE_SELLING_COUNT = 3;
  }
}