package id.sapi.ktp.aplikasiktpsapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahKandang extends AppCompatActivity {

    EditText txtid, txtkandang, txbsuhu, tbkelembapan, tbgas;
    Button btnsimpan;
    Toolbar toolbars;
    ActionBar actionBar;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kandang);

        Intent i = getIntent();
        iduser = i.getStringExtra("id_user");

        txtid = (EditText) findViewById(R.id.idKandang);
        txtkandang = (EditText)findViewById(R.id.kandang);
        txbsuhu = (EditText)findViewById(R.id.bsuhu);
        tbkelembapan = (EditText)findViewById(R.id.bkelembapan);
        tbgas=(EditText)findViewById(R.id.bgas);
        btnsimpan = (Button)findViewById(R.id.btnSimpan);

        //Drawerbar
        toolbars = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbars);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtid.setText(getIntent().getStringExtra("id_kandang"));
        txtkandang.setText(getIntent().getStringExtra("kandang"));
        txbsuhu.setText(getIntent().getStringExtra("bsuhu"));
        tbkelembapan.setText(getIntent().getStringExtra("bkelembapan"));
        tbgas.setText(getIntent().getStringExtra("bgas"));

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    private void simpan() {
        koneksi();
        String id = iduser.toString().trim();
        String kd = txtkandang.getText().toString().trim();
        String sh = txbsuhu.getText().toString().trim();
        String kl = tbkelembapan.getText().toString().trim();
        String gs = tbgas.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.insertKandang(id, kd, sh, kl, gs);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                //loading.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(TambahKandang.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahKandang.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(TambahKandang.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(TambahKandang.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
