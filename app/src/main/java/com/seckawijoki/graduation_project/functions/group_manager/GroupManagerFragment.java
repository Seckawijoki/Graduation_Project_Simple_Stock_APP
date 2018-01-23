package com.seckawijoki.graduation_project.functions.group_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.ActivityIntent;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.util.ToastUtils;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/10 at 15:02.
 */

public class GroupManagerFragment extends Fragment implements GroupManagerContract.View,OnGroupManagerClickListener {
  private static final String TAG = "GroupManagerFragment";
  private GroupManagerContract.View.ActionCallback callback;
  private AppCompatActivity activity;
  private View view;
  private RecyclerView rv;
  private GroupManagerAdapter adapter;
  private boolean hasAdded = false;
  public static GroupManagerFragment newInstance() {
    Bundle args = new Bundle();
    GroupManagerFragment fragment = new GroupManagerFragment();
    fragment.setArguments(args);
    return fragment;
  }
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return view = inflater.inflate(R.layout.fragment_group_manager, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = (AppCompatActivity) getActivity();
    Toolbar tb = view.findViewById(R.id.tb_group_manager);
    activity.setSupportActionBar(tb);
    rv = view.findViewById(R.id.rv_favorite_group_name_and_count);
    LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rv.setLayoutManager(layoutManager);
    adapter = new GroupManagerAdapter(getContext())
            .setOnGroupManagerClickListener(this);
    rv.setAdapter(adapter);
    callback.onRequestFavoriteGroups();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_group_manager, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ){
      case android.R.id.home:
        Log.d(TAG, "onOptionsItemSelected(): hasAdded = " + hasAdded);
        Intent data = activity.getIntent();
        boolean hasDeleted = data.getBooleanExtra(IntentKey.HAS_DELETED_GROUP, false);
        boolean hasRenamed = data.getBooleanExtra(IntentKey.HAS_RENAMED_GROUP, false);
        if ( hasAdded || hasDeleted || hasRenamed){
          data.putExtra(IntentKey.HAS_ADDED_NEW_GROUP, hasAdded);
          activity.setResult(Activity.RESULT_OK, data);
        } else {
          activity.setResult(Activity.RESULT_CANCELED);
        }
        activity.finish();
        break;
      case R.id.menu_edit:
        Log.d(TAG, "onOptionsItemSelected(): item.getItemId() = " + item.getItemId());
        activity.startActivityForResult(
                new Intent(ActivityIntent.GROUP_EDITOR),
                ActivityRequestCode.GROUP_EDITOR
        );
        break;
      default:return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onGroupClick(String groupName) {
    // TODO: 2017/12/10
  }

  @Override
  public void onAddNewGroup() {
    // TODO: 2017/12/10
    View v = activity.getLayoutInflater().inflate(R.layout.dialog_on_group_name_change, null);
    final EditText et = v.findViewById(R.id.et_group_name);
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(R.string.label_input_group_name)
            .setPositiveButton(R.string.action_confirm, (dialog, which) -> {
              callback.onRequestAddNewGroup(
                      et.getText().toString());
            })
            .setNegativeButton(R.string.action_cancel, (dialog, which) -> {

            });
    AlertDialog alertDialog = dialogBuilder.create();
    alertDialog.show();
    et.requestFocus();
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
  public void displayAddNewGroup(boolean successful, int msgId) {
    if (successful){
//      adapter.addNewGroup() .notifyDataSetChanged();
      hasAdded = true;
      callback.onRequestFavoriteGroups();
    }
    ToastUtils.show(activity, msgId);
  }

  @Override
  public void displayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList) {
    rv.setAdapter(
            adapter = new GroupManagerAdapter(activity)
                    .setFavoriteGroupTypeList(favoriteGroupTypeList)
                    .setOnGroupManagerClickListener(this));
  }
}
