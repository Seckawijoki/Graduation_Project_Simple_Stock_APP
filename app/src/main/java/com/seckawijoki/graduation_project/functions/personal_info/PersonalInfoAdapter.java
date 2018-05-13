package com.seckawijoki.graduation_project.functions.personal_info;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/20 at 22:22.
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

import org.litepal.crud.DataSupport;

public class PersonalInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final String TAG = "PersonalInfoAdapter";
  private Context context;
  private User user;
  private OnPersonalInfoListener listener;
  PersonalInfoAdapter setUser(){
    user = DataSupport
            .where("userId = ?", GlobalVariableTools.getUserId(context))
            .findFirst(User.class);
    return this;
  }
  PersonalInfoAdapter setOnPersonalInfoListener(OnPersonalInfoListener onPersonalInfoListener) {
    this.listener = onPersonalInfoListener;
    return this;
  }

  PersonalInfoAdapter(Context context) {
    this.context = context;
    setUser();
  }

  @Override
  public int getItemViewType(int position) {
    return position;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if ( viewType == PersonalInfoOptions.PORTRAIT ) {
      View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_user_portrait, parent, false);
      return new PortraitViewHolder(view);
    } else {
      View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_settings, parent, false);
      return new DefaultViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
//    if (user == null)return;
    View.OnClickListener onClickListener = v -> listener.onPersonalInfoClick(position, user);
    if ( position == PersonalInfoOptions.PORTRAIT ) {
      PortraitViewHolder holder = ( (PortraitViewHolder) h );
      String portraitUri = user.getPortraitUri();
      if ( TextUtils.isEmpty(portraitUri) ) {
        holder.img.setImageResource(R.drawable.ic_default_image);
      } else {
        holder.img.setImageBitmap(BitmapFactory.decodeFile(portraitUri));
      }
      holder.layout.setOnClickListener(onClickListener);
    } else {
      DefaultViewHolder holder = ( (DefaultViewHolder) h );
      holder.layout.setOnClickListener(onClickListener);
      switch ( position ) {
        default:
        case PersonalInfoOptions.NICKNAME:
          holder.tvLabel.setText(R.string.label_nickname);
          holder.tvValue.setText(user.getNickname());
          break;
        case PersonalInfoOptions.USER_ID:
          holder.tvLabel.setText(R.string.label_user_id);
          holder.tvValue.setText(user.getUserId() + "");
          holder.layout.setClickable(false);
          holder.layout.setOnClickListener(null);
          holder.layout.setBackgroundColor(Color.WHITE);
          break;
        case PersonalInfoOptions.PHONE:
          holder.tvLabel.setText(R.string.label_phone);
          holder.tvValue.setText(user.getPhone());
          holder.layout.setClickable(false);
          holder.layout.setOnClickListener(null);
          holder.layout.setBackgroundColor(Color.WHITE);
          break;
        case PersonalInfoOptions.EMAIL:
          holder.tvLabel.setText(R.string.label_email);
          if ( TextUtils.isEmpty(user.getEmail()) ) {
            holder.tvValue.setText(R.string.label_not_bound_with_email);
            holder.tvValue.setTextColor(Color.RED);
          } else {
            holder.tvValue.setText(user.getEmail());
            holder.tvValue.setTextColor(Color.BLACK);
          }
          break;
      }
    }
  }

  @Override
  public int getItemCount() {
    return PersonalInfoOptions.count;
  }

  static class DefaultViewHolder extends RecyclerView.ViewHolder {
    View layout;
    TextView tvLabel;
    TextView tvValue;
    DefaultViewHolder(View view) {
      super(view);
      layout = view.findViewById(R.id.layout_list_item_settings);
      tvLabel = view.findViewById(R.id.tv_settings_label);
      tvValue = view.findViewById(R.id.tv_settings_value);
    }
  }

  static class PortraitViewHolder extends RecyclerView.ViewHolder {
    View layout;
    ImageView img;

    PortraitViewHolder(View view) {
      super(view);
      layout = view.findViewById(R.id.layout_list_item_user_portrait);
      img = view.findViewById(R.id.img_portrait);
    }

  }

  /**
   * Created by 瑶琴频曲羽衣魂 on 2018/1/20 at 23:09.
   */
  interface PersonalInfoOptions {
    int count = 4;
    int PORTRAIT = 0;
    int NICKNAME = 1;
    int USER_ID = 2;
    int PHONE = 3;
    int EMAIL = 4;
  }
}