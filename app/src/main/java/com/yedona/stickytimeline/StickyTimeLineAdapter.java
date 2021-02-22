package com.yedona.stickytimeline;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author: yedona
 * @date: 2021/2/22 14:18
 * @description
 */
public class StickyTimeLineAdapter extends RecyclerView.Adapter<StickyTimeLineAdapter.ViewHolder> {


    private final LayoutInflater inflater;
    List<DateBean> list;
    Context context;

    int[] colors = {Color.GREEN, Color.YELLOW, Color.GRAY};

    OnItemClickListener onItemClickListener;

    boolean isEditModel;


    public StickyTimeLineAdapter(List<DateBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_sticky_time_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final DateBean bean = list.get(position);

        holder.ll.setBackgroundColor(colors[bean.spanIndex % 3]);

        holder.tv.setText(bean.date);
        holder.tv2.setText("position:" + position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(bean, position);
                }
            }
        });

        holder.cb.setVisibility(isEditModel ? View.VISIBLE : View.GONE);


        holder.cb.setChecked(bean.isSelect);


        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bean.isSelect = b;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        TextView tv2;
        RelativeLayout ll;
        CheckBox cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll);
            tv = itemView.findViewById(R.id.tv);
            tv2 = itemView.findViewById(R.id.tv2);
            cb = itemView.findViewById(R.id.cb);

        }
    }

    public interface OnItemClickListener {
        void onClick(DateBean bean, int position);
    }

}
