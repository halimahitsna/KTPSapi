package id.sapi.ktp.aplikasiktpsapi.tambah;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahPakan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText txtpakan, txtjml;
    Spinner stat;
    Button btnsimpan;
    Toolbar toolbars;
    ActionBar actionBar;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pakan);
        Intent i = getIntent();
        iduser = i.getStringExtra("id_user");

        //Drawerbar
        toolbars = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbars);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtpakan = (EditText)findViewById(R.id.pakan);
        txtjml = (EditText)findViewById(R.id.jml);
        stat = (Spinner)findViewById(R.id.status);
        btnsimpan = (Button)findViewById(R.id.btnSimpan);
        stat.setOnItemSelectedListener(this);
        stat.setPrompt("Pilih status");
        List<String> status = new ArrayList<String>();
        status.add("Tersedia");
        status.add("Tidak tersedia");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        stat.setAdapter(dataAdapter);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    private void simpan() {
        koneksi();
        String id = iduser.trim();
        String pkn = txtpakan.getText().toString().trim();
        String jml = txtpakan.getText().toString().trim();
        String st = stat.getSelectedItem().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.insertPakan(id, pkn, jml, st);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                //loading.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(TambahPakan.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahPakan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(TambahPakan.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    private boolean adaInternet(){
        ConnectivityManager koneks = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneks.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(TambahPakan.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
