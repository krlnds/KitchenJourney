package com.karlina.kitchenjourney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karlina.kitchenjourney.helper.SharedPrefManager;
import com.karlina.kitchenjourney.model.Tersimpan;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView tvNama;
    TextView tvDesc;
    TextView tvCara;
    ImageView tvGambar;
    ImageButton btnSave;

    List<Tersimpan> savedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama = findViewById(R.id.txt_nama_makanan);
        tvDesc = findViewById(R.id.txt_desc_makanan);
        tvCara = findViewById(R.id.txt_cara);
        tvGambar = findViewById(R.id.iv_menu);
        btnSave = findViewById(R.id.btn_save);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        String namaMakanan = getIntent().getStringExtra("nama_makanan");
        String descMakanan= getIntent().getStringExtra("desc_makanan");
        String cara= getIntent().getStringExtra("cara");

        tvNama.setText(namaMakanan);
        tvDesc.setText(descMakanan);
        tvCara.setText(cara);
        String img = getIntent().getStringExtra("gambar_makanan");

        Picasso.get().load(img).into(tvGambar);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadDataSaved();
                savedList.add(new Tersimpan(namaMakanan, descMakanan,cara, img));
                SaveMessage();
                Toast.makeText(DetailActivity.this, "Resep tersmipan", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void SaveMessage(){

        SharedPreferences sharedPreferences =  getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(savedList);
        editor.putString(SharedPrefManager.KEY_RESEP, json);
        editor.apply();
    }

    public void LoadDataSaved(){

        SharedPreferences sharedPreferences = DetailActivity.this.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString(SharedPrefManager.KEY_RESEP, null);

        Type type = new TypeToken<ArrayList<Tersimpan>>() {}.getType();

        savedList= gson.fromJson(json, type);

        if (savedList == null) {
            savedList = new ArrayList<>();
        }

    }



}
