package com.seckawijoki.graduation_project.functions.transaction;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:45.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.functions.assets.OnFollowToBuyOrSellListener;

public class TransactionFragment extends Fragment {
  private static final String TAG = "TransactionFragment";
  private TransactionContract.Presenter presenter;
  private OnFollowToBuyOrSellListener listener;
  public static TransactionFragment newInstance(OnFollowToBuyOrSellListener listener) {
    Bundle args = new Bundle();
    TransactionFragment fragment = new TransactionFragment();
    fragment.listener = listener;
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.i(TAG, "onAttach()\n: ");
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.i(TAG, "onCreate()\n: ");
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    Log.i(TAG, "onCreateView()\n: ");
    return inflater.inflate(R.layout.fragment_transaction, container, false);
  }



  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.i(TAG, "onActivityCreated()\n: ");
    if (presenter == null) {
      presenter = new TransactionPresenterImpl()
              .setModel(getActivity())
              .setView(new TransactionViewImpl(this).setOnFollowToBuyOrSellListener(listener))
              .initiate();
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.i(TAG, "onStart()\n: ");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.i(TAG, "onResume()\n: ");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.i(TAG, "onPause()\n: ");
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.i(TAG, "onStop()\n: ");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.i(TAG, "onDestroyView()\n: ");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i(TAG, "onDestroy()\n: ");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.i(TAG, "onDetach()\n: ");
//    presenter.destroy();
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