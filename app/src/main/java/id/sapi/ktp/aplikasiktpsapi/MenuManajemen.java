package id.sapi.ktp.aplikasiktpsapi;

import android.app.ProgressDialog;
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
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Kategori;
import id.sapi.ktp.aplikasiktpsapi.modal.KategoriAdapter;
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

public class MenuManajemen extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    private TextView textView;
    Button datasapi, jenis, indukan, kandang, pakan, penyakit;
    SharedPrefManager sharedPrefManager;
    public TextView nama;
    public ImageView image;
    ArrayList<Profil> profilList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_manajemen);

        sharedPrefManager = new SharedPrefManager(this);

       /* if (!sharedPrefManager.getLogin()) {
            startActivity(new Intent(MainActivity.this, HalamanLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }*/

        //initViews();

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

        String id = getIntent().getStringExtra("id_user");
        datasapi = (Button)findViewById(R.id.sapi);
        jenis = (Button)findViewById(R.id.jenis);
        kandang = (Button)findViewById(R.id.kandang);
        indukan = (Button)findViewById(R.id.indukan);
        pakan = (Button)findViewById(R.id.pakan);
        penyakit = (Button)findViewById(R.id.penyakit);

        jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sapi = new Intent(MenuManajemen.this, Manajemen.class);
                sapi.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(sapi);
            }
        });
        kandang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sapi = new Intent(MenuManajemen.this, Manajemen.class);
                sapi.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(sapi);
            }
        });
        indukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sapi = new Intent(MenuManajemen.this, Manajemen.class);
                sapi.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(sapi);
            }
        });
        penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sapi = new Intent(MenuManajemen.this, Manajemen.class);
                sapi.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(sapi);
            }
        });
        pakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sapi = new Intent(MenuManajemen.this, Manajemen.class);
                sapi.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(sapi);
            }
        });

        datasapi.setText(id);
        datasapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sapi = new Intent(MenuManajemen.this, Manajemen.class);
                sapi.putExtra("id_user", getIntent().getStringExtra("id_user"));
                startActivity(sapi);
            }
        });
    }

        //tampilan
        private void loadHeader() {
        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        // get user data from session
        HashMap<String, String> user = sharedPrefManager.getUserDetails();
        ApiService service = UtilsApi.getClient().create(ApiService.class);
        String name = user.get(SharedPrefManager.KEY_USER_NAME);
        Call<ProfilList> userCall = service.getJSONProfil("http://uol.techarea.co.id/api/getuser?user=" + name);
        /*userCall.enqueue(new Callback<ProfilList>(){

            @Override
            public void onResponse(Call<ProfilList> call, Response<ProfilList> response) {
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
                                Intent a = new Intent(MenuManajemen.this, MainActivity.class);
                                startActivity(a);
                                return true;
                            case R.id.menu_manajemen:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent b = new Intent(MenuManajemen.this, MenuManajemen.class);
                                b.putExtra("id_user", getIntent().getStringExtra("id_user"));
                                startActivity(b);
                                return true;
                            case R.id.menu_monitoring:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent c = new Intent(MenuManajemen.this, MonitoringKandang.class);
                                c.putExtra("id_user", getIntent().getStringExtra("id_user"));
                                startActivity(c);
                                return true;
                            case R.id.menu_jadwal:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent d = new Intent(MenuManajemen.this, JadwalMakan.class);
                                d.putExtra("id_user", getIntent().getStringExtra("id_user"));
                                startActivity(d);
                                return true;
                            case R.id.menu_laporan:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent e = new Intent(MenuManajemen.this, Laporan.class);
                                e.putExtra("id_user", getIntent().getStringExtra("id_user"));
                                startActivity(e);
                                return true;
                            case R.id.menu_profil:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent f = new Intent(MenuManajemen.this, Profil.class);
                                f.putExtra("id_user", getIntent().getStringExtra("id_user"));
                                startActivity(f);
                                return true;
                            case R.id.menu_pengaturan:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent g = new Intent(MenuManajemen.this, Pengaturan.class);
                                g.putExtra("id_user", getIntent().getStringExtra("id_user"));
                                startActivity(g);
                                return true;
                            case R.id.menu_keluar:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.KEY_LOGIN, false);
                                startActivity(new Intent(MenuManajemen.this, HalamanLogin.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                return true;
                        }
                        return true;
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
            Toast.makeText(MenuManajemen.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
    }
}
