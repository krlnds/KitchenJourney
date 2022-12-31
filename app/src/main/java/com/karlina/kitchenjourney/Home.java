package com.karlina.kitchenjourney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karlina.kitchenjourney.helper.ApiClient;
import com.karlina.kitchenjourney.helper.ApiInterface;
import com.karlina.kitchenjourney.helper.SharedPrefManager;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;

    ImageCarousel carousel;
    List<CarouselItem> list_img = new ArrayList<>();

    private ResepAdapter resepadapter;
    private ArrayList<Resep> resepArrayList;

    ImageButton btnProfil, btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

//        addData();
        resepArrayList = new ArrayList<>();
        getResep();

        carousel = findViewById(R.id.carousel);
        btnProfil = findViewById(R.id.btn_profil);
        btnSave = findViewById(R.id.btn_save);

        list_img.add(new CarouselItem(R.drawable.ayam_goreng));
        list_img.add(new CarouselItem(R.drawable.kangkung));
        list_img.add(new CarouselItem(R.drawable.ayammentega));
        list_img.add(new CarouselItem(R.drawable.nasigorengsederhana));
        list_img.add(new CarouselItem(R.drawable.rendang));
        list_img.add(new CarouselItem(R.drawable.sayurlodeh));
        list_img.add(new CarouselItem(R.drawable.sayurasam));
        list_img.add(new CarouselItem(R.drawable.rawon));
        list_img.add(new CarouselItem(R.drawable.sotoayamkuning));

        carousel.setData(list_img);
        carousel.setAutoPlay(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewresep);

        resepadapter = new ResepAdapter(resepArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Home.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(resepadapter);


        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, DetailProfil.class);
                startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, TersimpanActivity.class);
                startActivity(i);
            }
        });

    }

    void addData(){
//        resepArrayList.add(new Resep("Oseng - oseng kangkung","Bahan : \n1. Kangkung 2 ikat \n2. Bawang putih 3 siung \n3. Bawang merah 5 siung \n4. Garam 1 sdt \n5. Gula pasir 1/2 sdt \n6. Saus tiram 1 sdm \n7. Cabe rawit merah 2 buah \n8. Cabe merah keriting 3 buah \n9. Minyak goreng 1 sdm \nCara Membuat : \n1. Iris tipis bawang putih dan bawang merah. Potong serong cabai rawit merah dan cabai keriting \n2. Panaskan minyak, tumis bawang merah dan bawang putih hingga harum, kemudian tambahkan cabai dan tumis sebentar hingga layu \n3. Masukkan garam, gula, dan saus tiram. Aduk hingga semua bumbu tercampur rata \n4. Masukkan kangkung lalu masak dengan api besar hingga matang, angkat dan sisihkan \n5. Tumis kangkung siap untuk dinikmati",R.drawable.kangkung));
//        resepArrayList.add(new Resep("Ayam goreng kecap","Bahan : \n1. Ayam, potong-potong - 1/2 ekor \n2. Air jeruk nipis - 2 sdm \n3. Garam - 1/2 sdt \n4. Jahe, memarkan - 3 cm \n5. Bawang bombay, iris kecil memanjang - 1/2 buah \n6. Cabe merah, iris serong - 3 buah \n7. Bawang putih, tumbuk halus - 3 siung \n8. Kecap manis - 6 sdm \n9. Daun bawang, iris kasar - 1 tangkai \n10. Gula pasir - 1/2 sdt \n11. Garam - 1 sdt \n 12. Merica bubuk - 1/2 sdt \n13. Air - 150 ml \n 14. Minyak, untuk menggoreng ayam - secukupnya \nCara Membuat : \n1. Lumuri ayam dengan air jeruk nipis dan garam. Diamkan selama 20 menit, lalu cuci bersih \n2. Panaskan minyak goreng. Goreng ayam hingga berwarna kecokelatan. Angkat dan tiriskan \n3. Ambil 2 sdm minyak bekas menggoreng ayam dan panaskan \n4. Tumis bawang putih dan jahe hingga harum. Tambahkan bawang bombay lalu tumis hingga layu \n5. Masukkan ayam goreng. Tambahkan kecap manis, cabai merah, merica, gula dan garam. Tuangkan air lalu aduk hingga rata \n6. Masak ayam hingga kuah mengental. Koreksi rasanya \n7. Sesaat sebelum diangkat, masukkan daun bawang. Angkat \n8. Siap disajikan",R.drawable.ayam_goreng));
//        resepArrayList.add(new Resep("Nasi goreng sederhana","Bahan : \n1. 4 sdm minyak goreng \n2. 2 batang daun bawang \n3. 150 g wortel, iris dadu kecil \n4. 600 g nasi dingin \n5. 1 sdt merica putih bubuk \n6. 2 sdm kecap asin \n7. 4 butir telur ayam, kocok lepas \n8. 1 sdm minyak wijen \nCara Membuat : \n1. Panaskan minyak goreng dalam wajan, tumis daun bawang hingga harum. Tambahkan wortel, aduk \n2. Masukkan nasi, masak sambil diaduk hingga nasi mulai berasap. Tambahkan merica dan kecap asin. Masak sambil diaduk rata \n3. Buat lubang di tengah nasi, tuang telur kocok. Aduk-aduk telur menjadi orak-arik, aduk rata dengan nasi. Siramkan minyak wijen, aduk. Angkat \n4. Taruh nasi goreng di atas piring saji. Sajikan dengan acar, sambal, dan kerupuk ",R.drawable.nasigorengsederhana));
//        resepArrayList.add(new Resep("Sayur lodeh kuah santan","Bahan : \n1. Bawang putih - 3 siung \n2. Bawang merah - 5 butir \n3. Kemiri - 2 butir \n4. Terasi, bakar - 1/2 sdt \n5. Labu siam, potong dadu - 1 buah \n6. Kacang panjang, potong 3 cm - 5 batang \n7. Jagung, potong potong - 1 buah \n8. Terong ungu, potong potong - 1 buah \n9. Daun melinjo - 1 genggam \n 10. Melinjo merah dan hijau - 1 genggam \n11. Cabai hijau, potong potong - 3 buah \n12. Lengkuas, memarkan - 1 ruas \n13. Daun salam - 3 lembar \n14. Santan encer - 1 liter \n15. Garam - 2 sdt \n16. Kaldu bubuk - 1/2 sdt \n17. Gula - 1 sdt \nCara Membuat : \n1. Potong-potong sayuran, sisihkan \n2. Haluskan bumbu halus: Bawang merah, bawang putih, kemiri, dan terasi bakar \n3. Panaskan santan, masukkan bumbu halus, salam, lengkuas \n4. Masak sampai mendidih \n5. Masukkan sayuran yang keras terlebih dahulu dengan jarak sekitar 1 menit: Labu siam, melinjo, lalu jagung. Masak sampai setengah matang. Masukkan kacang panjang, masak sampai setengah matang. Lalu masukkan terong, cabai hijau, dan daun melinjo \n6. Bumbui dengan garam, gula, dan kaldu bubuk. Koreksi rasanya \n7. Masak sampai matang dan semua sayur menjadi empuk \n8. Angkat dan sajikan hangat - hangat",R.drawable.sayurlodeh));
//        resepArrayList.add(new Resep("Ayam mentega","Bahan : \n1. 800 g (1 ekor) ayam, potong jadi 8 bagian \n2. 2 sdm mentega \n3. 75 g bawang bombang, iris memanjang \n4. 2 sdm saus Inggris \n5. 1 sdm Bango Kecap Manis \n6. 1 sdm air perasan jeruk nipis \n7. 1 sdt garam \n8. 1 sdm minyak, untuk menumis \n9. 500 ml minyak, untuk menggoreng \n10. 2 siung bawang putih \n11. ½ sdt merica butir \n12. 2 sdt garam \nCara Membuat : \n1. Marinasi ayam: lumuri ayam dengan bumbu halus, remas-remas agar bumbu meresap sempurna \n2. Panaskan minyak, goreng ayam hingga matang dan kecokelatan. Angkat dan tiriskan \n3. Saus mentega: panaskan minyak dan mentega, tumis bawang bombay hingga harum \n4. Masukkan ayam goreng, saus Inggris, Bango Kecap Manis, dan garam. Aduk rata \n5. Masukkan ayam goreng, saus Inggris, Bango Kecap Manis, dan garam. Aduk rata \n6. Masak hingga bumbu meresap, tambahkan air perasan jeruk nipis. Aduk rata \n 7. Angkat dan sajikan",R.drawable.ayammentega));
//        resepArrayList.add(new Resep("Sayur Asam","Bahan : \n1. 1,5 l air \n2. 3 cm lengkuas, memarkan \n3. 2 lembar daun salam \n4. 1 buah (250 g) jagung manis, potong-potong \n5. 50 g kacang tanah kupas \n6. 200 g labu siam, kupas, potong-potong \n7. 50 g buah melinjo, cuci bersih \n8. 2 buah cabai merah besar, iris serong \n9. 2 buah cabai hijau besar, iris serong \n10. 50 g kacang panjang, potong-potong ukuran 4 cm \n11. 50 g daun melinjo, cuci bersih \n12. 75 ml air asam jawa pekat \n13. 6 siung bawang putih \n14. 4 butir bawang merah \n15.1 sdt garam \n16. ½ sdt terasi \n17. 2 sdt gula merah \nCara Membuat : \n1. Rebus air dalam panci hingga mendidih. Tambahkan bumbu halus, lengkuas dan daun salam, masak hingga bau langunya hilang \n2. Masukkan jagung manis, kacang tanah, labu siam, dan buah melinjo. Masak hingga matang \n3. Tambahkan cabai merah besar,cabai hijau besar, kacang panjang, daun melinjo, dan air asam jawa. Masak hingga seluruh bahan matang dan bumbu meresap dengan api sedang. Angkat, sajikan hangat",R.drawable.sayurasam));
//        resepArrayList.add(new Resep("Rendang","Bahan : \n1. 1 Kg daging sapi ppotong-potong \n2. 5 buah cabai merah teropong \n3. 5 buah cabai merah keriting \n4. 6-8 bawang merah \n5. 5 siung bawang putih \n6. 5cm jahe \n7. 1/2 ketumbar \n8. 1 sdt jinten \n9. 3 buah kemiri \n10. 3 buah kemiri \n11. 1 sdt garam \n12. 1 sdt kaldu bubuk \n13. Lada secukupnya \n14. Minyak secukupnya \n15. 1 buah kelapa parut \n16. 4 lembar daun jeruk \n17. 5 butir kapulaga \n18. 5 butir cengkih \n19. 5 cm lengkuas \n20. 5 lembar daun salam \n21. 2 batang serai \n22. 5 cm kayu manis \n23. Pala secukupnya, parut \n24. 600 ml sante encer \n25. 200 ml santan kental \nCara Membuat : \n1. Haluskan cabai merah teropong , cbai merah keriting, bawang merah, bawang putih, jahe, ketumbar, jinten, kemiri, garam, kaldu bubuk, lada dan minyak menggunakakn blander kemudian sisihkan \n2. Sangrai kelapa parut lalu sisihkan \n3. Masak bumbu yang sudah disisihkan tadi kemudian masukan potongan daging sapi tambahkan daun jeruk, kapulaga cengkih, lengkuas, daun salam, kayu manis, parutan pala, dan kelapa sangrai lalu di aduk aduk setelah itu tambahkan santan cair, masak sebentar \n4. Masukan santan kental, masak hingga kering, kemudian angkat dan sajikan",R.drawable.rendang));
//        resepArrayList.add(new Resep("Rawon","",R.drawable.rawon));
//        resepArrayList.add(new Resep("Soto ayam kuning","",R.drawable.sotoayamkuning));

    }

    private void getResep(){

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> getResep = apiInterface.getNews();

        getResep.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        resepArrayList.clear();
                        String respon = response.body().string();
                        Log.e("Respon", "Response " + respon);
                        JSONObject jsonObj = new JSONObject(respon);

//                        String error_sts = jsonObj.getString("error");

                        JSONArray news = jsonObj.getJSONArray("results");

                        for(int i = 0; i < news.length(); i++){
                            JSONObject c = news.getJSONObject(i);

                            Integer id = c.getInt("id");
                            String title = c.getString("title");
                            String bahan = c.getString("bahan");
                            String cara = c.getString("cara");
                            String image = c.getString("image");

                            Resep n = new Resep();
                            n.setId(id);
                            n.setTitle(title);
                            n.setBahan(bahan);
                            n.setCara(cara);
                            n.setImage(image);
                            resepArrayList.add(n);

                            Log.e("Hasil: ", "News " + c);
                        }

                        resepadapter.notifyDataSetChanged();

                    } catch (JSONException e){
                        Toast.makeText(Home.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Error ", "Souldn't get json from server.");
                    Home.this.runOnUiThread(new Runnable() {
                        @Override
                        public void
                        run() {
                            Toast.makeText(Home.this, "Couldn't get json from server. Check LoCat for possible errors!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Home.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}