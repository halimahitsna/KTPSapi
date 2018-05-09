package id.sapi.ktp.aplikasiktpsapi.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.api.ApiService;
import id.sapi.ktp.aplikasiktpsapi.api.JSONResponse;
import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailData extends AppCompatActivity {

    ActionBar actionBar;
    Toolbar toolbar;
    TextView txtidSapi,txjenis, txindukan, txpakan, txpenyakit, txkandang, txjk, txtbobotlahir, txtbobothidup, txtumur, txtharga, txtwarna, txdate, txiduser, judul;
    CircleImageView foto;
    private ArrayList<Jenis> jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.setTitle(R.string.detail_data);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        foto = (CircleImageView) findViewById(R.id.foto);
        txtidSapi = (TextView) findViewById(R.id.idSapi);
        txjenis = (TextView) findViewById(R.id.jenis);
        txindukan = (TextView) findViewById(R.id.indukan);
        txpakan = (TextView) findViewById(R.id.pakan);
        txkandang = (TextView) findViewById(R.id.kandang);
        txpenyakit = (TextView) findViewById(R.id.penyakit);
        txtharga = (TextView) findViewById(R.id.harga);
        txtbobothidup = (TextView) findViewById(R.id.bobot_hidup);
        txtbobotlahir = (TextView) findViewById(R.id.bobot_lhr);
        txtumur = (TextView) findViewById(R.id.umur);
        txtwarna = (TextView) findViewById(R.id.warna);
        txdate = (TextView) findViewById(R.id.tgl);
        txjk = (TextView)findViewById(R.id.jk) ;

        //TextGet
        txtidSapi.setText(getIntent().getStringExtra("id_sapi"));
        txjenis.setText(getIntent().getStringExtra("jenis"));
        txindukan.setText(getIntent().getStringExtra("indukan"));
        txkandang.setText(getIntent().getStringExtra("kandang"));
        txpakan.setText(getIntent().getStringExtra("pakan"));
        txpenyakit.setText(getIntent().getStringExtra("penyakit"));
        txtharga.setText("Rp "+getIntent().getStringExtra("harga"));
        txjk.setText(getIntent().getStringExtra("jenis_kelamin"));
        txtumur.setText(getIntent().getStringExtra("umur"));
        txtwarna.setText(getIntent().getStringExtra("warna"));
        txdate.setText(getIntent().getStringExtra("tgl_lahir"));
        txtbobotlahir.setText(getIntent().getStringExtra("bobot_lahir"));
        txtbobothidup.setText(getIntent().getStringExtra("bobot_hidup"));
        Picasso.with(this).load(getIntent().getStringExtra("foto")).resize(150, 150)
                .into(foto);
    }

    private boolean adaInternet(){
        ConnectivityManager koneksi = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return koneksi.getActiveNetworkInfo() != null;
    }
    private void koneksi(){
        if(adaInternet()){
//            Toast.makeText(HalamanUtama.this, "Terhubung ke internet", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(DetailData.this, "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
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
        if (item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
