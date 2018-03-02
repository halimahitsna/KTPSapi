package id.sapi.ktp.aplikasiktpsapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Result;
import id.sapi.ktp.aplikasiktpsapi.modal.Sapi;
import id.sapi.ktp.aplikasiktpsapi.modal.User;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditData extends AppCompatActivity {
    Toolbar toolbar;
    Button btnsimpan;
    EditText txtidSapi, txtidJenis, txtumur, txtpassulang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

        btnsimpan = (Button)findViewById(R.id.btnSimpan);
        txtidSapi = (EditText)findViewById(R.id.idSapi);
        txtidJenis = (EditText)findViewById(R.id.idJenis);
        txtumur = (EditText)findViewById(R.id.umur);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }
    private void simpan() {

        //getting the user values

        String id = txtidSapi.getText().toString().trim();
        String jenis = txtidJenis.getText().toString().trim();
        String umur = txtumur.getText().toString().trim();


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiService service = retrofit.create(ApiService.class);

        //Defining the user object as we need to pass it with the call
        Sapi sapi = new Sapi(id, jenis, umur);

        //defining the call
        Call<Result> call = service.insertSapi(
                sapi.getId_sapi(),
                sapi.getId_jenis(),
                sapi.getUmur()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), "Pendaftaran Berhasil! \nSilahkan Masuk", Toast.LENGTH_LONG).show();
                txtidSapi.setText("");
                txtidJenis.setText("");
                txtumur.setText("");
                txtpassulang.setText("");
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
