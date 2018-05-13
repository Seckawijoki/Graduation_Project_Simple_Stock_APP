package com.seckawijoki.graduation_project.functions.transaction;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/25 at 14:48.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.db.client.UserTransaction;
import com.seckawijoki.graduation_project.tools.GlobalVariableTools;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
  private Context context;
  private List<UserTransaction> transactionList;
  private OnTransactionClickListener listener;

  public TransactionAdapter setOnTransactionClickListener(OnTransactionClickListener onTransactionClickListener) {
    this.listener = onTransactionClickListener;
    return this;
  }

  TransactionAdapter(Context context) {
    this.context = context;
  }

  TransactionAdapter setTransactionList(List<UserTransaction> transactionList) {
    this.transactionList = transactionList;
    return this;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_transaction, parent, false);
    return new ViewHolder(view);
  }



  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    if ( transactionList == null ) return;
    UserTransaction transaction = transactionList.get(position);
    try {
      holder.img.setImageBitmap(transaction.getPortrait());
    } catch ( NullPointerException ignored ) {
      holder.img.setImageResource(R.mipmap.ic_launcher);
    }
    holder.tvNickname.setText(transaction.getNickname());
    holder.tvStockInfo.setText(
            String.format(
                    context.getString(R.string.format_stock_name_id_and_type),
                    transaction.getStockName(),
                    transaction.getStockId(),
                    GlobalVariableTools.getStockType(context, transaction.getStockType())
            )
    );
    holder.tvValuePrice.setText(
            String.format(
                    context.getString(R.string.format_transaction_price),
                    transaction.getTradePrice()
            )
    );
    holder.tvDateTime.setText(new PrettyTime().format(new Date(transaction.getTradeDateTime())).trim());
    int tradeCount = transaction.getTradeCount();
    if ( tradeCount >= 0 ) {
      holder.tvLabelBuyOrSell.setText(R.string.label_buy);
      holder.tvFollow.setBackgroundResource(R.drawable.shape_follow_to_buy);
      holder.tvFollow.setText(R.string.action_follow_to_buy);
    } else {
      holder.tvLabelBuyOrSell.setText(R.string.label_sell);
      holder.tvFollow.setBackgroundResource(R.drawable.shape_follow_to_sell);
      holder.tvFollow.setText(R.string.action_follow_to_sell);
    }
    final long stockTableId = transaction.getStockTableId();
    holder.tvStockInfo.setOnClickListener(v -> listener.onStockClick(stockTableId));
    holder.tvFollow.setOnClickListener(v -> listener.onFollowToBuyOrSell(transaction));
  }

  @Override
  public int getItemCount() {
    return transactionList == null ? 0 : transactionList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView img;
    TextView tvNickname;
    TextView tvLabelBuyOrSell;
    TextView tvStockInfo;
    //    TextView tvLabelPrice;
    TextView tvValuePrice;
    TextView tvDateTime;
    TextView tvFollow;

    ViewHolder(View view) {
      super(view);
      img = view.findViewById(R.id.img_transaction_user_portrait);
      tvNickname = view.findViewById(R.id.tv_nickname);
      tvLabelBuyOrSell = view.findViewById(R.id.tv_buy_or_sell);
      tvStockInfo = view.findViewById(R.id.tv_stock_name_id_and_type);
//      tvLabelPrice = view.findViewById(R.id.tv_label_transaction_price);
      tvValuePrice = view.findViewById(R.id.tv_value_transaction_price);
      tvDateTime = view.findViewById(R.id.tv_transaction_date_time);
      tvFollow = view.findViewById(R.id.tv_follow_to_buy_or_sell);
    }

  }
}