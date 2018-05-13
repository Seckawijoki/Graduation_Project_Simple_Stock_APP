package com.seckawijoki.graduation_project.functions.information_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/28 at 9:52.
 */

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.client.Information;
import com.seckawijoki.graduation_project.db.client.SimpleInformation;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class InformationDetailsFragment
        extends Fragment
        implements InformationDetailsContract.View, View.OnClickListener {
  private static final String TAG = "InfoDetailsFragment";
  private ActionCallback callback;
  private Activity activity;

  public static InformationDetailsFragment newInstance() {
    Bundle args = new Bundle();
    InformationDetailsFragment fragment = new InformationDetailsFragment();
    fragment.activity = fragment.getActivity();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_information_details, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = getActivity();
    View view = getView();
    Toolbar tb = view.findViewById(R.id.tb_information_details);
    ( (AppCompatActivity) activity ).setSupportActionBar(tb);
    Bundle bundle = activity.getIntent().getExtras();
    bundle.setClassLoader(SimpleInformation.class.getClassLoader());
    SimpleInformation simpleInformation = bundle.getParcelable(IntentKey.INFORMATION_DETAILS);
    Log.d(TAG, "onActivityCreated()\n: simpleInformation = " + simpleInformation);
    callback.onRequestInformation(simpleInformation.getInformationId());
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_information_details, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        getActivity().finish();
        break;
      case R.id.menu_increase_font_size:
        TextView tv = getView().findViewById(R.id.tv_information_details_content);
        float fontSize = activity.getResources().getDimension(R.dimen.ts_information_details_content_large);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        break;
      case R.id.menu_decrease_font_size:
        tv = getView().findViewById(R.id.tv_information_details_content);
        fontSize = activity.getResources().getDimension(R.dimen.ts_information_details_content_small);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayInformation(Information information) {
    Log.d(TAG, "displayInformation()\n: information = " + information);
    if (information == null)return;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    TextView tvTitle = activity.findViewById(R.id.tv_information_details_title);
    TextView tvDateTime = activity.findViewById(R.id.tv_information_details_date_time);
    TextView tvAuthor = activity.findViewById(R.id.tv_information_details_author);
    TextView tvContent = activity.findViewById(R.id.tv_information_details_content);
    tvTitle.setText(information.getTitle());
    tvDateTime.setText(dateFormat.format(information.getDateTime()));
    tvAuthor.setText(information.getAuthor());
    tvContent.setText(information.getContent());
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {

    }
  }

}