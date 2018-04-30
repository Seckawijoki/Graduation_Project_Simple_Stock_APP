package com.seckawijoki.graduation_project.functions.quotation_details;
/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/19 at 19:21.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.seckawijoki.graduation_project.R;

class KLineChoiceAdapter extends RecyclerView.Adapter<KLineChoiceAdapter.ViewHolder> {
  private OnKLineChooseListener listener;
  private Context context;
  private int currentChoice = 0;
  private String[] kLineChoiceLabels;

  KLineChoiceAdapter(Context context) {
    this.context = context;
    kLineChoiceLabels = context.getResources().getStringArray(R.array.array_k_line_choice_labels);
  }

  KLineChoiceAdapter setOnKLineChooseListener(OnKLineChooseListener listener) {
    this.listener = listener;
    return this;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_k_line_choice, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if ( position < kLineChoiceLabels.length ) {
      holder.chbChoice.setText(kLineChoiceLabels[position]);
    }
    holder.chbChoice.setChecked(currentChoice == position);
    final int p = position;
    holder.chbChoice.setOnClickListener(v -> {
              currentChoice = p;
              if ( listener != null ) {
                listener.onKLineChoose(p);
              }
            }
    );
  }

  @Override
  public int getItemCount() {
    return kLineChoiceLabels == null ? 0 : kLineChoiceLabels.length;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    CheckBox chbChoice;

    ViewHolder(View view) {
      super(view);
      chbChoice = view.findViewById(R.id.chb_k_line_choice);
    }
  }
}