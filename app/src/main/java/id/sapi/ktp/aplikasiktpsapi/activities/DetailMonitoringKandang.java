package id.sapi.ktp.aplikasiktpsapi.activities;

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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailMonitoringKandang extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    TextView txtid, txtkandang, txtsuhu, txtkelembapan, txtgas, txtbsuhu;
    Switch swotomatis, swkipas, swlampu;
    String idkan, stlampu, stkipas;
    Integer bsuhu, bkelembapan, bgas, suhu, kelembapan, gas;
    CircleProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_monitoring_kandang);
        Intent i = getIntent();
        idkan = i.getStringExtra("id_kandang");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        loading = (CircleProgressBar)findViewById(R.id.progress);
        txtkandang = (TextView)findViewById(R.id.kandang);
        txtsuhu = (TextView)findViewById(R.id.suhu);
        txtkelembapan = (TextView)findViewById(R.id.kelembapan);
        txtgas = (TextView)findViewById(R.id.gas);
        swotomatis  = (Switch) findViewById(R.id.oto);
        swkipas = (Switch)findViewById(R.id.kipas);
        swlampu = (Switch)findViewById(R.id.lampu);

        txtkandang.setText(getIntent().getStringExtra("kandang"));
        txtsuhu.setText(getIntent().getStringExtra("suhu"));
        txtkelembapan.setText(getIntent().getStringExtra("kelembapan"));
        txtgas.setText(getIntent().getStringExtra("gas"));
        //txtbsuhu.setText(getIntent().getStringExtra("bsuhu"));
       // bsuhu = Integer.valueOf(txtbsuhu.getText().toString());
//        bkelembapan = Integer.valueOf(i.getStringExtra("bkelembapan"));
  //      bgas = Integer.valueOf(i.getStringExtra("bgas"));
        suhu = Integer.valueOf(txtsuhu.getText().toString());
        kelembapan = Integer.valueOf(i.getStringExtra("kelembapan"));
        gas = Integer.valueOf(i.getStringExtra("gas"));

        swotomatis.setTextOn("ON");
        swotomatis.setTextOff("OFF");
        swkipas.setTextOn("ON");
        swkipas.setTextOff("OFF");
        swlampu.setTextOn("ON");
        swlampu.setTextOff("OFF");
        swotomatis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // Show the switch button checked status as toast message
                    Toast.makeText(getApplicationContext(),
                            "Switch is on", Toast.LENGTH_LONG).show();
                    if (suhu < 27) {
                        stlampu = "1";
                        stkipas = "0";
                    }else if (suhu > 27) {
                        stkipas = "1";
                        stlampu = "0";
                    }else{
                        stlampu = "0";
                        stkipas = "0";
                    }
                    update();
                }
                else {
                    stlampu = "0";
                    stkipas = "0";
                    update();
                    // Show the switch button checked status as toast message
                    Toast.makeText(getApplicationContext(),
                            "Switch is off", Toast.LENGTH_LONG).show();
                }
            }
        });

        swkipas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // Show the switch button checked status as toast message
                    Toast.makeText(getApplicationContext(),"Switch is on", Toast.LENGTH_LONG).show();
                    stlampu = "0";
                    stkipas = "1";
                    update();
                }
                else {
                    stlampu = "0";
                    stkipas = "0";
                    update();
                    // Show the switch button checked status as toast message
                    Toast.makeText(getApplicationContext(),
                            "Switch is off", Toast.LENGTH_LONG).show();
                }
            }
        });

        swlampu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // Show the switch button checked status as toast message
                    Toast.makeText(getApplicationContext(),"Switch is on", Toast.LENGTH_LONG).show();
                    stlampu = "1";
                    stkipas = "0";
                    update();
                }
                else {
                    stlampu = "0";
                    stkipas = "0";
                    update();
                    // Show the switch button checked status as toast message
                    Toast.makeText(getApplicationContext(),
                            "Switch is off", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void update() {
        koneksi();
        String id = idkan.toString().trim();
        final String lamp = stlampu.trim();
        final String fan = stkipas.trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.updateStatus(id, lamp, fan);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                loading.setVisibility(View.INVISIBLE);
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(DetailMonitoringKandang.this, message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(DetailMonitoringKandang.this, "lampu : " +lamp + " kipas : "+ fan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailMonitoringKandang.this, message, Toast.LENGTH_SHORT).show();
                }
              //  updateLampu();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(DetailMonitoringKandang.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLampu() {
        koneksi();
        String id = idkan.toString().trim();
        final String lamp = stlampu.trim();
        final String fan = stkipas.trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.updateLampu(id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(DetailMonitoringKandang.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailMonitoringKandang.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(DetailMonitoringKandang.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
        // onBackPressed();
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
            Toast.makeText(DetailMonitoringKandang.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
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
