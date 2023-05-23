package com.example.nutritionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
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

public class MainActivity extends AppCompatActivity implements RvInterface {
    ArrayList<MenuModel> menuModels = new ArrayList<>();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        TextView kcalTV = findViewById(R.id.kcal_tv);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        databaseReference = FirebaseDatabase.getInstance().getReference("Kcal");
        System.out.println(date);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DailyKcalModel dailyKcalModel = snapshot.child(date).getValue(DailyKcalModel.class);
                if (dailyKcalModel != null) {

                    String kcalString = String.valueOf(dailyKcalModel.getKcal());


                    kcalString = kcalString + " / 1250";
                    kcalTV.setText(kcalString);

                    int progress = (int) (dailyKcalModel.getKcal() / 1250 * 100);
                    progressBar.setProgress(progress);
                } else {

                    HashMap<String, Object> newData = new HashMap<>();
                    newData.put("date", date);
                    newData.put("kcal", 0);
                    databaseReference.child(date).setValue(newData);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_View);

        setMenuModels();
        MenuAdapter menuAdapter = new MenuAdapter(this, menuModels, this);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setMenuModels() {
        String[] repast = getResources().getStringArray(R.array.repast);
        String[] recommendation = getResources().getStringArray(R.array.recommendation);
        for (int i = 0; i < repast.length; i++) {
            menuModels.add(new MenuModel(repast[i], recommendation[i]));
        }
    }

    public void checkAndCreateKcalData() {

    }

    @Override
    public void itemClickListener(int position) {
        String repastName = menuModels.get(position).getRepast();
        Intent intent = new Intent(MainActivity.this, AddFood.class);
        intent.putExtra("repastVal", repastName);
        startActivity(intent);


    }
}