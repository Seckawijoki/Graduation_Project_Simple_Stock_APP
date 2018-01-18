package com.seckawijoki.graduation_project.functions.risk_rating;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/22.
 */

public class RiskRatingActivity extends AppCompatActivity{
  private static final String TAG = "RiskRatingActivity";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_risking_rate);
    adapter = new RiskRatingAdapter(
            getSupportFragmentManager(), onFragmentChangeListener);
    vp = (ViewPager) findViewById(R.id.vp_layer_to_be_chose);
    vp.setAdapter(adapter);
    vp.setOffscreenPageLimit(3);
    vp.addOnPageChangeListener(listener);
    vp.setPageTransformer(true, transformer);
  }


  private ViewPager vp;
  private RiskRatingAdapter adapter;
  private OnFragmentChangeListener onFragmentChangeListener
          = new OnFragmentChangeListener() {
    @Override
    public void onFragmentChange(int position, Bundle bundle) {
      if (position < 0)position = 0;
      if (position > 2)position = 2;
      vp.setCurrentItem(position, true);
      adapter.setChoice(bundle);
    }
  };
  private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
  };
  private ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
    private static final float MIN_SCALE = 0.75f;
    @Override
    public void transformPage(View page, float position) {
      int pageWidth = page.getWidth();

      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        page.setAlpha(0);

      } else if (position <= 0) { // [-1,0]
        // Use the default slide transition when moving to the left page
        page.setAlpha(1);
        page.setTranslationX(0);
        page.setScaleX(1);
        page.setScaleY(1);

      } else if (position <= 1) { // (0,1]
        // Fade the page out.
        page.setAlpha(1 - position);

        // Counteract the default slide transition
        page.setTranslationX(pageWidth * -position);

        // Scale the page down (between MIN_SCALE and 1)
        float scaleFactor = MIN_SCALE
                + (1 - MIN_SCALE) * (1 - Math.abs(position));
        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);

      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        page.setAlpha(0);
      }

    }
  };
}
