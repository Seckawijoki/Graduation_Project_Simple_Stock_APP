package com.seckawijoki.graduation_project.functions.quotation_list;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/4 at 16:37.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.BundleKey;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.functions.favorite.OnQuotationListRefreshListener;

public class QuotationListFragment extends Fragment{
  private QuotationListContract.Presenter presenter;
  private QuotationListContract.View view;
  private QuotationListContract.Model model;
  private OnQuotationListRefreshListener listener;
  public static QuotationListFragment newInstance(OnQuotationListRefreshListener listener) {
//    Bundle args = new Bundle();
    QuotationListFragment fragment = new QuotationListFragment();
    fragment.listener = listener;
//    fragment.setArguments(args);
    return fragment;
  }


  public QuotationListContract.View getMvpView() {
    return view;
  }

  public QuotationListContract.Model getMvpModel() {
    return model;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_quotation_list, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new QuotationListPresenterImpl();
    view = new QuotationListViewImpl(
            this,
            getArguments().getString(BundleKey.FAVORITE_GROUP_NAME));
    model = new QuotationListModelImpl(
            getActivity(),
            getArguments().getString(BundleKey.FAVORITE_GROUP_NAME),
            listener);
    presenter.setView(view).setModel(model).initiate();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if ( resultCode == Activity.RESULT_OK &&
            requestCode == ActivityRequestCode.SINGLE_QUOTATION &&
            data.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false) ) {
      listener.onQuotationListRefresh(null);
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (hidden){
      model.pause();
    } else {
      model.resume();
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
    view = null;
    model = null;
    presenter = null;
  }
}