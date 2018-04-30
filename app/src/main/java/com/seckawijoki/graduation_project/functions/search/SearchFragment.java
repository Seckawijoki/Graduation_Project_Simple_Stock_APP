package com.seckawijoki.graduation_project.functions.search;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/7 at 20:25.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.client.SearchStock;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import java.util.List;

public final class SearchFragment extends Fragment
        implements SearchContract.View, View.OnClickListener, OnSearchStockListener, SearchView.OnQueryTextListener {
  private static final String TAG = "SearchFragment";
  private ActionCallback callback;
  private Activity activity;
  private SearchView svStockSearch;
  private ViewGroup layoutEmptySearch;
  private ViewGroup layoutSomethingSearch;
  private RecyclerView rvHistory;
  private RecyclerView rvSearch;
  private SearchStockAdapter historyAdapter;
  private SearchStockAdapter searchAdapter;
  private boolean hasFavored;
  private String search;
  private long favoriteGroupId;

  public static SearchFragment newInstance() {
    Bundle args = new Bundle();
    SearchFragment fragment = new SearchFragment();
    fragment.activity = fragment.getActivity();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search, container, false);
    setHasOptionsMenu(true);
    svStockSearch = view.findViewById(R.id.sv_stock_search);
    layoutEmptySearch = view.findViewById(R.id.layout_empty_search);
    layoutSomethingSearch = view.findViewById(R.id.layout_something_search);
    rvHistory = view.findViewById(R.id.rv_stock_search_history);
    rvSearch = view.findViewById(R.id.rv_stock_search);
    layoutSomethingSearch.setVisibility(View.GONE);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();
    Intent intent = activity.getIntent();
    hasFavored = hasFavored | intent.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false);
    Toolbar tb = activity.findViewById(R.id.tb_search);
    ( (AppCompatActivity) activity ).setSupportActionBar(tb);
    svStockSearch.setOnQueryTextListener(this);
    rvHistory.setLayoutManager(new LinearLayoutManager(activity));
    rvSearch.setLayoutManager(new LinearLayoutManager(activity));
    historyAdapter = new SearchStockAdapter(activity)
            .setOnSearchStockListener(this)
            .switchHistoryAdapter(true);
    searchAdapter = new SearchStockAdapter(activity)
            .setOnSearchStockListener(this)
            .switchHistoryAdapter(false);
    rvHistory.setAdapter(historyAdapter);
    rvSearch.setAdapter(searchAdapter);
    callback.onRequestStockSearchHistory();
    ToastUtils.show(activity, activity.getIntent().getLongExtra(IntentKey.FAVORITE_GROUP_ID, -1L)+"");
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_search, menu);
    super.onCreateOptionsMenu(menu, inflater);
    activity.invalidateOptionsMenu();
  }



  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        destroy();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.w(TAG, "onActivityResult()\n: ");
    Log.d(TAG, "onActivityResult()\n: requestCode = " + requestCode);
    Log.d(TAG, "onActivityResult()\n: resultCode = " + resultCode);
    Log.d(TAG, "onActivityResult()\n: data = " + data);
    if ( resultCode == Activity.RESULT_OK && requestCode == ActivityRequestCode.SINGLE_QUOTATION ) {
      boolean hasFavoredFromDetails = data.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false);
      Log.d(TAG, "onActivityResult()\n: hasFavoredFromDetails = " + hasFavoredFromDetails);
      hasFavored = hasFavored | hasFavoredFromDetails;
      if ( hasFavoredFromDetails ) {
        if ( TextUtils.isEmpty(search) ) {
          callback.onRequestStockSearchHistory();
        } else {
          callback.onRequestStockSearch(search);
        }
      }
    }
  }

  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {
    Intent intent = new Intent();
    intent.putExtra(IntentKey.HAS_FAVORED_STOCK, hasFavored);
    activity.setResult(hasFavored ? Activity.RESULT_OK : Activity.RESULT_CANCELED, intent);
    activity.setIntent(intent);
    activity.finish();
  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayStockSearchHistory(List<SearchStock> searchStockList) {
    if ( searchStockList == null || searchStockList.size() == 0 ) {
      layoutEmptySearch.setVisibility(View.GONE);
    } else {
      layoutEmptySearch.setVisibility(View.VISIBLE);
      if ( historyAdapter == null ) {
        historyAdapter = new SearchStockAdapter(activity)
                .setOnSearchStockListener(this)
                .switchHistoryAdapter(true);
        rvHistory.setAdapter(historyAdapter);
      }
      historyAdapter.setSearchStockList(searchStockList).notifyDataSetChanged();
    }
  }

  @Override
  public void displayStockSearch(List<SearchStock> searchStockList) {
    if ( searchStockList == null ) {
      layoutSomethingSearch.setVisibility(View.GONE);
    } else {
      layoutSomethingSearch.setVisibility(View.VISIBLE);
      if ( searchAdapter == null ) {
        searchAdapter = new SearchStockAdapter(activity)
                .setOnSearchStockListener(this)
                .switchHistoryAdapter(false);
        rvSearch.setAdapter(searchAdapter);
      }
      searchAdapter.setSearchStockList(searchStockList).notifyDataSetChanged();
    }
  }

  @Override
  public void displayClearStockSearchHistory(boolean successful) {
    layoutEmptySearch.setVisibility(successful ? View.GONE : View.VISIBLE);
    ToastUtils.show(activity, successful ?
            R.string.msg_succeed_in_clearing_stock_search_history
            : R.string.error_failed_to_clear_stock_search_history);
  }

  @Override
  public void displayAddFavoriteStock(boolean successful) {
    hasFavored = successful | hasFavored;
    ToastUtils.show(activity, successful ?
            R.string.msg_succeed_in_adding_favorite_stock
            : R.string.error_failed_to_add_favorite_stock);
    searchAdapter.notifyDataSetChanged();
  }

  @Override
  public void displayDeleteFavoriteStock(boolean successful) {
    hasFavored = successful | hasFavored;
    ToastUtils.show(activity, successful ?
            R.string.msg_succeed_in_cancelling_favorite_stock
            : R.string.error_failed_to_cancel_favorite_stock);
    searchAdapter.notifyDataSetChanged();
  }

  @Override
  public void displayNotifyDataSetChange() {
    historyAdapter.notifyDataSetChanged();
    searchAdapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {

    }
  }

  @Override
  public void onSearchStockFavor(SearchStock searchStock) {
      if ( searchStock.isFavorite() ) {
        callback.onRequestDeleteFavoriteStock(searchStock);
      } else {
        callback.onRequestAddFavoriteStock(searchStock);
      }
  }

  @Override
  public void onSearchStockClick(SearchStock searchStock) {
    boolean clickForCallback = activity.getIntent().getBooleanExtra(IntentKey.SEARCH_FOR_CALLBACK, false);
    if ( clickForCallback ) {
      Intent intent = new Intent();
      intent.putExtra(IntentKey.STOCK_TABLE_ID, searchStock.getStockTableId());
      activity.setResult(Activity.RESULT_OK, intent);
      activity.finish();
    } else {
      Intent intent = new Intent(IntentAction.SINGLE_QUOTATION);
      intent.putExtra(IntentKey.STOCK_TABLE_ID, searchStock.getStockTableId());
      startActivityForResult(intent, ActivityRequestCode.SINGLE_QUOTATION);
      callback.onRequestSaveStockSearchHistory(searchStock);
    }
  }

  @Override
  public void onSearchStockHistoryClear() {
    callback.onRequestClearStockSearchHistory();
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    search = query;
    callback.onRequestStockSearch(query);
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    search = newText;
    if ( TextUtils.isEmpty(newText) ) {
      layoutEmptySearch.setVisibility(View.VISIBLE);
      layoutSomethingSearch.setVisibility(View.GONE);
      callback.onRequestStockSearchHistory();
    } else {
      layoutEmptySearch.setVisibility(View.GONE);
      layoutSomethingSearch.setVisibility(View.VISIBLE);
      callback.onRequestStockSearch(newText);
    }
    return false;
  }
}