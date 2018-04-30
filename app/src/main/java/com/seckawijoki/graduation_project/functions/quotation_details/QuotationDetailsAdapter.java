package com.seckawijoki.graduation_project.functions.quotation_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/18 at 22:44.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.QuotationDetailsViewType;
import com.seckawijoki.graduation_project.db.QuotationDetails;

class QuotationDetailsAdapter extends RecyclerView.Adapter<QuotationDetailsAdapter.ViewHolder> {
  private static final String TAG = "QuotationDetailsAdapter";
  private Context context;
  private QuotationDetails details;
  QuotationDetailsAdapter(Context context) {
    this.context = context;
  }
  QuotationDetailsAdapter setQuotationDetails(QuotationDetails details) {
    this.details = details;
    return this;
  }
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.recycler_item_quotation_details, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(context, position, details);
  }

  @Override
  public int getItemCount() {
    return QuotationDetailsViewType.count;
  }

  static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvLabel;
    private TextView tvValue;
    ViewHolder(View view) {
      super(view);
      tvLabel = view.findViewById(R.id.tv_quotation_details_label);
      tvValue = view.findViewById(R.id.tv_quotation_details_value);
    }

    void bind(Context context, int position, QuotationDetails details) {
      if (details == null)return;
      double priceDifference = details.getPriceDifference();
      switch ( position ){
        case QuotationDetailsViewType.CURRENT_PRICE:
          tvLabel.setText(
                  String.format(context.getString(R.string.format_stock_price), details.getCurrentPrice())
          );
          float fontSize =
                  context.getResources().getDimension(R.dimen.ts_quotation_details_current_price)
                          / context.getResources().getDisplayMetrics().density;
          tvLabel.setTextSize(fontSize);
          tvValue.setTextSize(fontSize);
          if (priceDifference <= 0) {
            tvLabel.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
            tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
            tvValue.setText(R.string.label_down);
          } else {
            tvLabel.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
            tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
            tvValue.setText(R.string.label_up);
          }
          break;
        case QuotationDetailsViewType.HIGHEST_PRICE_TODAY:
          tvLabel.setText(R.string.label_highest_price_today);
          tvValue.setText(
                  String.format(context.getString(R.string.format_stock_price), details.getHighestPriceToday())
          );
          tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
          break;
        case QuotationDetailsViewType.OPENING_PRICE_TODAY:
          tvLabel.setText(R.string.label_opening_price_today);
          tvValue.setText(
                  String.format(context.getString(R.string.format_stock_price), details.getOpeningPriceToday())
          );
          tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
          break;
        case QuotationDetailsViewType.PRICE_DIFFERENCE:
          if (priceDifference <= 0) {
            tvLabel.setText(
                    String.format(context.getString(R.string.format_percentage_with_two_decimals), priceDifference)
            );
            tvValue.setText(
                    String.format(context.getString(R.string.format_percentage_with_two_decimals), details.getPriceDifferenceRate())
            );
            tvLabel.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
            tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
          } else {
            tvLabel.setText(
                    String.format(context.getString(R.string.format_stock_price_positive), priceDifference)
            );
            tvValue.setText(
                    String.format(context.getString(R.string.format_price_difference_rate_positive), details.getPriceDifferenceRate())
            );
            tvLabel.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
            tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
          }
          break;
        case QuotationDetailsViewType.LOWEST_PRICE_TODAY:
          tvLabel.setText(R.string.label_lowest_price_today);
          tvValue.setText(
                  String.format(context.getString(R.string.format_stock_price), details.getLowestPriceToday())
          );
          tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
          break;
        case QuotationDetailsViewType.CLOSING_PRICE_YESTERDAY:
          tvLabel.setText(R.string.label_closing_price_yesterday);
          tvValue.setText(
                  String.format(context.getString(R.string.format_stock_price), details.getClosingPriceYesterday())
          );
          break;
        case QuotationDetailsViewType.VOLUME:
          tvLabel.setText(R.string.label_volume);
          tvValue.setText(
                  String.format(
                          context.getString(R.string.format_volume_in_hundred_million_hands),
                          details.getVolumeInHundredMillionHands())
          );
          break;
        case QuotationDetailsViewType.TURNOVER:
          tvLabel.setText(R.string.label_turnover);
          tvValue.setText(
                  String.format(
                          context.getString(R.string.format_turnover_in_hundred_million),
                          details.getTurnoverInHundredMillion())
          );
          break;
        case QuotationDetailsViewType.FLUCTUATION_RATE:
          tvLabel.setText(R.string.label_fluctuation_rate);
          tvValue.setText(
                  String.format(context.getString(R.string.format_fluctuation_rate), details.getFluctuationRate())
          );
          break;
        case QuotationDetailsViewType.INCREMENT:
          tvLabel.setText(R.string.label_increment);
// TODO: 2017/12/10
          break;
        case QuotationDetailsViewType.FLAT_PLATE:
          tvLabel.setText(R.string.label_flat_plate);
// TODO: 2017/12/10
          break;
        case QuotationDetailsViewType.DECREMENT:
          tvLabel.setText(R.string.label_decrement);
// TODO: 2017/12/10
          break;
        case QuotationDetailsViewType.AVERAGE_PRICE:
          tvLabel.setText(R.string.label_average_price);
          tvValue.setText(
                  String.format(context.getString(R.string.format_average_price), details.getAveragePrice())
          );
          break;
      }
    }

    @Override
    public void onClick(View v) {

    }
  }
}