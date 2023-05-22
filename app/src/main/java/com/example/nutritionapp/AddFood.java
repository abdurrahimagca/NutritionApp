package com.example.nutritionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddFood extends AppCompatActivity implements RvInterface {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AddFoodAdapter addFoodAdapter;
    ArrayList<FoodModel> foodModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        TextView textView = findViewById(R.id.repast_menu_tv);
        String pathString = getIntent().getStringExtra("repastVal");
        textView.setText(pathString);
        //todo: tüm öğün seçenek ve kalorileri öğüne göre firebaseden çekilmeli.
        recyclerView = findViewById(R.id.add_food_recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("Foods").child(pathString);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        foodModels = new ArrayList<>();
        addFoodAdapter = new AddFoodAdapter(this,foodModels,this);
        recyclerView.setAdapter(addFoodAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("line this");


                foodModels.clear();


                for(DataSnapshot dataSnapshot : snapshot.getChildren()){


                    FoodModel foodModel =dataSnapshot.getValue(FoodModel.class);
                    foodModels.add(foodModel);
                    System.out.println("food : "  + foodModels);
                }
                addFoodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void itemClickListener(int position) {

        //şuanki yiyeceğin kalorisi
        TextView currentKcal = findViewById(R.id.kcal_tv);
        String kcalString =  currentKcal.getText().toString();
        int kcal = Integer.parseInt(kcalString);
        DailyKcalModel dailyKcalModel;

        //hali hazırda olan kalori
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        DatabaseReference databaseReference = database.getReference("Kcal");


        Map<String, Object> kcalModelMap = new HashMap<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DailyKcalModel dailyKcalModel = snapshot.child(date).getValue(DailyKcalModel.class);
                int updatedKcal = dailyKcalModel.getKcal() + kcal;
                System.out.println(updatedKcal);
                databaseReference.child(date).child("kcal").setValue(updatedKcal);



               /* dailyKcalModel.setKcal(updatedKcal);
                System.out.println(dailyKcalModel.getKcal());
                kcalModelMap.put(date,dailyKcalModel);
                databaseReference.child(date).updateChildrenAsync(kcalModelMap);*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //store data in cloud



    }
}