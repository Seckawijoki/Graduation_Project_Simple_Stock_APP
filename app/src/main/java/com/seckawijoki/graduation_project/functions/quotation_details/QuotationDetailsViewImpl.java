package com.seckawijoki.graduation_project.functions.quotation_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:51.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.constants.server.KLineType;
import com.seckawijoki.graduation_project.db.QuotationDetails;
import com.seckawijoki.graduation_project.db.Stock;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuotationDetailsViewImpl implements
        QuotationDetailsContract.View, OnKLineChooseListener, CompoundButton.OnCheckedChangeListener{
  private static final String TAG = "QuotationDetailsFrag";
  private AppCompatActivity activity;
  private View view;
  private Fragment fragment;
  private QuotationDetailsContract.View.ActionCallback callback;
  private CheckBox chbFavor;
  private QuotationDetailsAdapter detailsAdapter;
  private boolean isFavorChanged;
  private KLineChoiceAdapter kLineChoiceAdapter;
  private int kLineType = KLineType.MINUTE;

  @Override
  public void initiate() {
    chbFavor = view.findViewById(R.id.chb_add_to_favorite_stocks);
    chbFavor.setOnCheckedChangeListener(this);

    RecyclerView rvQuotationDetails = view.findViewById(R.id.rv_quotation_details);
    detailsAdapter = new QuotationDetailsAdapter(activity);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rvQuotationDetails.setLayoutManager(gridLayoutManager);
    rvQuotationDetails.setAdapter(detailsAdapter);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    RecyclerView rvKLineChoice = view.findViewById(R.id.rv_k_line_choice);
    kLineChoiceAdapter = new KLineChoiceAdapter(activity)
            .setOnKLineChooseListener(this);
    rvKLineChoice.setLayoutManager(linearLayoutManager);
    rvKLineChoice.setAdapter(kLineChoiceAdapter);
    callback.onRequestQuotationDetails();
  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayUpdateStockList(List<Stock> quotationList) {

  }

  @Override
  public void displayQuotationDetails(QuotationDetails details) {
    chbFavor.setOnCheckedChangeListener(null);
    chbFavor.setChecked(details.isFavorite());
    chbFavor.setOnCheckedChangeListener(this);
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 15);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    Date today15OClock = calendar.getTime();
    calendar.set(Calendar.HOUR_OF_DAY, 9);
    Date today9OClock = calendar.getTime();
    Time clockTime = details.getClockTime();
    java.sql.Date dateTime = details.getDateTime();
    activity.runOnUiThread(() -> {
      ( (TextView) view.findViewById(R.id.tv_quotation_details_id) )
              .setText(details.getStockId());
      ( (TextView) view.findViewById(R.id.tv_quotation_details_name) )
              .setText(details.getStockName());
      if ( clockTime.before(today9OClock) ) {
        ( (TextView) view.findViewById(R.id.tv_quotation_details_status) )
                .setText(R.string.tv_stock_status_has_not_opened);
      } else if ( clockTime.after(today9OClock) && clockTime.before(today15OClock) ) {
        ( (TextView) view.findViewById(R.id.tv_quotation_details_status) )
                .setText(R.string.tv_stock_status_has_opened);
      } else {
        ( (TextView) view.findViewById(R.id.tv_quotation_details_status) )
                .setText(R.string.tv_stock_status_has_closed);
      }
      ( (TextView) view.findViewById(R.id.tv_quotation_details_date_and_clock_time) )
              .setText(dateTime.toString().substring(5)
                      + "  "
                      + clockTime.toString().substring(0, 5));

      detailsAdapter.setQuotationDetails(details).notifyDataSetChanged();
    });
    callback.onRequestKLine(kLineType);
  }

  @Override
  public void displayAddFavoriteStock(boolean successful) {
    if ( successful ) {
      Log.i(TAG, "displayAddFavoriteStock()\n: ");
      isFavorChanged = !isFavorChanged;
      Intent intent = activity.getIntent();
      boolean hasFavored = intent.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false);
      intent.putExtra(IntentKey.HAS_FAVORED_STOCK, isFavorChanged | hasFavored);
      activity.setIntent(intent);
    }
    ToastUtils.show(activity, successful ?
            R.string.msg_succeed_in_adding_favorite_stock :
            R.string.error_failed_to_add_favorite_stock);
  }

  @Override
  public void displayDeleteFavoriteStock(boolean successful) {
    if ( successful ) {
      isFavorChanged = !isFavorChanged;
      Intent intent = activity.getIntent();
      boolean hasFavored = intent.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false);
      intent.putExtra(IntentKey.HAS_FAVORED_STOCK, isFavorChanged | hasFavored);
      activity.setIntent(intent);
    }
    ToastUtils.show(activity, successful ?
            R.string.msg_succeed_in_cancelling_favorite_stock :
            R.string.error_failed_to_cancel_favorite_stock);
  }


  @Override
  public void displayKLine(File chartFile) {
    final Bitmap bitmap = BitmapFactory.decodeFile(chartFile.getPath());
    activity.runOnUiThread(()->{
      kLineChoiceAdapter.notifyDataSetChanged();
      ImageView img = view.findViewById(R.id.img_k_line_chart);
      img.setImageBitmap(bitmap);
    });
  }

  @Override
  public void onKLineChoose(int kLineType) {
    this.kLineType = kLineType;
    callback.onRequestKLine(kLineType);
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    if ( buttonView.getId() == R.id.chb_add_to_favorite_stocks ) {
      if ( isChecked ) {
        callback.onRequestAddFavoriteStock();
      } else {
        callback.onRequestDeleteFavoriteStock();
      }
    }
  }

  private QuotationDetailsViewImpl(){}
  QuotationDetailsViewImpl(Fragment fragment){
    this.fragment = fragment;
    view = fragment.getView();
    activity = (AppCompatActivity) fragment.getActivity();
  }
}