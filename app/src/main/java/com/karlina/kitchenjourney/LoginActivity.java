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
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;

public class LoginActivity extends AppCompatActivity {


    EditText etEmail, etPass;
    Button btnLogin;
    TextView tvDaftar;
    String email, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.edt_masukanemail);
        etPass = findViewById(R.id.edt_masukanpassword);
        btnLogin = findViewById(R.id.btn_login);
        tvDaftar = findViewById(R.id.tx_goto_register);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Semua kolom wajib di isi", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email,pass);
                }

            }
        });

        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Register.class));
            }
        });


    }

    private void loginUser(String email, String password) {
//        loadingPB.setVisibility(View.VISIBLE);

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> loginUser = apiInterface.loginUser(email, password);

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
                            Toast.makeText(LoginActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject user = jsonObj.getJSONObject("user");
                            User user_data = new User(
                                    user.getInt("id"),
                                    user.getString("name"),
                                    user.getString("email"),
                                    user.getString("key")
                            );

                            SharedPrefManager.getInstance(LoginActivity.this).userLogin(user_data);
//                            Toast.makeText(Login.this, users.getName(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, Home.class));
                            LoginActivity.super.finish();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}