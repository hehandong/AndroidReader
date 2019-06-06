package com.hehandong.androidreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hehandong.androidreader.R;
import com.hehandong.androidreader.Retrofit.module.WanBaseModel;
import com.hehandong.androidreader.listener.OnItemClickListener;


public abstract class WanBaseAdapter<T> extends RecyclerView.Adapter<WanBaseAdapter.MyViewHolder> {
    protected LayoutInflater mInflater;

    protected OnItemClickListener mClickListener;
    protected WanBaseModel<T> mBaseModel;

    public WanBaseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(WanBaseModel<T> wanBaseModel) {
        mBaseModel = wanBaseModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wx_artcle, parent, false);
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
    public void onBindViewHolder(WanBaseAdapter.MyViewHolder holder, int position) {

        if (checkData()) {
//            List<WxArticleListModel.DatasBean> datas = mBaseModel.data.getDatas();
//            if (datas != null) {
//                WxArticleListModel.DatasBean bean = datas.get(position);
//                holder.tvTitle.setText(bean.getTitle());
//                holder.tvContent.setText(bean.getNiceDate());
//            }
        }

    }

    @Override
    public int getItemCount() {
//        if (checkData()) {
//            return mBaseModel.data.getDatas().size();
//        }
        return 0;
    }

//    public WxArticleListModel.DatasBean getItem(int position) {
//        if (checkData()) {
//            return mBaseModel.data.getDatas().get(position);
//        }
//        return null;
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public abstract boolean checkData();

    public boolean checkBaseData() {
        if (mBaseModel != null && mBaseModel.data != null) {
            return true;
        }
        return false;
    }
}
