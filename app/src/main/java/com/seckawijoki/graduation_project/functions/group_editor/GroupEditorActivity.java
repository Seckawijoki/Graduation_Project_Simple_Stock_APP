package com.seckawijoki.graduation_project.functions.group_editor;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/13 at 16:46.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.utils.ActivityUtils;

public class GroupEditorActivity extends AppCompatActivity {
  private GroupEditorContract.Presenter presenter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_editor);

    GroupEditorFragment fragment = (GroupEditorFragment)
            getSupportFragmentManager().findFragmentById(R.id.layout_group_editor_fragment);
    if ( fragment == null ) {
      fragment = GroupEditorFragment.newInstance();
      ActivityUtils.addFragmentToActivity(
              getSupportFragmentManager(), fragment, R.id.layout_group_editor_fragment
      );
    }
    presenter = new GroupEditorPresenterImpl()
            .setView(fragment)
            .setModel(new GroupEditorModelImpl(this))
            .setModel(this)
            .initiate();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.destroy();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }
}