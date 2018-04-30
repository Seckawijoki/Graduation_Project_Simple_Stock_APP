package com.seckawijoki.graduation_project.functions.group_manager;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/10 at 15:43.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.server.FavoriteGroupType;

import org.litepal.crud.DataSupport;

import java.util.List;

public class GroupManagerAdapter extends RecyclerView.Adapter<GroupManagerAdapter.ViewHolder> {
  private static final String TAG = "GroupManagerAdapter";
  private Context context;
  private List<FavoriteGroupType> favoriteGroupTypeList;
  private OnGroupManagerClickListener listener;

  public GroupManagerAdapter(Context context) {
    this.context = context;
  }

  public GroupManagerAdapter setFavoriteGroupTypeList(List<FavoriteGroupType> favoriteGroupTypeList) {
    this.favoriteGroupTypeList = favoriteGroupTypeList;
    return this;
  }

  public GroupManagerAdapter setOnGroupManagerClickListener(OnGroupManagerClickListener listener) {
    this.listener = listener;
    return this;
  }

  public GroupManagerAdapter addNewGroup() {
    favoriteGroupTypeList = DataSupport.findAll(FavoriteGroupType.class);
    return this;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_group_manager, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Log.i(TAG, "onBindViewHolder(): ");
    if ( ( favoriteGroupTypeList == null && position == 0 )
            || ( favoriteGroupTypeList != null && position == favoriteGroupTypeList.size() ) ) {
      holder.tv.setText(context.getString(R.string.action_add_new_group));
      holder.tv.setTextColor(ContextCompat.getColor(context, R.color.tc_add_new_group));
      holder.tv.setGravity(Gravity.CENTER_HORIZONTAL);
      holder.tv.setOnClickListener(v -> listener.onAddNewGroup());
    } else if ( favoriteGroupTypeList != null && position < favoriteGroupTypeList.size() ) {
      FavoriteGroupType favoriteGroupType = favoriteGroupTypeList.get(position);
      int count = favoriteGroupType.getStockCount();
      String groupName = favoriteGroupType.getFavoriteGroupName();
      holder.tv.setText(String.format(
              context.getString(R.string.format_group_name),
              groupName, count
      ));
      holder.tv.setOnClickListener(v -> listener.onGroupClick(groupName));
    }
  }

  @Override
  public int getItemCount() {
    return favoriteGroupTypeList == null ? 1 : favoriteGroupTypeList.size() + 1;
  }

  static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv;

    ViewHolder(View view) {
      super(view);
      tv = view.findViewById(R.id.tv_group_name);
    }

    @Override
    public void onClick(View v) {
    }
  }
}