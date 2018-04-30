package com.seckawijoki.graduation_project.functions.search;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/7 at 20:40.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class SearchActivity extends AppCompatActivity {
  private SearchContract.Presenter presenter;
  private SearchFragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    fragment = (SearchFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_search_fragment);
    if ( fragment == null ) {
      fragment = SearchFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_search_fragment
      );
    }
    presenter = new SearchPresenterImpl()
            .setModel(new SearchModelImpl(this,
                    getIntent().getLongExtra(IntentKey.FAVORITE_GROUP_ID, -1L)))
            .setView(fragment)
            .initiate();
  }

  @Override
  public void onBackPressed() {
    fragment.destroy();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}