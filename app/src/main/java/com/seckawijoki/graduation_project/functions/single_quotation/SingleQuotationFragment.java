package com.seckawijoki.graduation_project.functions.single_quotation;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/15 at 22:58.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.seckawijoki.graduation_project.functions.main.ToolbarNavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class SingleQuotationFragment extends Fragment{
  private static final String TAG = "SingleQuotationFragment";
  private boolean hasFavored;
  private AppCompatActivity activity;
  public static SingleQuotationFragment newInstance() {
    Bundle args = new Bundle();
    SingleQuotationFragment fragment = new SingleQuotationFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_single_quotation, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = ( (AppCompatActivity) getActivity() );
    View view = getView();
    ViewPager vp = view.findViewById(R.id.vp_single_quotation);
    Toolbar tb = view.findViewById(R.id.tb_single_quotation);
    MagicIndicator mi = view.findViewById(R.id.indicator_single_quotation);

    activity.setSupportActionBar(tb);
    vp.setOffscreenPageLimit(1);
    SingleQuotationAdapter adapter = new SingleQuotationAdapter(
            getFragmentManager(),
            getActivity().getIntent().getLongExtra(IntentKey.STOCK_TABLE_ID, 1)
    );
    vp.setAdapter(adapter);
    CommonNavigator commonNavigator = new CommonNavigator(activity);
    commonNavigator.setBackgroundResource(R.drawable.shape_round_indicator);
    String[] labels = activity.getResources().getStringArray(R.array.array_single_quotation);
    ToolbarNavigatorAdapter navigatorAdapter = new ToolbarNavigatorAdapter(labels, vp);
    commonNavigator.setAdapter(navigatorAdapter);
    mi.setNavigator(commonNavigator);
    ViewPagerHelper.bind(mi, vp);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.i(TAG, "onActivityResult()\n: ");
    super.onActivityResult(requestCode, resultCode, data);
    if ( resultCode == Activity.RESULT_OK && requestCode == ActivityRequestCode.SEARCH ) {
      hasFavored = data.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_single_quotation, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        Intent intent = activity.getIntent();
        hasFavored = hasFavored | intent.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false);
        if ( hasFavored ) {
          intent.putExtra(IntentKey.HAS_FAVORED_STOCK, true);
          activity.setResult(Activity.RESULT_OK, intent);
        } else {
          activity.setResult(Activity.RESULT_CANCELED);
        }
        activity.finish();
        break;
      case R.id.menu_search:
        startActivityForResult(
                new Intent(IntentAction.SEARCH),
                ActivityRequestCode.SEARCH
        );
        break;
      case R.id.menu_message:
        startActivity(new Intent(IntentAction.MESSAGE));
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

}