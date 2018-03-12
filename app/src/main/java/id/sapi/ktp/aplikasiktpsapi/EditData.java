package id.sapi.ktp.aplikasiktpsapi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.api.UtilsApi;
import id.sapi.ktp.aplikasiktpsapi.modal.Jenis;
import retrofit2.Retrofit;

public class EditData extends AppCompatActivity {
    Toolbar toolbar;
    Button btnsimpan;
    EditText txtidSapi, txtbobotlahir, txtbobothidup, txtumur, txtharga;
    ImageView foto;
    Spinner jenis, kandang, indukan,pakan, tgl_lahir;
    Adapter adapter;
    List<Jenis> listJenis = new ArrayList<Jenis>();
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
        String[] pilihan_menu=getResources().getStringArray(R.array.pilihan_menu); // ambil menu dari string.xml
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.isi_spinner,R.id.txItemSpin, pilihan_menu);
        jenis.setAdapter(adapter);
        kandang.setAdapter(adapter);
        pakan.setAdapter(adapter);
        indukan.setAdapter(adapter);

        jenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(EditData.this, jenis.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}
