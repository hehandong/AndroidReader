package com.hehandong.androidreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.module.WanBaseModel;
import com.hehandong.androidreader.Retrofit.module.reponse.HomeListModel;
import com.hehandong.androidreader.listener.OnItemClickListener;

import java.util.List;


public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;
    private WanBaseModel<HomeListModel> mBaseModel;

    public HomeListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(WanBaseModel<HomeListModel> wanBaseModel) {
        mBaseModel = wanBaseModel;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home2, parent, false);
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

        if (checkData()) {
            List<HomeListModel.DatasBean> datas = mBaseModel.data.getDatas();
            if (datas != null) {
                HomeListModel.DatasBean bean = datas.get(position);
                holder.mTypeTv.setText(bean.getChapterName());
                holder.mTitleTv.setText(bean.getTitle());
                if (TextUtils.isEmpty(bean.getDesc())){
                    holder.mContentTv.setVisibility(View.GONE);
                }else {
                    holder.mContentTv.setVisibility(View.VISIBLE);
                    holder.mContentTv.setText(bean.getDesc());
                }
                holder.mDateTv.setText(bean.getNiceDate());
            }
        }

    }

    @Override
    public int getItemCount() {
        if (checkData()) {
            return mBaseModel.data.getDatas().size();
        }
        return 0;
    }

    public HomeListModel.DatasBean getItem(int position) {
        if (checkData()) {
            return mBaseModel.data.getDatas().get(position);
        }
        return null;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTypeTv, mTitleTv, mContentTv, mDateTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTypeTv = (TextView) itemView.findViewById(R.id.tv_type);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_title);
            mContentTv = (TextView) itemView.findViewById(R.id.tv_content);
            mDateTv = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public boolean checkData() {
        if (mBaseModel != null && mBaseModel.data != null && mBaseModel.data.getDatas() != null) {
            return true;
        }
        return false;
    }
}
