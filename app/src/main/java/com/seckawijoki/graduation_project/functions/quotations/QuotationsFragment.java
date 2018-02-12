package com.seckawijoki.graduation_project.functions.quotations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuotationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuotationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuotationsFragment extends Fragment implements ViewPager.OnPageChangeListener{
  private static final String TAG = "QuotationsFragment";
  private OnFragmentInteractionListener mListener;
  private QuotationsAdapter adapter;
  public QuotationsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment QuotationsFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static QuotationsFragment newInstance() {
    QuotationsFragment fragment = new QuotationsFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_quotations, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    AppCompatActivity activity = ( (AppCompatActivity) getActivity() );
    View view = getView();
    ViewPager vp = view.findViewById(R.id.vp_quotations);
    Toolbar tb = view.findViewById(R.id.tb_quotations);
    MagicIndicator mi = view.findViewById(R.id.indicator_quotations);

    activity.setSupportActionBar(tb);
    adapter = new QuotationsAdapter(getFragmentManager());
    vp.setOffscreenPageLimit(2);
    vp.addOnPageChangeListener(this);
    vp.setAdapter(adapter);
    CommonNavigator commonNavigator = new CommonNavigator(activity);
    commonNavigator.setBackgroundResource(R.drawable.shape_round_indicator);
    String[] labels = activity.getResources().getStringArray(R.array.array_quotations);
    ToolbarNavigatorAdapter navigatorAdapter = new ToolbarNavigatorAdapter(labels, vp);
    commonNavigator.setAdapter(navigatorAdapter);
    mi.setNavigator(commonNavigator);
    ViewPagerHelper.bind(mi, vp);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_quotations, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case R.id.menu_search:
        startActivityForResult(
                new Intent(IntentAction.SEARCH),
                ActivityRequestCode.SEARCH
        );
        break;
      case R.id.menu_message:
        startActivity(new Intent(IntentAction.MESSAGE));
        break;
      case R.id.menu_refresh:
        adapter.getFavoriteFragment().getMvpModel().requestFavoriteGroups();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.i(TAG, "onActivityResult()\n: ");
    if ( resultCode == Activity.RESULT_OK && requestCode == ActivityRequestCode.SEARCH &&
            data.getBooleanExtra(IntentKey.HAS_FAVORED_STOCK, false) ) {
      adapter.getFavoriteFragment().refreshQuotationLists();
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden)getActivity().invalidateOptionsMenu();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    /*
    if ( context instanceof OnFragmentInteractionListener ) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement OnFragmentInteractionListener");
    }
    */
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {

  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }


}
