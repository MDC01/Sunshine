package cn.edu.zstu.sunshine.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器的基类
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    private List<T> data = new ArrayList<>();
    private int br;
    private int layout;
    private OnItemHandler onItemHandler;

    public BaseAdapter(int layout, int br, List<T> data) {
        this.br = br;
        this.data = data;
        this.layout = layout;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layout, parent, false);
        return new BaseViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(BaseAdapter.BaseViewHolder holder, int position) {
        holder.getViewDataBinding().setVariable(br, data.get(position));
        holder.getViewDataBinding().executePendingBindings();
        if (onItemHandler != null) {
            onItemHandler.onItemHandler(holder.getViewDataBinding(), position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public BaseAdapter setOnItemHandler(OnItemHandler onItemHandler) {
        this.onItemHandler = onItemHandler;
        return this;
    }

    public interface OnItemHandler {
        void onItemHandler(ViewDataBinding viewDataBinding, int position);
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding viewDataBinding;

        BaseViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }
}


