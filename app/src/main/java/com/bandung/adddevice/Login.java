package com.bandung.adddevice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bandung.adddevice.Support.InitRetrofit;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private LinearLayout coordinatorLayout;
    EditText edtEmail, edtPassword;
    Button Login;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail= (EditText)findViewById( R.id.username );
        edtPassword=(EditText)findViewById( R.id.password );

        Login=(Button)findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    edtEmail.setFocusable(false);
                    edtPassword.setFocusable(false);
                    showSnackbar();
                } else {
                    loading = ProgressDialog.show(Login.this,"Loading.....",null,true,true);
                    RequestLogin();
                }
            }
        });
//        if (sharedPrefManager.getSudahLogin()){
//            startActivity(new Intent(Login.this, Dashboard.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
//        }
    }

    private void RequestLogin(){
        String user = String.valueOf(edtEmail.getText());
        String pas = String.valueOf(edtPassword.getText());
        if (user.equals("")) {
            showSnackbar();
        } else if (pas.equals("")) {
            showSnackbar();
        } else {
//            mApiService.userLogin(Username.getText().toString(), Password.getText().toString())
            retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().userLogin(user,pas);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        loading.dismiss();
//                        try {
//                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                            if (jsonRESULTS.getString("error").equals("false")){
//                                Log.d("response api", jsonRESULTS.toString());
//                                Toast.makeText(Login.this, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
////                                        String id = jsonRESULTS.getJSONObject("user").getString("ID");
//                                String nama = jsonRESULTS.getJSONObject("user").getString("nama");
//                                String email = jsonRESULTS.getJSONObject("user").getString("email");
//                                Log.d("alamat", nama+email.toString());
                                startActivity(new Intent(Login.this, MainActivity.class));
//                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
//                            } else {
//                                String error_message = jsonRESULTS.getString("error_msg");
//                                Toast.makeText(Login.this, error_message, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        loading.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.v("debug", "onFailure: ERROR > " + t.toString());
                    loading.dismiss();

                }
            });
        }

}

    public void showSnackbar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Lengkapi data terlebih dahulu", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ulangi", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Silahkan ulangi", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        edtEmail.setFocusableInTouchMode(true);
                        edtPassword.setFocusableInTouchMode(true);
                    }
                });
        snackbar.show();
}
}
