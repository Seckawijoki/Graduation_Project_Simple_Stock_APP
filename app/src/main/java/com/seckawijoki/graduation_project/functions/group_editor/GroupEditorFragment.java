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
import com.seckawijoki.graduation_project.constants.app.UnderlineDecoration;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import java.util.List;

public class GroupEditorFragment extends Fragment implements GroupEditorContract.View, OnGroupEditorClickListener, View.OnClickListener {
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
  private String groupName;
  private EditText etNewGroupName;
  private AlertDialog groupNameDialog;
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
    UnderlineDecoration itemDecoration = new UnderlineDecoration.Builder(activity)
            .setPaddingLeftRes(R.dimen.p_l_group_manager_list_item)
            .setPaddingRightRes(R.dimen.p_l_group_manager_list_item)
            .setLineSizeRes(R.dimen.h_group_list_item_divider)
            .setColorRes(R.color.bg_group_manager_editor_fragment)
            .build();
    rv.addItemDecoration(itemDecoration);
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
      miDelete.setEnabled(false);
      miDelete.getIcon().setAlpha(100);
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
    int i = 0;
    if (i == 0)return;
    View view = activity.getLayoutInflater().inflate(R.layout.alert_dialog_on_group_name_change, null);
    etNewGroupName = view.findViewById(R.id.et_group_name);
    etNewGroupName.setText(groupName);
    view.findViewById(R.id.btn_cancel).setOnClickListener(this);
    view.findViewById(R.id.btn_confirm).setOnClickListener(this);
    groupNameDialog = new AlertDialog.Builder(activity)
            .setView(view)
            .setTitle(R.string.label_input_group_name)
            .create();
    groupNameDialog.show();
    etNewGroupName.setSelection(groupName.length());
    etNewGroupName.requestFocus();
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

  @Override
  public void onClick(View v) {
    switch ( v.getId() ){
      case R.id.btn_confirm:
        groupNameDialog.dismiss();
        String newGroupName = etNewGroupName.getText().toString();
        if ( TextUtils.equals(groupName, newGroupName)){
          return;
        }
        callback.onRequestRenameFavoriteGroup(
                groupName,
                newGroupName);
        break;
      case R.id.btn_cancel:
        groupNameDialog.dismiss();
        break;
    }
  }
}