package id.sapi.ktp.aplikasiktpsapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.JenisAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailData extends AppCompatActivity {

    ActionBar actionBar;
    Toolbar toolbar;
    TextView txtidSapi,txjenis, txindukan, txpakan, txpenyakit, txkandang, txtbobotlahir, txtbobothidup, txtumur, txtharga, txtwarna, txdate, txiduser, judul;
    ImageView foto;
    private ArrayList<Jenis> jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.setTitle("Edit Data");

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        foto = (ImageView) findViewById(R.id.foto);
        txtidSapi = (TextView) findViewById(R.id.idSapi);
        txjenis = (TextView) findViewById(R.id.jenis);
        txindukan = (TextView) findViewById(R.id.indukan);
        txpakan = (TextView) findViewById(R.id.pakan);
        txkandang = (TextView) findViewById(R.id.kandang);
        txpenyakit = (TextView) findViewById(R.id.penyakit);
        txtharga = (TextView) findViewById(R.id.harga);
        txtbobothidup = (TextView) findViewById(R.id.bobot_hidup);
        txtbobotlahir = (TextView) findViewById(R.id.bobot_lhr);
        txtumur = (TextView) findViewById(R.id.umur);
        txtwarna = (TextView) findViewById(R.id.warna);
        txdate = (TextView) findViewById(R.id.tgl);

        getJenis();

        //TextGet
        txtidSapi.setText(getIntent().getStringExtra("id_sapi"));
        txtharga.setText(getIntent().getStringExtra("harga"));
        txtumur.setText(getIntent().getStringExtra("umur"));
        txtbobotlahir.setText(getIntent().getStringExtra("bobot_lahir"));
        txtbobothidup.setText(getIntent().getStringExtra("bobot_hidup"));
        Picasso.with(this).load(getIntent().getStringExtra("foto")).resize(150, 150)
                .into(foto);
    }

    private void getJenis() {
        koneksi();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService request = retrofit.create(ApiService.class);
        // Integer id = Integer.valueOf(sharedPrefManager.getSPId());
        Call<JSONResponse> call = request.getJenis(getIntent().getStringExtra("id_jenis").toString());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                jenis = new ArrayList<>(Arrays.asList(jsonResponse.getJenis()));
                txjenis.setText(jenis.get(0).getJenis());
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
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
            Toast.makeText(DetailData.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }
}
