package com.seckawijoki.graduation_project.functions.transaction;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:45.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.functions.assets.OnFollowToBuyOrSellListener;

public class TransactionFragment extends Fragment {
  private TransactionContract.Presenter presenter;
  private OnFollowToBuyOrSellListener listener;
  public static TransactionFragment newInstance(OnFollowToBuyOrSellListener listener) {
    Bundle args = new Bundle();
    TransactionFragment fragment = new TransactionFragment();
    fragment.listener = listener;
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_transaction, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new TransactionPresenterImpl()
            .setModel(getActivity())
            .setView(new TransactionViewImpl(this).setOnFollowToBuyOrSellListener(listener))
            .initiate();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        getActivity().finish();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }
}