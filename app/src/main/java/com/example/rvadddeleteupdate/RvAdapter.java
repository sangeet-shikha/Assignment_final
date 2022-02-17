package com.example.rvadddeleteupdate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {
    Context context;
    ArrayList<Model> models;
    Onclick onclick;

    public interface Onclick {
        void onEvent(Model model,int pos);
    }

    public RvAdapter(Context context, ArrayList<Model> models, Onclick onclick) {
        this.context = context;
        this.models = models;
        this.onclick = onclick;
    }

    View view;

    @NonNull
    @Override
    public RvAdapter.RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new RvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.RvViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Model model = models.get(position);
        if (model.getName() != null) {
            holder.itemName.setText(model.getName());

        }if (model.getNumber() !=null){
            holder.itemNumber.setText(model.getNumber());
        };


        holder.removeImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                models.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onEvent(model,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class RvViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,itemNumber;
        ImageView removeImg;
        LinearLayout llItem;

        public RvViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_name);
            itemNumber = itemView.findViewById(R.id.tv_number);
            removeImg = itemView.findViewById(R.id.img_remove);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}



