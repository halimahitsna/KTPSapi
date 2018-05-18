package id.sapi.ktp.aplikasiktpsapi.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import id.sapi.ktp.aplikasiktpsapi.R;

public class TambahRfid extends AppCompatActivity {
    Toolbar toolbars;
    ActionBar actionBar;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rfid);

        //Drawerbar
        toolbars = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbars);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
