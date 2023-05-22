package com.example.nutritionapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.FoodViewHolder>{
    private final RvInterface rvInterface;
    Context context;
    ArrayList<FoodModel> foodModels;

    public AddFoodAdapter(Context context, ArrayList<FoodModel> foodModels, RvInterface rvInterface) {
        this.context = context;
        this.foodModels = foodModels;
        this.rvInterface = rvInterface;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_food_rows,parent,false);
        return new FoodViewHolder(view, rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.nameTv.setText(foodModels.get(position).getFood());
        holder.kcalTv.setText(String.valueOf(foodModels.get(position).getKcal()));

    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView kcalTv;
        public FoodViewHolder(@NonNull View itemView, RvInterface rvInterface) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.food_name_tv);
            kcalTv = itemView.findViewById(R.id.kcal_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rvInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            rvInterface.itemClickListener(pos);
                        }
                    }
                }
            });
        }
    }



}
