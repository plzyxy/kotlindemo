package com.teayork.common_base.base.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.teayork.common_base.base.OnDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected List<T> data = new ArrayList<>();
    protected OnItemClickListener<T> listener;
    protected OnItemLongClickListener<T> onItemLongClickListener;

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position) {
        holder.onBaseBindViewHolder(data.get(position), position);

        holder.itemView.setOnClickListener(new OnDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                if (listener!=null){
                    listener.onClick(data.get(position), position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<T> data) {
        this.data.addAll(data);
    }

    public void add(T object) {
        data.add(object);
    }

    public void insert(int index,T object){
        data.add(index,object);
    }

    public void clear() {
        data.clear();
    }

    public void remove(T object) {
        data.remove(object);
    }
    public void remove(int position) {
        data.remove(position);
    }
    public void removeAll(List<T> data) {
        this.data.retainAll(data);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }


    public List<T> getData() {
        return data;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnItemClickListener<T> getListener() {
        return listener;
    }

    public OnItemLongClickListener<T> getOnItemLongClickListener() {
        return onItemLongClickListener;
    }
}
