package id.sapi.ktp.aplikasiktpsapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Profil;
import id.sapi.ktp.aplikasiktpsapi.modal.ProfilList;
import id.sapi.ktp.aplikasiktpsapi.modal.Sapi;
import id.sapi.ktp.aplikasiktpsapi.modal.SapiAdapter;
import id.sapi.ktp.aplikasiktpsapi.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HalamanData extends AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    ImageView add;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    private TextView toolbartext;
    private RecyclerView recyclerView;
    private ArrayList<Sapi> data;
    private SapiAdapter adapter;
    SharedPrefManager sharedPrefManager;
    public TextView nama;
    public ImageView image;
    ArrayList<Profil> profilList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_data);

        toolbartext = (TextView) findViewById(R.id.toolbar_title);
        add = (ImageView)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HalamanData.this, EditData.class);
                startActivity(a);
            }
        });
        initViews();
        sharedPrefManager = new SharedPrefManager(this);

        if (!sharedPrefManager.getLogin()) {
            startActivity(new Intent(HalamanData.this, HalamanLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        initViews();

        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        nama = (TextView) header.findViewById(R.id.tvnama);
        image = (ImageView) header.findViewById(R.id.imageView);
        loadHeader();
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);
    }

    //tampilan
    private void loadHeader() {
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
//
        // get user data from session
        HashMap<String, String> user = sharedPrefManager.getUserDetails();
        ApiService service = UtilsApi.getClient().create(ApiService.class);
        String name = user.get(SharedPrefManager.KEY_USER_NAME);
        Call<ProfilList> userCall = service.getJSONProfil("http://uol.techarea.co.id/api/getuser?user=" + name);
        /*userCall.enqueue(new Callback<ProfilList>(){

            @Override
            public void onResponse(Call<ProfilList> call, Response<ProfilList> response) {ipconfig

                if (response.isSuccessful()) {
                    profilList = response.body().getProfList();
                    Profil profil = profilList.get(0);
                    nama.setText(profil.getName());
                    //image .setImageResource();
                } else {
                    Toast.makeText(MainActivity.this, "Error while retrieving data from server, Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfilList> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Error while retrieving data from server, Please try again", Toast.LENGTH_SHORT).show();
                Log.d("onFailure", t.toString());
            }
        });*/
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        loadJSON();
    }

    private void loadJSON() {
        koneksi();
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(UtilsApi.BASE_URL)
                .baseUrl("http://ktpsapi.id/android/ktpsapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<JSONResponse> call = request.getJSONSapi("3");
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getSapi()));
                adapter = new SapiAdapter(getApplicationContext(), data);
               /* data2 = new ArrayList<>(Arrays.asList(jsonResponse.getJenis()));
                adapter2 = new JenisAdapter(getApplicationContext(), data2);*/
                recyclerView.setAdapter(adapter);

                toolbartext.setText("nnnn");
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
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
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_utama:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent a = new Intent(HalamanData.this, MainActivity.class);
                                startActivity(a);
                                return true;

                        }
                        return true;
                    }
                });
    }


    private boolean adaInternet() {
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }

    private void koneksi() {
        if (adaInternet()) {
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(HalamanData.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }

}
