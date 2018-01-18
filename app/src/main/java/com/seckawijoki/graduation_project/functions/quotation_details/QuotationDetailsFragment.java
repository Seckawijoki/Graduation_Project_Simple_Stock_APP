package com.seckawijoki.graduation_project.functions.quotation_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/14 at 23:51.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.BundleKey;

import static android.R.attr.fragment;

public class QuotationDetailsFragment extends Fragment{
  private static final String TAG = "QuotationDetailsFragment";
  public static QuotationDetailsFragment newInstance(long stockTableId) {
    Bundle args = new Bundle();
    args.putLong(BundleKey.STOCK_TABLE_ID, stockTableId);
    QuotationDetailsFragment fragment = new QuotationDetailsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_quotation_details, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Bundle bundle = getArguments();
    new QuotationDetailsPresenterImpl()
            .setView(new QuotationDetailsViewImpl(this))
            .setModel(new QuotationDetailsModelImpl(getActivity(), bundle.getLong(BundleKey.STOCK_TABLE_ID)))
            .initiate();
  }



}