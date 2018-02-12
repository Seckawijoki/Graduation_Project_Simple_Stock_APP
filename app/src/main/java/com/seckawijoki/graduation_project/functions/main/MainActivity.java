package com.seckawijoki.graduation_project.functions.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.LinearLayout;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.functions.assets.AssetsFragment;
import com.seckawijoki.graduation_project.functions.information.InformationFragment;
import com.seckawijoki.graduation_project.functions.latest_information.LatestInformationFragment;
import com.seckawijoki.graduation_project.functions.mine.MineFragment;
import com.seckawijoki.graduation_project.functions.quotations.QuotationsFragment;
import com.seckawijoki.graduation_project.functions.recommendations.RecommendationsFragment;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/22.
 */

public class MainActivity extends AppCompatActivity{
  private static final String TAG = "MainActivity";
  private FragmentContainerHelper fragmentContainerHelper;
  private SparseArray<Fragment> fragmentList;
  private String[] labels;
  private long firstExitClickTime;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    Log.e(TAG, "onCreate: ");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    labels = getResources().getStringArray(R.array.array_main_labels);
    initFragments();
    MagicIndicator magicIndicator = findViewById(R.id.indicator_main);
    CommonNavigator commonNavigator = new CommonNavigator(this);
    commonNavigator.setAdapter(adapter);
    magicIndicator.setNavigator(commonNavigator);

    LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
    titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    titleContainer.setDividerPadding(UIUtil.dip2px(this, 24));

    fragmentContainerHelper = new FragmentContainerHelper();
    fragmentContainerHelper.attachMagicIndicator(magicIndicator);
    int position = GlobalVariableTools.getMainFragment(this);
    fragmentContainerHelper.handlePageSelected(position, true);
    switchPages(position);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public void onBackPressed() {
    if (System.currentTimeMillis() - firstExitClickTime <= 1000) {
      super.onBackPressed();
    } else {
      firstExitClickTime = System.currentTimeMillis();
      ToastUtils.show(this, R.string.msg_one_more_time_click_to_exit);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != RESULT_OK)return;
    if (requestCode == ActivityRequestCode.SINGLE_QUOTATION ){
        fragmentList.get(3).onActivityResult(requestCode, resultCode, data);
    }
  }

  private void initFragments(){
      fragmentList = new SparseArray<>();
      for ( int i = 0 ; i < 5 ; ++i ) {
        Fragment fragment;
        switch ( i ){
          case 0:
            fragment = RecommendationsFragment.getInstance();break;
          case 1:
            fragment = AssetsFragment.newInstance();break;
          default:
          case 2:
            fragment = QuotationsFragment.newInstance();break;
          case 3:
            fragment = InformationFragment.newInstance();break;
          case 4:
            fragment = MineFragment.getInstance();break;
        }
        fragmentList.put(i, fragment);
      }
    }

  private void switchPages(int position) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment fragment;
    for (int i = 0, j = fragmentList.size(); i < j; i++) {
      if (i == position) {
        continue;
      }
      fragment = fragmentList.get(i);
      if (fragment.isAdded()) {
        fragmentTransaction.hide(fragment);
      }
    }
    fragment = fragmentList.get(position);
    if (fragment.isAdded()) {
      fragmentTransaction.show(fragment);
    } else {
      fragmentTransaction.add(R.id.layout_main, fragment);
    }
    GlobalVariableTools.setMainFragment(this, position);
    fragmentTransaction.commitNow();
  }

  private CommonNavigatorAdapter adapter = new CommonNavigatorAdapter() {

    @Override
    public int getCount() {
      return labels == null ? 0 : labels.length;
    }

    @Override
    public float getTitleWeight(Context context, int index) {
      return 1;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
      ClipPagerTitleView pagerTitleView = new ClipPagerTitleView(MainActivity.this);
      pagerTitleView.setText(labels[index]);
      pagerTitleView.setClipColor(ContextCompat.getColor(MainActivity.this, R.color.bg_main_labels_checked));
      pagerTitleView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.bg_main_labels));
      pagerTitleView.setOnClickListener(v -> {
        fragmentContainerHelper.handlePageSelected(index);
        switchPages(index);
      });
      return pagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
      LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
      linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
      return null;
    }
  };

}
