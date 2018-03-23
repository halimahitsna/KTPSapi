package id.sapi.ktp.aplikasiktpsapi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import id.sapi.ktp.aplikasiktpsapi.modal.ResponseData;
import id.sapi.ktp.aplikasiktpsapi.modal.SapiAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditData extends AppCompatActivity {
    Toolbar toolbar;
    Button btnsimpan;
    EditText txtidSapi, txtbobotlahir, txtbobothidup, txtumur, txtharga;
    ImageView foto;
    Spinner jenis, kandang, indukan,pakan, tgl_lahir;
    Adapter adapter;
    ArrayList<Jenis> data;
    Context mcontext;
    Retrofit apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        mcontext  = this;
        apiService = UtilsApi.getClient();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

        btnsimpan = (Button)findViewById(R.id.btnSimpan);
        foto = (ImageView)findViewById(R.id.foto);
        txtidSapi = (EditText)findViewById(R.id.idSapi);
        txtharga = (EditText)findViewById(R.id.harga);
        txtbobothidup = (EditText)findViewById(R.id.bobot_hidup);
        txtbobotlahir = (EditText)findViewById(R.id.bobot_lhr);
        txtumur = (EditText)findViewById(R.id.umur);
        //EditTextGet
        txtidSapi.setText(getIntent().getStringExtra("id_sapi"));
        txtharga.setText(getIntent().getStringExtra("umur"));
        txtumur.setText(getIntent().getStringExtra("umur"));
        txtbobotlahir.setText(getIntent().getStringExtra("bobot_lahir"));
        txtbobothidup.setText(getIntent().getStringExtra("bobot_hidup"));
        Picasso.with(mcontext).load(getIntent().getStringExtra("foto")).resize(150,150)
                .into(foto);

        jenis = (Spinner)findViewById(R.id.jenis);
        indukan = (Spinner)findViewById(R.id.indukan);
        kandang = (Spinner)findViewById(R.id.kandang);
        pakan = (Spinner)findViewById(R.id.pakan);
        tgl_lahir = (Spinner)findViewById(R.id.tgl_lahir);
        //initSpinnerJenis();
        initSpinnerJenis();
        jenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
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
        Call<ResponseData> call = request.getJen(3);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful()) {
                    List<Jenis> semuadosenItems = response.body().getJenis();
                    List<String> listSpinner = new ArrayList<String>();
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

            @Override
            public boolean onOptionsItemSelected (MenuItem item){
            if (item.getItemId() == android.R.id.home)
                finish();

            return super.onOptionsItemSelected(item);
        }

        }


