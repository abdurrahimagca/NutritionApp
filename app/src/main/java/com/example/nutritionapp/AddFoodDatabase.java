package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AddFoodDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sayfa boyutlarını ayarlar
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(height*0.5));

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        getWindow().setAttributes(layoutParams);


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_add_food_database);

        //database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        Button addFoodButton = findViewById(R.id.add_food_database_button);
        EditText foodName = findViewById(R.id.food_name);
        EditText foodKcal = findViewById(R.id.foods_kcal_tv);

        HashMap<String, Object> newFoodData = new HashMap<>();


        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFoodData.put("food", foodName.getText().toString());
                newFoodData.put("kcal", Integer.parseInt(foodKcal.getText().toString()));
                String child = getIntent().getStringExtra("repastNameToDatabase");

                databaseReference.child(child).push().setValue(newFoodData);
            }
        });







    }
}