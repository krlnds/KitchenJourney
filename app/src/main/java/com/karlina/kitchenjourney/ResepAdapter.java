package com.karlina.kitchenjourney;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.karlina.kitchenjourney.helper.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.ResepViewHolder> {

    private ArrayList<Resep> datalist;


    public ResepAdapter(ArrayList<Resep> datalist){
        this.datalist = datalist;
    }


    @NonNull
    @Override
    public ResepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ResepAdapter.ResepViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ResepViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_nama_makanan.setText(datalist.get(position).getTitle());
        Picasso.get().load(ApiClient.BASE_URL_IMAGE + datalist.get(position).getImage()).into(holder.iv_menu);
        holder.cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), DetailActivity.class);
                myIntent.putExtra("nama_makanan", datalist.get(position).getTitle());
                myIntent.putExtra("desc_makanan", datalist.get(position).getBahan());
                myIntent.putExtra("cara", datalist.get(position).getCara());
                myIntent.putExtra("gambar_makanan", ApiClient.BASE_URL_IMAGE+ datalist.get(position).getImage());
                view.getContext().startActivity(myIntent);

            }
        });


    }

    @Override

    public int getItemCount() {
        return (datalist != null) ? datalist.size() : 0;
    }


    public class ResepViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_nama_makanan;

        private ImageView iv_menu;

        CardView cv_item;

        public ResepViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nama_makanan = itemView.findViewById(R.id.txt_nama_makanan);
            iv_menu = itemView.findViewById(R.id.iv_menu);
            cv_item = itemView.findViewById(R.id.cv_item);
        }
    }


    }
