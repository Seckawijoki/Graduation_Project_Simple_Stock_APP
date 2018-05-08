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
import com.seckawijoki.graduation_project.constants.app.UnderlineDecoration;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;
import com.seckawijoki.graduation_project.utils.ToastUtils;

import java.util.List;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/10 at 15:02.
 */

public class GroupManagerFragment extends Fragment implements GroupManagerContract.View,OnGroupManagerClickListener, View.OnClickListener {
  private static final String TAG = "GroupManagerFragment";
  private GroupManagerContract.View.ActionCallback callback;
  private AppCompatActivity activity;
  private View view;
  private RecyclerView rv;
  private GroupManagerAdapter adapter;
  private boolean hasAdded = false;
  private AlertDialog alertDialog;
  private EditText etNewGroupName;

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
    UnderlineDecoration itemDecoration = new UnderlineDecoration.Builder(activity)
            .setPaddingLeftRes(R.dimen.p_l_group_manager_list_item)
            .setLineSizeRes(R.dimen.h_group_list_item_divider)
            .setColorRes(R.color.bg_group_manager_editor_fragment)
            .build();
    rv.addItemDecoration(itemDecoration);
    adapter = new GroupManagerAdapter(getContext())
            .setOnGroupManagerClickListener(this);
    rv.setAdapter(adapter);
    callback.onRequestFavoriteGroups();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.i(TAG, "onActivityResult(): ");
    if ( resultCode == Activity.RESULT_OK && requestCode == ActivityRequestCode.GROUP_EDITOR ) {
      activity.setIntent(data);
      callback.onRequestFavoriteGroups();
    }
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
        Intent data = activity.getIntent();
        boolean hasDeleted = data.getBooleanExtra(IntentKey.HAS_DELETED_GROUP, false);
        boolean hasRenamed = data.getBooleanExtra(IntentKey.HAS_RENAMED_GROUP, false);
        Log.d(TAG, "onOptionsItemSelected(): hasAdded = " + hasAdded);
        Log.d(TAG, "onOptionsItemSelected()\n: hasDeleted = " + hasDeleted);
        Log.d(TAG, "onOptionsItemSelected()\n: hasRenamed = " + hasRenamed);
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
        startActivityForResult(
                new Intent(IntentAction.GROUP_EDITOR),
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
    View v = activity.getLayoutInflater().inflate(R.layout.alert_dialog_on_group_name_change, null);
    etNewGroupName = v.findViewById(R.id.et_group_name);
    v.findViewById(R.id.btn_confirm).setOnClickListener(this);
    v.findViewById(R.id.btn_cancel).setOnClickListener(this);
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(R.string.label_input_group_name);
    alertDialog = dialogBuilder.create();
    alertDialog.show();
    etNewGroupName.requestFocus();
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

  @Override
  public void onClick(View v) {
    switch ( v.getId() ){
      case R.id.btn_confirm:
        alertDialog.dismiss();
        callback.onRequestAddNewGroup(etNewGroupName.getText().toString());
        break;
      case R.id.btn_cancel:
        alertDialog.dismiss();
        break;
    }
  }
}
