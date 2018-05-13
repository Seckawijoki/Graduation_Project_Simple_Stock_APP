package com.seckawijoki.graduation_project.functions.calendar;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/10 at 15:34.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seckawijoki.graduation_project.R;

public class CalendarFragment extends Fragment {
  private CalendarContract.Presenter presenter;

  public static CalendarFragment newInstance() {
    Bundle args = new Bundle();
    CalendarFragment fragment = new CalendarFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_calendar, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter = new CalendarPresenterImpl()
            .setModel(getActivity())
            .setView(this)
            .initiate();
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    presenter.destroy();
    presenter = null;
  }

}