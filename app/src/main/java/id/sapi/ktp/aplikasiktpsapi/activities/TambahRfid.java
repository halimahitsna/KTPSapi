package id.sapi.ktp.aplikasiktpsapi.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import id.sapi.ktp.aplikasiktpsapi.R;

public class TambahRfid extends AppCompatActivity {
    Toolbar toolbars;
    ActionBar actionBar;
    String iduser;
    AVLoadingIndicatorView avLoadingIndicatorView;
    AddFloatingActionButton add;
    Button stop;
    TextView tinfo;

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
        avLoadingIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.loading);
        add = (AddFloatingActionButton)findViewById(R.id.addRFID);
        stop = (Button)findViewById(R.id.btnstop);
        tinfo = (TextView)findViewById(R.id.infotext);
        //tinfo.setText("Tambah Kode dari RFID");
        stop.setVisibility(View.INVISIBLE);
        avLoadingIndicatorView.setVisibility(View.INVISIBLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinfo.setText("Scan RFID Tag");
                avLoadingIndicatorView.smoothToShow();
                add.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinfo.setText("Tambah Kode dari RFID");
                avLoadingIndicatorView.smoothToHide();
                add.setVisibility(View.VISIBLE);
                stop.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
