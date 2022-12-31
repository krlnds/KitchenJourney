package com.karlina.kitchenjourney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karlina.kitchenjourney.helper.SharedPrefManager;
import com.karlina.kitchenjourney.model.Tersimpan;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TersimpanActivity extends AppCompatActivity {

    private ArrayList<Tersimpan> savedArrayList = new ArrayList<>();
    TersimpanAdapter adapter;
    RecyclerView rvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tersimpan);
        rvSave = findViewById(R.id.recyclerViewresepsave);

        LoadDataMessage();

        adapter = new TersimpanAdapter(savedArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSave.setLayoutManager(linearLayoutManager);
        rvSave.setMotionEventSplittingEnabled(false);
        rvSave.setAdapter(adapter);

    }

    public void LoadDataMessage(){

        SharedPreferences sharedPreferences = TersimpanActivity.this.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString(SharedPrefManager.KEY_RESEP, null);

        Type type = new TypeToken<ArrayList<Tersimpan>>() {}.getType();

        savedArrayList= gson.fromJson(json, type);

        if (savedArrayList == null) {
            savedArrayList = new ArrayList<>();
        }

    }

}