package com.karlina.kitchenjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.karlina.kitchenjourney.helper.SharedPrefManager;
import com.karlina.kitchenjourney.model.User;

public class DetailProfil extends AppCompatActivity {

    EditText etEmail, etNama;
    Button btnLogout;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        etEmail = findViewById(R.id.edt_masukanemail);
        etNama = findViewById(R.id.edt_masukannama);
        btnLogout= findViewById(R.id.btn_logout);
        btnBack= findViewById(R.id.btn_back);

        User user = SharedPrefManager.getInstance(this).getUser();

        etNama.setText(user.getName());
        etEmail.setText(user.getEmail());

        setupFunction();


    }

    public void setupFunction(){

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(DetailProfil.this).logout();
//                finish();
//                DetailProfil.super.onDestroy();
//                onDestroy();
                DetailProfil.super.finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}