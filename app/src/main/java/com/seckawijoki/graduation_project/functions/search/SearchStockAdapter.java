package com.seckawijoki.graduation_project.functions.search;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/7 at 20:44.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.server.StockType;
import com.seckawijoki.graduation_project.db.client.SearchStock;

import java.util.List;

class SearchStockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private List<SearchStock> searchStockList;
  private OnSearchStockListener listener;
  private Context context;
  private boolean isHistory;
  private TextView tvHead;
  private TextView tvTail;

  private interface ViewType {
    int HEAD = 0;
    int NORMAL = 1;
    int TAIL = 2;
  }

  public SearchStockAdapter switchHistoryAdapter(boolean history) {
    isHistory = history;
    return this;
  }

  public SearchStockAdapter setOnSearchStockListener(OnSearchStockListener listener) {
    this.listener = listener;
    return this;
  }

  public SearchStockAdapter setSearchStockList(List<SearchStock> searchStockList) {
    this.searchStockList = searchStockList;
    return this;
  }

  public SearchStockAdapter(Context context) {
    this.context = context;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    if (isHistory) {
      if ( viewType == ViewType.HEAD || viewType == ViewType.TAIL ) {
        view = LayoutInflater.from(context).inflate(R.layout.tv_stock_search_history_label, parent, false);
        return new HeadTailViewHolder(view);
      } else if ( viewType == ViewType.NORMAL ) {
        view = LayoutInflater.from(context).inflate(R.layout.recycler_item_stock_search, parent, false);
        return new ViewHolder(view);
      }
    } else {
      view = LayoutInflater.from(context).inflate(R.layout.recycler_item_stock_search, parent, false);
      return new ViewHolder(view);
    }
    return null;
  }

  @Override
  public int getItemViewType(int position) {
    if (isHistory) {
      if ( position == 0 ) {
        return ViewType.HEAD;
      } else if ( position == searchStockList.size() + 1 ) {
        return ViewType.TAIL;
      } else
        return ViewType.NORMAL;
    } else {
      return super.getItemViewType(position);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if ( isHistory ) {
      if ( position == 0 ) {
        tvHead = ( (HeadTailViewHolder) holder ).tv;
        tvHead.setText(context.getString(R.string.label_stock_search_history));
        tvHead.setGravity(Gravity.CENTER_VERTICAL);
        tvHead.setClickable(false);
      } else if ( position == searchStockList.size() + 1 ) {
        tvTail = ( (HeadTailViewHolder) holder ).tv;
        tvTail.setText(context.getString(R.string.action_clear_stock_search_history));
        tvTail.setGravity(Gravity.CENTER);
        tvTail.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            listener.onSearchStockHistoryClear();
          }
        });
      } else {
        onBindViewHolder(holder, searchStockList.get(position - 1), position-1);
      }
    } else {
      onBindViewHolder(holder, searchStockList.get(position), position);
    }
  }

  private void onBindViewHolder(RecyclerView.ViewHolder h, SearchStock searchStock, int position) {
    ViewHolder holder = (ViewHolder) h;
    switch ( searchStock.getStockType() ) {
      case StockType.SH:
        holder.tvStockType.setText(context.getString(R.string.label_sh));
        break;
      case StockType.SZ:
        holder.tvStockType.setText(context.getString(R.string.label_sz));
        break;
    }
    holder.tvStockId.setText(searchStock.getStockId());
    holder.tvStockName.setText(searchStock.getStockName());
    holder.chbFavor.setChecked(searchStock.isFavorite());
    holder.layout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onSearchStockClick(searchStock);
      }
    });
    holder.chbFavor.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onSearchStockFavor(searchStock, position, isHistory);
      }
    });
  }

  @Override
  public int getItemCount() {
    return searchStockList == null ? 0 :
            isHistory ? searchStockList.size() + 2 : searchStockList.size();
  }

  private static class HeadTailViewHolder extends RecyclerView.ViewHolder {
    TextView tv;

    HeadTailViewHolder(View itemView) {
      super(itemView);
      tv = itemView.findViewById(R.id.tv_stock_search_history_label);
    }
  }

  private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ViewGroup layout;
    TextView tvStockType;
    TextView tvStockId;
    TextView tvStockName;
    CheckBox chbFavor;

    ViewHolder(View view) {
      super(view);
      layout = view.findViewById(R.id.layout_list_item_stock_search);
//      layout = view.findViewById(R.id.layout_quotation_item);
      tvStockType = view.findViewById(R.id.tv_stock_type);
      tvStockId = view.findViewById(R.id.tv_stock_id);
      tvStockName = view.findViewById(R.id.tv_stock_name);
      chbFavor = view.findViewById(R.id.chb_add_to_favorite_stocks);

    }

    @Override
    public void onClick(View v) {

    }
  }
}