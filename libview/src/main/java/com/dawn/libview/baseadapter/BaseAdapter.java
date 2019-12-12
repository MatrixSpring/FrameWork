package com.dawn.libview.baseadapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.VH>{
    public List<T> mDatas;
    public BaseAdapter(List<T> datas){
        this.mDatas = datas;
    }

    public abstract int getLayoutId(int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return VH.get(parent,getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setDatas(List<T> datas){
        this.mDatas = datas ;
        notifyDataSetChanged();
    }


    public void  appendDatas(List<T> appendDatas){
        int startIndex = 0 ;
        int countNums = 0 ;
        if (mDatas!=null){
            startIndex = mDatas.size() ;
        }
        if (appendDatas!=null){
            countNums = appendDatas.size() ;
        }
        mDatas.addAll(appendDatas) ;
        notifyItemRangeChanged(startIndex,countNums);
    }

    public abstract void convert(VH holder, T data, int position);

    public static class VH extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private View mConvertView;

        private VH(View v){
            super(v);
            mConvertView = v;
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutId){
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new VH(convertView);
        }

        public <T extends View> T getView(int id){
            View v = mViews.get(id);
            if(v == null){
                v = mConvertView.findViewById(id);
                mViews.put(id, v);
            }
            return (T)v;
        }

        public void setText(int id, String value){
            TextView view = getView(id);
            view.setText(value);
        }

        public void lineVisible(int id, boolean visible){
            ImageView iv_line = getView(id);
            if(null != iv_line){
                if(visible){
                    iv_line.setVisibility(View.VISIBLE);
                }else{
                    iv_line.setVisibility(View.GONE);
                }
            }
        }
    }
}
