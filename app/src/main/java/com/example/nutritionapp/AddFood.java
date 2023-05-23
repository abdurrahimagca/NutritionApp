package com.example.nutritionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Objects;

public class AddFood extends AppCompatActivity implements RvInterface {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AddFoodAdapter addFoodAdapter;
    ArrayList<FoodModel> foodModels;
    ImageButton addFoodDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
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
        addFoodDatabase = (ImageButton) findViewById(R.id.add_food_to_database);
        addFoodDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFood.this,AddFoodDatabase.class);
                intent.putExtra("repastNameToDatabase", pathString);
                startActivity(intent);

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

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}