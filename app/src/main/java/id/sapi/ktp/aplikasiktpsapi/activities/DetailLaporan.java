package id.sapi.ktp.aplikasiktpsapi.activities;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import id.sapi.ktp.aplikasiktpsapi.R;

public class DetailLaporan extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    TextView ttoolbar, tjudul, tisi, ttgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);
        Intent i = getIntent();
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setDisplayShowHomeEnabled(true);

        ttoolbar = (TextView)findViewById(R.id.toolbar_title);
        tjudul = (TextView)findViewById(R.id.judul);
        tisi = (TextView)findViewById(R.id.isilaporan);
        ttgl = (TextView)findViewById(R.id.tgl);

        ttoolbar.setText("Detail Laporan");
        tjudul.setText(i.getStringExtra("judul"));
        tisi.setText(i.getStringExtra("isi"));
        ttgl.setText("Tanggal dibuat : " +i.getStringExtra("tanggal"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
