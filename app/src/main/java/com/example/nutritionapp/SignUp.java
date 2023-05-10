package com.example.nutritionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button signUpButton = findViewById(R.id.signup_button_signup);
        EditText nuMail = findViewById(R.id.mail_signup_text);
        EditText nuPass = findViewById(R.id.pass_signup_text);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid(nuMail.getText().toString())&&nuPass.length()>5){
                    firebaseAuth.createUserWithEmailAndPassword(nuMail.getText().toString(), nuPass.getText().toString())
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "Başarıyla üye oldunuz", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this, Login.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(SignUp.this, "Bir şeyler yolunda gitmedi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
                else{
                    //
                    Toast.makeText(SignUp.this, "mail adresi ve en az altı haneli bir şifre kullanınız", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    public boolean isValid(String mail){
        return Pattern.compile("^(.+)@(.+)$")
                .matcher(mail)
                .matches();
    }

}