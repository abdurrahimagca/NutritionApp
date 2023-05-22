package com.example.nutritionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private final RvInterface rvInterface;
    Context context;
    ArrayList<MenuModel> menuModels;

    public MenuAdapter(Context context, ArrayList<MenuModel> menuModels, RvInterface rvInterface){
        this.context = context;
        this.menuModels = menuModels;
        this.rvInterface = rvInterface;
    }
    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =  layoutInflater.inflate(R.layout.main_page_rows, parent,false);
        return new MenuViewHolder(view, rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        holder.recTV.setText(menuModels.get(position).getRecommendation());
        holder.repastTV.setText(menuModels.get(position).getRepast());

    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }
    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        TextView repastTV;
        TextView recTV;

        public MenuViewHolder(@NonNull View itemView, RvInterface rvInterface ) {
            super(itemView);
            repastTV = itemView.findViewById(R.id.repast_tv);
            recTV = itemView.findViewById(R.id.recommendation_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rvInterface !=  null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION);
                        {
                            rvInterface.itemClickListener(pos);
                        }
                    }
                }
            });
        }
    }
}
