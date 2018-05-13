package com.seckawijoki.graduation_project.functions.settings;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 23:43.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

import org.litepal.crud.DataSupport;

class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
  private Context context;
  private static int marginTop;
  private OnSettingsOptionListener listener;
  SettingsAdapter(Context context) {
    this.context = context;
    marginTop = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            context.getResources().getDimension(R.dimen.m_t_layout_settings_list_item),
            context.getResources().getDisplayMetrics()
    );
  }

  SettingsAdapter setOnSettingsOptionListener(OnSettingsOptionListener onSettingsOptionListener) {
    this.listener = onSettingsOptionListener;
    return this;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_settings, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    ViewGroup.MarginLayoutParams p =
            (ViewGroup.MarginLayoutParams) holder.layout.getLayoutParams();
    switch ( position ) {
      case SettingsOptions.MULTI_ACCOUNT_MANAGEMENT:
        p.setMargins(0, marginTop, 0, 0);
        holder.layout.setLayoutParams(p);
        holder.tvLabel.setText(R.string.label_multi_account_management);
        User user = DataSupport.where("userId = ?", GlobalVariableTools.getUserId(context))
                .findFirst(User.class);
        holder.tvValue.setText(user.getNickname());
        break;
      case SettingsOptions.ACCOUNT_AND_SAFETY:
        p.setMargins(0, marginTop, 0, 0);
        holder.layout.setLayoutParams(p);
        holder.tvLabel.setText(R.string.label_account_and_safety);

        break;
      case SettingsOptions.PRIVACY:
        holder.tvLabel.setText(R.string.label_privacy);

        break;
      case SettingsOptions.COMMON_SETTINGS:
        holder.tvLabel.setText(R.string.label_common_settings);

        break;
      case SettingsOptions.ABOUT_US:
        holder.tvLabel.setText(R.string.label_about_us);
        break;
      case SettingsOptions.LOGOUT:
        p.setMargins(0, marginTop, 0, 0);
        holder.layout.setLayoutParams(p);
        holder.tvLabel.setText(R.string.label_logout);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.tvLabel.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        holder.tvLabel.setLayoutParams(layoutParams);
        break;
    }
    holder.layout.setOnClickListener(v -> listener.onSettingsOptionClick(position));
  }

  @Override
  public int getItemCount() {
    return SettingsOptions.count;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    View layout;
    TextView tvLabel;
    TextView tvValue;

    ViewHolder(View view) {
      super(view);
      layout = view.findViewById(R.id.layout_list_item_settings);
      tvLabel = view.findViewById(R.id.tv_settings_label);
      tvValue = view.findViewById(R.id.tv_settings_value);
    }
  }
}