package id.sapi.ktp.aplikasiktpsapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HalamanLogin extends AppCompatActivity {

    Button btnlogin, btnDaftar;//btn masuk
    EditText Euser, Epass;
    ProgressDialog loading;
    Context mContext;
    private SharedPreferences pref;
    private static final int REQ_CODE = 9001;
    Toolbar toolbar;
    TextView textToolbar;
    SharedPrefManager sharedPrefManager;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        textToolbar = (TextView)findViewById(R.id.toolbar_title);

        loading = new ProgressDialog(this, R.style.AppTheme);
        DoubleBounce doubleBounce = new DoubleBounce();
        loading.setIndeterminate(true);
        loading.setIndeterminateDrawable(doubleBounce);
        loading.setMessage("Please wait...");
        loading.setCancelable(false);

        Euser = (EditText) findViewById(R.id.username);
        Epass = (EditText)findViewById(R.id.password);
        btnlogin = (Button)findViewById(R.id.btnMasuk);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(validate()){
                   loading = ProgressDialog.show(mContext,null,"tunggu", true, false);
                   validate();
                   login();
                }
            }
        });
        btnDaftar = (Button)findViewById(R.id.linkdaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, HalamanDaftar.class));
            }
        });

        if(sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(HalamanLogin.this, MainActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
    private boolean validate(){
            boolean valid = true;
            String user = Euser.getText().toString();
            String password = Epass.getText().toString();

            if(user .isEmpty()){
                Euser.setError("Username masih kosong!");
                valid = false;
            }else {
                Euser.setError(null);
            }
            if(password.isEmpty()){
                Epass.setError("Password masih kosong!");
                valid = false;
            }
            return valid;
    }

    private void login(){
        koneksi();
        mApiService.loginRequest(Euser.getText().toString(), Epass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            loading.dismiss();
                            try{
                                JSONObject jsonRESULT = new JSONObject(response.body().string());
                                if(jsonRESULT.getString("error").equals("false")){
                                    Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();
                                    String user = jsonRESULT.getJSONObject("user").getString("user");
                                    sharedPrefManager.saveSPString(SharedPrefManager.KEY_USER_USER, user);
                                    String id_user= jsonRESULT.getJSONObject("user").getString("id_user");
                                    sharedPrefManager.saveSPString(SharedPrefManager.KEY_USER_ID, id_user);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.KEY_LOGIN, true);
                                    startActivity(new Intent(mContext, MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                }else {
                                    String error_message= jsonRESULT.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            loading.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "OnFailure: ERROR > " +t.toString());
                        loading.dismiss();
                    }
                });
    }
    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(HalamanLogin.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }


}
