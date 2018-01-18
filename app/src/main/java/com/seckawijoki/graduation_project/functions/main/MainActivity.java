package com.seckawijoki.graduation_project.functions.main;

import android.content.Context;
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
import com.seckawijoki.graduation_project.functions.assets.AssetsFragment;
import com.seckawijoki.graduation_project.functions.information.InformationFragment;
import com.seckawijoki.graduation_project.functions.mine.MineFragment;
import com.seckawijoki.graduation_project.functions.quotations.QuotationsFragment;
import com.seckawijoki.graduation_project.functions.recommendations.RecommendationsFragment;
import com.seckawijoki.graduation_project.util.GlobalVariableUtils;
import com.seckawijoki.graduation_project.util.ToastUtils;

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
  private int position;
  private String[] labels;
  private long firstExitClickTime;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    Log.e(TAG, "onCreate: ");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    labels = getResources().getStringArray(R.array.array_main_labels);
    initFragments();
    MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.indicator_main);
    CommonNavigator commonNavigator = new CommonNavigator(this);
    commonNavigator.setAdapter(adapter);
    magicIndicator.setNavigator(commonNavigator);

    LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
    titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    titleContainer.setDividerPadding(UIUtil.dip2px(this, 24));

    fragmentContainerHelper = new FragmentContainerHelper();
    fragmentContainerHelper.attachMagicIndicator(magicIndicator);
    position = GlobalVariableUtils.getMainFragment(this);
    fragmentContainerHelper.handlePageSelected(position, true);
    switchPages(position);
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    GlobalVariableUtils.setMainFragment(this, position);
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
            fragment = InformationFragment.getInstance();break;
          case 4:
            fragment = MineFragment.getInstance();break;
        }
        fragmentList.put(i, fragment);
      }
    }

  private void switchPages(int index) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment fragment;
    for (int i = 0, j = fragmentList.size(); i < j; i++) {
      if (i == index) {
        continue;
      }
      fragment = fragmentList.get(i);
      if (fragment.isAdded()) {
        fragmentTransaction.hide(fragment);
      }
    }
    fragment = fragmentList.get(index);
    if (fragment.isAdded()) {
      fragmentTransaction.show(fragment);
    } else {
      fragmentTransaction.add(R.id.layout_main, fragment);
    }
    fragmentTransaction.commitAllowingStateLoss();
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
        position = index;
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
