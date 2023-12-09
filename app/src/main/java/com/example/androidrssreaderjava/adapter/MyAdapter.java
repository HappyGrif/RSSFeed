package com.example.androidrssreaderjava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidrssreaderjava.databinding.LayoutItemBinding;
import com.example.androidrssreaderjava.interfaces.IRecyclerClickListener;
import com.example.androidrssreaderjava.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Item> items;
    private IRecyclerClickListener listener;

    public MyAdapter(List<Item> items, IRecyclerClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemBinding binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.binding.txtTitle.setText(items.get(position).title);
        holder.binding.txtPublished.setText(items.get(position).pubDate);

        if(items.get(position).enclosure != null) {
            Picasso.get()
                    .load(items.get(position).enclosure.url)
                    .resize(80, 80)
                    .centerCrop()
                    .into(holder.binding.image);
        }

        holder.binding.card.setOnClickListener(view -> listener.onItemClick(items.get(position).link));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LayoutItemBinding binding;

        public MyViewHolder(@NonNull LayoutItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
