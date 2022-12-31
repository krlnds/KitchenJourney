package com.karlina.kitchenjourney;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.karlina.kitchenjourney.helper.ApiClient;
import com.karlina.kitchenjourney.helper.SharedPrefManager;
import com.karlina.kitchenjourney.model.Tersimpan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TersimpanAdapter extends RecyclerView.Adapter<TersimpanAdapter.ResepViewHolder> {

    private final Context view;
    private ArrayList<Tersimpan> datalist;


    public TersimpanAdapter(ArrayList<Tersimpan> datalist, Context view){
        this.datalist = datalist;
        this.view = view;
    }


    @NonNull
    @Override
    public ResepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new TersimpanAdapter.ResepViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ResepViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Tersimpan s = datalist.get(position);
        holder.txt_nama_makanan.setText(datalist.get(position).getTitle());
        Picasso.get().load(datalist.get(position).getImage()).into(holder.iv_menu);
        holder.cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), DetailActivity.class);
                myIntent.putExtra("nama_makanan", datalist.get(position).getTitle());
                myIntent.putExtra("desc_makanan", datalist.get(position).getBahan());
                myIntent.putExtra("cara", datalist.get(position).getCara());
                myIntent.putExtra("gambar_makanan", datalist.get(position).getImage());
                view.getContext().startActivity(myIntent);

            }
        });

        holder.cv_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), holder.cv_item);
                popup.inflate(R.menu.menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                datalist.remove(position);
                                notifyDataSetChanged();
                                Context context = view.getContext();
                                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();

                                String json = gson.toJson(datalist);
                                editor.putString(SharedPrefManager.KEY_RESEP, json);
                                editor.apply();

                                Toast.makeText(view.getContext(), "Terhapus", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

                return false;
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
