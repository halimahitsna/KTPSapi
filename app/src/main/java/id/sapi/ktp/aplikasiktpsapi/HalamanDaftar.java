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
import id.sapi.ktp.aplikasiktpsapi.modal.User;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HalamanDaftar extends AppCompatActivity {
    Toolbar toolbar;
    Button btndaftar;
    EditText txtnama, txtuser, txtpass, txtpassulang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
                //daftar();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    /*private void daftar() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        //getting the user values

        String nm = txtnama.getText().toString().trim();
        String users = txtuser.getText().toString().trim();
        String password = txtpass.getText().toString().trim();


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiService service = retrofit.create(ApiService.class);

        //Defining the user object as we need to pass it with the call
        User user = new User(nm, users, password);

        //defining the call
        *//*Call<Result> call = service.createUser(
                user.getName(),
                user.getUser(),
                user.getPassword()
        );*//*

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), "Pendaftaran Berhasil! \nSilahkan Masuk", Toast.LENGTH_LONG).show();
                txtnama.setText("");
                txtuser.setText("");
                txtpass.setText("");
                txtpassulang.setText("");
                Intent masuk = new Intent(HalamanDaftar.this, HalamanLogin.class);
                startActivity(masuk);

                //if there is no error
                if (!response.body().getError()) {
                    //starting profile activity
                    finish();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    startActivity(new Intent(getApplicationContext(), HalamanLogin.class));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/

}
