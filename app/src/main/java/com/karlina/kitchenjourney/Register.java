package com.karlina.kitchenjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karlina.kitchenjourney.helper.ApiClient;
import com.karlina.kitchenjourney.helper.ApiInterface;
import com.karlina.kitchenjourney.helper.SharedPrefManager;
import com.karlina.kitchenjourney.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText etEmail,etPass, etNama,etCoPass;
    Button btnRegister;
    String email, name, pass, copass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.edt_masukanemail);
        etPass = findViewById(R.id.edt_masukanpassword);
        etNama = findViewById(R.id.edt_masukannama);
        etCoPass = findViewById(R.id.edt_copassword);
        btnRegister= findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email= etEmail.getText().toString();
                pass = etPass.getText().toString();
                name = etNama.getText().toString();
                copass = etCoPass.getText().toString();


                if (email.isEmpty() || pass.isEmpty() || name.isEmpty() || copass.isEmpty()){
                    Toast.makeText(Register.this, "Semua kolom wajib di isi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!copass.equals(pass) ){
                    Toast.makeText(Register.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser(email, name, pass);

            }
        });


    }

    private void registerUser(String email, String nama, String password) {
//        loadingPB.setVisibility(View.VISIBLE);

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> loginUser = apiInterface.registerUser(name, pass, email);

        loginUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                loadingPB.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {

                        String respon = response.body().string();
                        Log.e("Response: ", "Response " + respon);
                        JSONObject jsonObj = new JSONObject(respon);

                        String error_sts = jsonObj.getString("error");

                        if (error_sts == "true") {
                            String error_msg = jsonObj.getString("error_msg");
                            Toast.makeText(Register.this, error_msg, Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject user = jsonObj.getJSONObject("user");
                            User user_data = new User(
                                    user.getInt("id"),
                                    user.getString("name"),
                                    user.getString("email"),
                                    user.getString("key")
                            );

                            SharedPrefManager.getInstance(Register.this).userLogin(user_data);
//                            Toast.makeText(Login.this, users.getName(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Register.this, Home.class));
                            Register.super.finish();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Error: ", "Souldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void
                        run() {
                            Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LoCat for possible errors!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                loadingPB.setVisibility(View.GONE);
                Toast.makeText(Register.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}