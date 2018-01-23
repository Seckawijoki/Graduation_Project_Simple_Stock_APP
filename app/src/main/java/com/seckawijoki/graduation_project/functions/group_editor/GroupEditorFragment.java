package com.seckawijoki.graduation_project.functions.group_editor;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/11 at 18:05.
 */

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.util.ToastUtils;

import java.util.List;

public class GroupEditorFragment extends Fragment implements GroupEditorContract.View, OnGroupEditorClickListener {
  private static final String TAG = "GroupEditorFragment";
  private ActionCallback callback;
  private AppCompatActivity activity;
  private View view;
  private Toolbar tb;
  private RecyclerView rv;
  private GroupEditorAdapter adapter;
  private boolean hasDeleted;
  private boolean hasRenamed;
  private MenuItem miDelete;
  public static GroupEditorFragment newInstance() {
    Bundle args = new Bundle();
    GroupEditorFragment fragment = new GroupEditorFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return view = inflater.inflate(R.layout.fragment_group_editor, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = (AppCompatActivity) getActivity();
    tb = view.findViewById(R.id.tb_group_editor);
    activity.setSupportActionBar(tb);
    rv = view.findViewById(R.id.rv_group_editor);
    LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rv.setLayoutManager(layoutManager);
    adapter = new GroupEditorAdapter(activity)
            .setOnGroupEditorClickListener(this);
    rv.setAdapter(adapter);
    callback.onRequestFavoriteGroups();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_group_editor, menu);
    super.onCreateOptionsMenu(menu, inflater);
    miDelete = menu.findItem(R.id.menu_delete);
    miDelete.getIcon().setAlpha(100);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ){
      case R.id.menu_delete:
        callback.onRequestDeleteFavoriteGroups(
                adapter.getSelectedGroup()
        );
        break;
      case android.R.id.home:
        if ( hasDeleted || hasRenamed) {
          Intent data = new Intent();
          data.putExtra(IntentKey.HAS_DELETED_GROUP, hasDeleted);
          data.putExtra(IntentKey.HAS_RENAMED_GROUP, hasRenamed);
          activity.setResult(Activity.RESULT_OK, data);
        } else {
          activity.setResult(Activity.RESULT_CANCELED);
        }
        activity.finish();
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void displayFavoriteGroupName(List<FavoriteGroupType> favoriteGroupTypeList){
    rv.setAdapter(
            adapter = new GroupEditorAdapter(activity)
                    .setFavoriteGroupTypeList(favoriteGroupTypeList)
                    .setOnGroupEditorClickListener(this));
  }

  @Override
  public void displayDeleteFavoriteGroups(boolean successful){
    Log.d(TAG, "displayDeleteFavoriteGroups(): successful = " + successful);
    if (successful) {
      hasDeleted = true;
      ToastUtils.show(activity, R.string.msg_succeed_in_deleting);
      callback.onRequestFavoriteGroups();
    } else {
      ToastUtils.show(activity, R.string.error_failed_to_delete);
    }
  }


  @Override
  public void displayRenameFavoriteGroup(boolean successful){
    if (successful){
      ToastUtils.show(activity, R.string.msg_succeed_in_renaming_group);
      hasRenamed = true;
      callback.onRequestFavoriteGroups();
    } else {
      ToastUtils.show(activity, R.string.error_failed_to_rename_group);
    }
  }

  @Override
  public void displayFavoriteGroups(List<FavoriteGroupType> favoriteGroupTypeList) {
    adapter.setFavoriteGroupTypeList(favoriteGroupTypeList).notifyDataSetChanged();
  }


  @Override
  public void onGroupRename(String groupName) {
    View v = activity.getLayoutInflater().inflate(R.layout.dialog_on_group_name_change, null);
    final EditText et = v.findViewById(R.id.et_group_name);
    et.setText(groupName);
    et.setSelection(groupName.length());
    String oldGroupName = groupName;
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(R.string.label_input_group_name)
            .setPositiveButton(R.string.action_confirm, (dialog, which) -> {
              Log.i(TAG, "onGroupRename(): confirm");
              String newGroupName = et.getText().toString();
              if ( TextUtils.equals(oldGroupName, newGroupName)){
                return;
              }
              callback.onRequestRenameFavoriteGroup(
                      oldGroupName,
                      newGroupName);
            }).setNegativeButton(R.string.action_cancel, (dialog, which) -> {
              Log.i(TAG, "onGroupRename(): cancel");

            });
    AlertDialog alertDialog = dialogBuilder.create();
    alertDialog.show();
    et.requestFocus();
  }

  @Override
  public void onGroupChoose(boolean hasSelection) {
    miDelete.setEnabled(hasSelection);
    miDelete.getIcon().setAlpha(hasSelection ? 255 : 100);
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
}