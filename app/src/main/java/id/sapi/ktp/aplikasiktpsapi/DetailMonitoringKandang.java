package id.sapi.ktp.aplikasiktpsapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailMonitoringKandang extends AppCompatActivity {
    TextView txtid, txtkandang, txtsuhu, txtkelembapan, txtgas, txtbsuhu;
    Switch swotomatis, swkipas, swlampu;
    String idkan, stlampu, stkipas;
    Integer bsuhu, bkelembapan, bgas, suhu, kelembapan, gas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_monitoring_kandang);
        Intent i = getIntent();
        idkan = i.getStringExtra("id_kandang");

        //txtid = (TextView)findViewById(R.id.idKandang);
        txtkandang = (TextView)findViewById(R.id.kandang);
        txtsuhu = (TextView)findViewById(R.id.suhu);
        txtkelembapan = (TextView)findViewById(R.id.kelembapan);
        txtgas = (TextView)findViewById(R.id.gas);
        swotomatis  = (Switch) findViewById(R.id.oto);
        swkipas = (Switch)findViewById(R.id.kipas);
        swlampu = (Switch)findViewById(R.id.lampu);
        txtbsuhu =(TextView)findViewById(R.id.bsuhu);

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

        swotomatis.setTextOn("1");
        swotomatis.setTextOff("0");
        swkipas.setTextOn("1");
        swkipas.setTextOff("0");
        swlampu.setTextOn("1");
        swlampu.setTextOff("0");

        Button btnok = (Button)findViewById(R.id.btnSimpan);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swotomatis.isChecked() == true) {
                    if (suhu < 27) {
                        stlampu = swlampu.getTextOn().toString();
                        stkipas = swkipas.getTextOff().toString();
                    }else if (suhu > 27) {
                        stkipas = swkipas.getTextOn().toString();
                        stlampu = swkipas.getTextOff().toString();
                    }else{
                        stlampu = swlampu.getTextOff().toString();
                    stkipas = swkipas.getTextOff().toString();}
                }else if(swkipas.isChecked() == true){
                    stlampu = swlampu.getTextOff().toString();
                    stkipas = swkipas.getTextOn().toString();
                }else if(swlampu.isChecked() == true){
                    stlampu = swlampu.getTextOn().toString();
                    stkipas = swkipas.getTextOff().toString();
                }else{
                    stlampu = swlampu.getTextOff().toString();
                    stkipas = swkipas.getTextOff().toString();
                }
                update();
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
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    Toast.makeText(DetailMonitoringKandang.this, message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(DetailMonitoringKandang.this, lamp + fan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailMonitoringKandang.this, message, Toast.LENGTH_SHORT).show();
                }
                updateLampu();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(DetailMonitoringKandang.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
       // onBackPressed();
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
}
