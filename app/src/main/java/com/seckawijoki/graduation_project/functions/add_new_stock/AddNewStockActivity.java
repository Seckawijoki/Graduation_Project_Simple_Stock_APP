package com.seckawijoki.graduation_project.functions.add_new_stock;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/14 at 12:12.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.util.ActivityUtils;

public class AddNewStockActivity extends AppCompatActivity {
  private AddNewStockContract.Presenter presenter;
  private AddNewStockContract.Model model;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_new_stock);

    AddNewStockFragment fragment = (AddNewStockFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_add_new_stock_fragment);
    if ( fragment == null ) {
      fragment = AddNewStockFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_add_new_stock_fragment
      );
    }
    presenter = new AddNewStockPresenterImpl()
            .setModel(model = new AddNewStockModelImpl())
            .setView(fragment)
            .initiate();
  }
}