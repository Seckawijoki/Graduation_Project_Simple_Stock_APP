package com.seckawijoki.graduation_project.functions.trade;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.app.UnderlineDecoration;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.constants.server.KLineType;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.db.client.UserTransaction;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.ToastUtils;
import com.seckawijoki.graduation_project.utils.ViewUtils;

import java.io.File;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:52.
 */

final class TradeViewImpl implements TradeContract.View, View.OnClickListener, View.OnKeyListener, TextWatcher {
  private ActionCallback callback;
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private int tradeCount;
  private EditText etTradePrice;
  private EditText etTradeCount;
  private TradeSummaryAdapter summaryAdapter;
  private String kLineChartUri;
  private boolean hasShownKLine;
  @Override
  public void onPresenterInitiate() {
    ViewUtils.bindOnClick(this,
            view.findViewById(R.id.img_k_line_chart),
            view.findViewById(R.id.img_refresh),
            view.findViewById(R.id.tv_open_or_close_k_line_chart),
            view.findViewById(R.id.tv_stock_id_and_district),
            view.findViewById(R.id.tv_subtract_point_nought_one),
            view.findViewById(R.id.tv_add_point_nought_one),
            view.findViewById(R.id.tv_subtract_hundred),
            view.findViewById(R.id.tv_add_hundred),
            view.findViewById(R.id.btn_buy),
            view.findViewById(R.id.btn_sell)
    );
    etTradePrice = view.findViewById(R.id.et_trade_price);
    etTradeCount = view.findViewById(R.id.et_trade_count);
    etTradeCount.addTextChangedListener(this);
    etTradePrice.setOnKeyListener(this);
    etTradeCount.setText(R.string.default_trade_count_hundred);
    summaryAdapter = new TradeSummaryAdapter(activity);
    RecyclerView rv = view.findViewById(R.id.rv_trade_summary);
    rv.setLayoutManager(new GridLayoutManager(activity, 2));
    rv.setAdapter(summaryAdapter);
    callback.onRequestRefreshQuotation();
  }

  @Override
  public void destroy() {
    activity = null;
    fragment = null;
    callback = null;
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayRefreshQuotation(Stock stock) {
    if ( stock == null ) {
      ToastUtils.show(activity, R.string.error_failed_to_refresh);
      return;
    }
    ( (TextView) view.findViewById(R.id.tv_stock_name_id_and_type) ).setText(
            String.format(activity.getString(R.string.format_stock_name_id_and_type),
                    stock.getStockName(),
                    stock.getStockId(),
                    GlobalVariableTools.getStockType(activity, stock.getStockType())
            )
    );
    TextView tvCurrentPrice = view.findViewById(R.id.tv_stock_current_price);
    TextView tvCurrentPoint = view.findViewById(R.id.tv_stock_current_point);
    TextView tvFluctuationRate = view.findViewById(R.id.tv_fluctuation_rate);
    tvCurrentPrice.setText(
            String.format(activity.getString(R.string.format_double_with_three_decimals),
                    stock.getCurrentPrice()
            )
    );
    ( (TextView) view.findViewById(R.id.tv_stock_id_and_district) ).setText(
            String.format(activity.getString(R.string.format_stock_id_and_china), stock.getStockId())
    );
    etTradePrice.setText(String.format(activity.getString(R.string.format_double_with_two_decimals),
            stock.getCurrentPrice()
            )
    );
    double fluctuationRate = stock.getFluctuationRate();
    if ( fluctuationRate > 0 ) {
      tvCurrentPrice.setTextColor(ContextCompat.getColor(activity, R.color.tc_stock_red));
      tvCurrentPoint.setTextColor(ContextCompat.getColor(activity, R.color.tc_stock_red));
      tvFluctuationRate.setTextColor(ContextCompat.getColor(activity, R.color.tc_stock_red));
      tvCurrentPoint.setText(
              String.format(activity.getString(R.string.format_double_with_three_decimals_positive),
                      stock.getCurrentPoint()
              )
      );
      tvFluctuationRate.setText(
              String.format(activity.getString(R.string.format_percentage_with_two_decimals_positive),
                      stock.getFluctuationRate()
              )
      );
    } else if ( fluctuationRate < 0 ) {
      tvCurrentPrice.setTextColor(ContextCompat.getColor(activity, R.color.tc_stock_green));
      tvCurrentPoint.setTextColor(ContextCompat.getColor(activity, R.color.tc_stock_green));
      tvFluctuationRate.setTextColor(ContextCompat.getColor(activity, R.color.tc_stock_green));
      tvCurrentPoint.setText(
              String.format(activity.getString(R.string.format_double_with_three_decimals),
                      stock.getCurrentPoint()
              )
      );
      tvFluctuationRate.setText(
              String.format(activity.getString(R.string.format_percentage_with_two_decimals),
                      stock.getFluctuationRate()
              )
      );
    } else {
      tvCurrentPrice.setTextColor(Color.BLACK);
      tvCurrentPoint.setTextColor(Color.BLACK);
      tvFluctuationRate.setTextColor(Color.BLACK);
      tvCurrentPoint.setText(
              String.format(activity.getString(R.string.format_double_with_three_decimals),
                      stock.getCurrentPoint()
              )
      );
      tvFluctuationRate.setText(
              String.format(activity.getString(R.string.format_percentage_with_two_decimals),
                      stock.getFluctuationRate()
              )
      );
    }
    summaryAdapter
            .setTradePrice(stock.getCurrentPrice())
            .notifyDataSetChanged();
  }

  @Override
  public void displayKLineChart(File chartFile) {
    kLineChartUri = chartFile.getPath();
    hasShownKLine = true;
    ImageView img = view.findViewById(R.id.img_k_line_chart);
    img.setImageBitmap(BitmapFactory.decodeFile(kLineChartUri));
  }

  @Override
  public void displayFollowingTransaction(UserTransaction transaction) {
    callback.onRequestRefreshQuotation(transaction.getStockTableId());
    ( (TextView) view.findViewById(R.id.tv_stock_id_and_district) ).setText(
            String.format(activity.getString(R.string.format_stock_id_and_china), transaction.getStockId())
    );
    etTradePrice.setText(String.format(activity.getString(R.string.format_double_with_two_decimals),
            transaction.getTradePrice()
            )
    );
    etTradeCount.setText(R.string.default_trade_count_hundred);
    summaryAdapter
            .setTradePrice(transaction.getTradePrice())
            .notifyDataSetChanged();
  }

  private TradeViewImpl() {
  }

  TradeViewImpl(AppCompatActivity activity) {
    this.activity = activity;
  }

  TradeViewImpl(Fragment fragment) {
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {
      case R.id.img_k_line_chart:
        if (hasShownKLine){
          Intent intent = new Intent(IntentAction.FULL_SCREEN_IMAGE);
          intent.putExtra(IntentKey.FULL_SCREEN_IMAGE_URI, kLineChartUri);
          fragment.startActivity(intent);
        }
        break;
      case R.id.img_refresh:
        callback.onRequestRefreshQuotation();
        break;
      case R.id.tv_open_or_close_k_line_chart:
        ImageView imgKLineChart = view.findViewById(R.id.img_k_line_chart);
        TextView tv = view.findViewById(R.id.tv_open_or_close_k_line_chart);
        if ( imgKLineChart.getVisibility() != View.VISIBLE ) {
          imgKLineChart.setVisibility(View.VISIBLE);
          tv.setText(R.string.empty);
          tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_close_k_line_chart, 0);
          callback.onRequestKLineChart(KLineType.MINUTE);
        } else {
          imgKLineChart.setVisibility(View.GONE);
          tv.setText(R.string.action_open_k_line_chart);
          tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_open_k_line_chart, 0);
        }
        break;
      case R.id.tv_stock_id_and_district:
        Intent intent = new Intent(IntentAction.SEARCH);
        intent.putExtra(IntentKey.SEARCH_FOR_CALLBACK, true);
        fragment.startActivityForResult(intent, ActivityRequestCode.SEARCH);
        break;
      case R.id.tv_subtract_point_nought_one:
        double tradePrice = Double.parseDouble(etTradePrice.getText().toString());
        tradePrice -= 0.01;
        if ( tradePrice < 0 ) tradePrice = 0;
        etTradePrice.setText(
                String.format(activity.getString(R.string.format_double_with_two_decimals),
                        tradePrice
                )
        );
        summaryAdapter.setTradePrice(tradePrice).notifyDataSetChanged();
        break;
      case R.id.tv_add_point_nought_one:
        tradePrice = Double.parseDouble(etTradePrice.getText().toString());
        tradePrice += 0.01;
        etTradePrice.setText(
                String.format(activity.getString(R.string.format_double_with_two_decimals),
                        tradePrice
                )
        );
        summaryAdapter.setTradePrice(tradePrice).notifyDataSetChanged();
        break;
      case R.id.tv_subtract_hundred:
        tradeCount = Integer.parseInt(etTradeCount.getText().toString());
        tradeCount -= 100;
        if ( tradeCount < 0 ) tradeCount = 0;
        etTradeCount.setText(tradeCount + "");
        summaryAdapter.setTradeCount(tradeCount).notifyDataSetChanged();
        break;
      case R.id.tv_add_hundred:
        tradeCount = Integer.parseInt(etTradeCount.getText().toString());
        tradeCount += 100;
        etTradeCount.setText(tradeCount + "");
        summaryAdapter.setTradeCount(tradeCount).notifyDataSetChanged();
        break;
      case R.id.btn_buy:
        tradePrice = Double.parseDouble(etTradePrice.getText().toString());
        tradeCount = Integer.parseInt(etTradeCount.getText().toString());
        callback.onRequestBuying(tradePrice, tradeCount);
        break;
      case R.id.btn_sell:
        tradePrice = Double.parseDouble(etTradePrice.getText().toString());
        tradeCount = Integer.parseInt(etTradeCount.getText().toString());
        callback.onRequestSelling(tradePrice, tradeCount);
        break;
    }
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if ( v.getId() == R.id.et_trade_price || v.getId() == R.id.et_trade_count ) {

      return true;
    } else {
      return false;
    }
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {

  }
}