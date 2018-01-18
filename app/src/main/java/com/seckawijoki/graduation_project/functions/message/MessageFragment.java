package com.seckawijoki.graduation_project.functions.message;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 23:18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityIntent;
import com.seckawijoki.graduation_project.util.ToastUtils;

public class MessageFragment extends Fragment {
  public static MessageFragment newInstance() {
    Bundle args = new Bundle();
    MessageFragment fragment = new MessageFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_message, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Toolbar tb = getView().findViewById(R.id.tb_message);
    ( (AppCompatActivity) getActivity() ).setSupportActionBar(tb);

  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_message, menu);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ){
      case android.R.id.home:
        getActivity().finish();
        break;
      case R.id.menu_address_book:
        startActivity(new Intent(ActivityIntent.ADDRESS_BOOK));
        break;
      case R.id.menu_create_group:
        ToastUtils.show(getActivity(), R.string.msg_under_developing);
        break;
      case R.id.menu_add_person:
        ToastUtils.show(getActivity(), R.string.msg_under_developing);
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }
}