package com.seckawijoki.graduation_project.functions.about_us;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/2/6 at 20:32.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;

class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {
  private Context context;
  private OnAboutUsOptionListener listener;
  private int currentVersionCode;
  private int latestVersionCode;
  public AboutUsAdapter setLatestVersionCode(int latestVersionCode) {
    this.latestVersionCode = latestVersionCode;
    return this;
  }

  AboutUsAdapter setOnAboutUsOptionListener(OnAboutUsOptionListener onAboutUsClickListener) {
    this.listener = onAboutUsClickListener;
    return this;
  }
  AboutUsAdapter(Activity activity) {
    this.context = activity;
    try {
      PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
      currentVersionCode = packageInfo.versionCode;
    } catch ( PackageManager.NameNotFoundException e ) {
      e.printStackTrace();
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_settings, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    switch ( position ) {
      case AboutUsOptions.VERSION_UPDATE:
        holder.tvLabel.setText(R.string.label_version_update);
        if (currentVersionCode < latestVersionCode){
          holder.tvValue.setText(R.string.label_find_latest_version);
          holder.tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_red));
        } else {
          holder.tvValue.setText(R.string.label_is_the_latest_version);
          holder.tvValue.setTextColor(ContextCompat.getColor(context, R.color.tc_stock_green));
        }
        break;
      case AboutUsOptions.GRADE_MY_APP:
        holder.tvLabel.setText(R.string.label_grade_my_app);

        break;
      case AboutUsOptions.DISCLAIMER_AND_PRIVACY_STATEMENT:
        holder.tvLabel.setText(R.string.label_disclaimer_and_privacy_statement);

        break;
      case AboutUsOptions.CONTACT_US:
        holder.tvLabel.setText(R.string.label_contact_us);
        break;
    }
    holder.layout.setOnClickListener(v -> listener.onAboutUsOptionClick(position));
  }

  @Override
  public int getItemCount() {
    return AboutUsOptions.count;
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