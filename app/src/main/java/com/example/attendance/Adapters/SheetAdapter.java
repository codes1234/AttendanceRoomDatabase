package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.DB.DataEntity;
import com.example.attendance.DB.LoginEntity;
import com.example.attendance.R;
import com.example.attendance.databinding.SheetItemBinding;

import java.util.List;

public class SheetAdapter extends RecyclerView.Adapter<SheetAdapter.ExViewHolder> {
    Context context;
    List<LoginEntity> userlist;

    public SheetAdapter(Context context, List<LoginEntity> userlist) {
        this.context = context;
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExViewHolder(LayoutInflater.from(context).inflate(R.layout.sheet_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {
        LoginEntity model= userlist.get(position);

        holder.binding.empIdIteam.setText(model.getEmpId());
        holder.binding.nameItem.setText(model.getFirstName());
        holder.binding.dateItem.setText(model.getDate());
        holder.binding.timeItem.setText(model.getTime());
        holder.binding.locationItem.setText(model.getLocation());


    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        SheetItemBinding binding;
        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SheetItemBinding.bind(itemView.getRootView());
        }
    }
}
