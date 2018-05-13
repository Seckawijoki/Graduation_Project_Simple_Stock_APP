package com.seckawijoki.graduation_project.functions.recommendations;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.functions.quotation_list.OnQuotationClickListener;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/28 at 16:41.
 */

public class TopStockAdapter
        extends ExpandableRecyclerViewAdapter<TopStockAdapter.TopTypeViewHolder,
        TopStockAdapter.StockViewHolder> {
  private Context context;
  private OnQuotationClickListener onQuotationClickListener;
  public TopStockAdapter(Context context, List<? extends ExpandableGroup> groups) {
    super(groups);
    this.context = context;
  }

  public TopStockAdapter setOnQuotationClickListener(OnQuotationClickListener onQuotationClickListener) {
    this.onQuotationClickListener = onQuotationClickListener;
    return this;
  }

  @Override
  public TopTypeViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.expandable_group_item_top_stock, parent, false);
    return new TopTypeViewHolder(view);
  }

  @Override
  public StockViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_stock, parent, false);
    return new StockViewHolder(view);
  }

  @Override
  public void onBindChildViewHolder(StockViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
    holder.set(context, group, childIndex);
  }

  @Override
  public void onBindGroupViewHolder(TopTypeViewHolder holder, int flatPosition, ExpandableGroup group) {
    holder.set(group);
  }

  class TopTypeViewHolder extends GroupViewHolder{
    private TextView tv;
    TopTypeViewHolder(View view) {
      super(view);
      tv = view.findViewById(R.id.tv_top_stock_group_type);
    }
    TopTypeViewHolder set(ExpandableGroup group){
      tv.setText(group.getTitle());
      return this;
    }

    @Override
    public void expand() {
      super.expand();
    }

    @Override
    public void collapse() {
      super.collapse();
    }
  }
  class StockViewHolder extends ChildViewHolder{
    private View layout;
    private TextView tvStockType;
    private TextView tvStockName;
    private TextView tvStockId;
    private TextView tvCurrentPrice;
    private TextView tvFluctuationRate;
    StockViewHolder(View view) {
      super(view);
      layout = view.findViewById(R.id.layout_quotation_item);
      tvStockType = view.findViewById(R.id.tv_stock_type);
      tvStockType.setVisibility(View.INVISIBLE);
      tvStockName = view.findViewById(R.id.tv_stock_name);
      tvStockId = view.findViewById(R.id.tv_stock_id);
      tvCurrentPrice = view.findViewById(R.id.tv_stock_current_price);
      tvFluctuationRate = view.findViewById(R.id.tv_fluctuation_rate);
    }
    StockViewHolder set(Context context, ExpandableGroup group, int position){
      Stock stock = (Stock) group.getItems().get(position);
      tvStockName.setText(stock.getStockName());
      tvStockId.setText(stock.getStockId());
      tvCurrentPrice.setText(
              String.format(context.getString(R.string.format_double_with_two_decimals),
                      stock.getCurrentPrice())
      );
      double fluctuationRate = stock.getFluctuationRate();
      if (fluctuationRate > 0){
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_percentage_with_two_decimals_positive),
                        fluctuationRate)
        );
        tvFluctuationRate.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_red));
        tvCurrentPrice.setTextColor(ContextCompat.getColor(context, R.color.bg_stock_red));
      } else if (fluctuationRate < 0){
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_percentage_with_two_decimals),
                        fluctuationRate)
        );
        tvFluctuationRate.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_green));
        tvCurrentPrice.setTextColor(ContextCompat.getColor(context, R.color.bg_stock_green));
      } else {
        tvFluctuationRate.setText(
                String.format(context.getString(R.string.format_percentage_with_two_decimals),
                        fluctuationRate)
        );
        tvFluctuationRate.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_stock_grey));
        tvCurrentPrice.setTextColor(ContextCompat.getColor(context, R.color.bg_stock_grey));
      }
      long stockTableId = stock.getStockTableId();
      layout.setOnClickListener(v -> onQuotationClickListener.onQuotationClick(stockTableId));
      return this;
    }
  }
}
