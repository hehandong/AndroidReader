package com.hehandong.androidreader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.module.WxMenuListModel;
import com.hehandong.androidreader.listener.OnItemClickListener;


public class WxListAdapter extends RecyclerView.Adapter<WxListAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;

    private SparseBooleanArray mBooleanArray;

    private OnItemClickListener mClickListener;

    private int mLastCheckedPosition = -1;

    private WxMenuListModel mArticleListModel;

    public WxListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setDatas(WxMenuListModel model) {
        mArticleListModel = model;

        mBooleanArray = new SparseBooleanArray(mArticleListModel.getData().size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_menu, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!mBooleanArray.get(position)) {
            holder.viewLine.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundResource(R.color.bg_app);
            holder.tvName.setTextColor(Color.BLACK);
        } else {
            holder.viewLine.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }

        if (mArticleListModel != null && mArticleListModel.getData() != null) {
            holder.tvName.setText(mArticleListModel.getData().get(position).getName());
        }

    }

    @Override
    public int getItemCount() {
        if (mArticleListModel != null && mArticleListModel.getData() != null) {
            return mArticleListModel.getData().size();
        }
        return 0;
    }

    public void setItemChecked(int position) {
        mBooleanArray.put(position, true);

        if (mLastCheckedPosition > -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
            notifyItemChanged(mLastCheckedPosition);
        }
        notifyDataSetChanged();

        mLastCheckedPosition = position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View viewLine;
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            viewLine = itemView.findViewById(R.id.view_line);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
