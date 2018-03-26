package id.sapi.ktp.aplikasiktpsapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Indukan;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.Kandang;
import id.sapi.ktp.aplikasiktpsapi.modal.Pakan;
import id.sapi.ktp.aplikasiktpsapi.modal.Penyakit;
import id.sapi.ktp.aplikasiktpsapi.modal.ResponseData;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import id.sapi.ktp.aplikasiktpsapi.modal.SapiAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditData extends AppCompatActivity {
    ActionBar actionBar;
    Toolbar toolbar;
    ProgressDialog loading;
    Button btnsimpan;
    EditText txtidSapi, txtbobotlahir, txtbobothidup, txtumur, txtharga, txtwarna, tgllahir;
    ImageView foto;
    Spinner jenis, kandang, indukan, pakan, penyakit;
    DatePicker tgl_lahir;
    ArrayList<Jenis> data;
    Context mcontext;
    Retrofit apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        mcontext = this;
        apiService = UtilsApi.getClient();
        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
       // actionBar.setDisplayShowHomeEnabled(true);

        btnsimpan = (Button) findViewById(R.id.btnSimpan);
        foto = (ImageView) findViewById(R.id.foto);
        txtidSapi = (EditText) findViewById(R.id.idSapi);
        txtharga = (EditText) findViewById(R.id.harga);
        txtbobothidup = (EditText) findViewById(R.id.bobot_hidup);
        txtbobotlahir = (EditText) findViewById(R.id.bobot_lhr);
        txtumur = (EditText) findViewById(R.id.umur);
        txtwarna = (EditText)findViewById(R.id.warna);
        //EditTextGet
        txtidSapi.setText(getIntent().getStringExtra("id_sapi"));
        txtharga.setText(getIntent().getStringExtra("umur"));
        txtumur.setText(getIntent().getStringExtra("umur"));
        txtbobotlahir.setText(getIntent().getStringExtra("bobot_lahir"));
        txtbobothidup.setText(getIntent().getStringExtra("bobot_hidup"));
        Picasso.with(mcontext).load(getIntent().getStringExtra("foto")).resize(150, 150)
                .into(foto);

        jenis = (Spinner) findViewById(R.id.jenis);
        indukan = (Spinner) findViewById(R.id.indukan);
        kandang = (Spinner) findViewById(R.id.kandang);
        pakan = (Spinner) findViewById(R.id.pakan);
        penyakit = (Spinner) findViewById(R.id.penyakit);
        tgllahir = (EditText) findViewById(R.id.tgl);
        //initSpinnerJenis();
        initSpinnerJenis();
        initSpinnerKandang();
        initSpinnerIndukan();
        initSpinnerPakan();
        initSpinnerPenyakit();
        jenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedjenis = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        kandang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedKandang = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        indukan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIndukan = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        pakan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPakan = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        penyakit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPenyakit = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }

    private void simpan() {
        //koneksi();
        String id = txtidSapi.getText().toString().trim();
        String bl = txtbobotlahir.getText().toString().trim();
        String bhdp = txtbobothidup.getText().toString().trim();
        String hr = txtharga.getText().toString().trim();
        String umur = txtumur.getText().toString().trim();
        String wr = txtwarna.getText().toString().trim();
        String jn = String.valueOf(jenis.getSelectedItemId());
        String kd = String.valueOf(kandang.getSelectedItemId());
        String in = String.valueOf(indukan.getSelectedItemId());
        String pkn = String.valueOf(pakan.getSelectedItemId());
        String pny = String.valueOf(penyakit.getSelectedItemId());
        String tg = tgllahir.getText().toString().trim();

        //validate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<Result> call = api.insertSapi(id, jn, kd, in, pkn, pny, tg, bl, bhdp, umur, wr, hr);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                loading.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(EditData.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditData.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progress.dismiss();
                Toast.makeText(EditData.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerJenis() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<ResponseData> call = request.getJen(3);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    List<Jenis> semuadosenItems = response.body().getJenis();
                    List<String> listSpinner = new ArrayList<String>();
                    listSpinner.add("- Pilih Jenis Sapi -");
                    for (int i = 0; i < semuadosenItems.size(); i++) {
                        listSpinner.add(semuadosenItems.get(i).getJenis());

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditData.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    jenis.setAdapter(adapter);
                } else {
                    Toast.makeText(EditData.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Toast.makeText(EditData.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerKandang() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<ResponseData> call = request.getKan(3);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    List<Kandang> semuadosenItems = response.body().getKandang();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < semuadosenItems.size(); i++) {
                        listSpinner.add(semuadosenItems.get(i).getKandang());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditData.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    kandang.setAdapter(adapter);
                } else {
                    Toast.makeText(EditData.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Toast.makeText(EditData.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerIndukan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<ResponseData> call = request.getInduk(3);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    List<Indukan> semuadosenItems = response.body().getIndukan();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < semuadosenItems.size(); i++) {
                        listSpinner.add(semuadosenItems.get(i).getIndukan());
                        listSpinner.add(semuadosenItems.get(i).getId_indukan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditData.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    indukan.setAdapter(adapter);
                } else {
                    Toast.makeText(EditData.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Toast.makeText(EditData.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerPakan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<ResponseData> call = request.getPa(3);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    List<Pakan> semuadosenItems = response.body().getPakan();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < semuadosenItems.size(); i++) {
                        listSpinner.add(semuadosenItems.get(i).getPakan());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditData.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pakan.setAdapter(adapter);
                } else {
                    Toast.makeText(EditData.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Toast.makeText(EditData.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerPenyakit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<ResponseData> call = request.getPen(3);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    List<Penyakit> semuadosenItems = response.body().getPenyakit();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < semuadosenItems.size(); i++) {
                        listSpinner.add(semuadosenItems.get(i).getPenyakit());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditData.this,
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    penyakit.setAdapter(adapter);
                } else {
                    Toast.makeText(EditData.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Toast.makeText(EditData.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
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


