package id.sapi.ktp.aplikasiktpsapi.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.DoubleBounce;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HalamanDaftar extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog loading;
    Button btndaftar;
    EditText txtnama, txtuser, txtpass, txtpassulang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        loading = new ProgressDialog(this, R.style.AppTheme);
        DoubleBounce doubleBounce = new DoubleBounce();
        loading.setIndeterminate(true);
        loading.setIndeterminateDrawable(doubleBounce);
        loading.setMessage("Please wait...");
        loading.setCancelable(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

        btndaftar = (Button)findViewById(R.id.btnDaftar);
        txtnama = (EditText)findViewById(R.id.nama);
        txtuser = (EditText)findViewById(R.id.username);
        txtpass = (EditText)findViewById(R.id.password);
        txtpassulang = (EditText)findViewById(R.id.passwordulangi);
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private boolean validate(){
        boolean valid = true;
        String nm = txtnama.getText().toString();
        String us = txtuser.getText().toString();
        String ps = txtpass.getText().toString();
        String psu =txtpassulang.getText().toString();

        if(us.isEmpty()){
            txtuser.setError("Username masih kosong!");
            valid = false;
        }else {
            txtuser.setError(null);
        }
        if(nm.isEmpty()){
            txtnama.setError("Nama masih kosong!");
            valid = false;
        }else {
            txtuser.setError(null);
        }
        if(ps.isEmpty()){
            txtpass.setError("Password masih kosong!");
            valid = false;
        }
        if(psu.isEmpty()){
            txtpassulang.setError("Ulangi password!");
            valid = false;
        }
        if(!psu.contentEquals(ps)){
            txtpassulang.setError("Password tidak sama, ulangi !");
        }
        return valid;
    }

    private void daftar() {
        koneksi();
        String nm = txtnama.getText().toString().trim();
        String us = txtuser.getText().toString().trim();
        String ps = txtpass.getText().toString().trim();

        validate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.createUser(nm, us, ps);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                loading.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(HalamanDaftar.this, message, Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(HalamanDaftar.this, HalamanLogin.class);
                    startActivity(login);
                    finish();
                } else {
                    Toast.makeText(HalamanDaftar.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
               // progress.dismiss();
                Toast.makeText(HalamanDaftar.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(HalamanDaftar.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }
}
