package com.seckawijoki.graduation_project.functions.address_book;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 19:35.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class AddressBookActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_address_book);

    AddressBookFragment fragment = (AddressBookFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_address_book_fragment);
    if ( fragment == null ) {
      fragment = AddressBookFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_address_book_fragment
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}