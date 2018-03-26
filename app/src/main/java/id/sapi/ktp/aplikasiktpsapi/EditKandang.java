package id.sapi.ktp.aplikasiktpsapi;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class EditKandang extends AppCompatActivity {

    EditText txtid, txtjenis;
    Button btnsimpan;
    Toolbar toolbars;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kandang);

        txtid = (EditText) findViewById(R.id.idJenis);
        txtjenis = (EditText)findViewById(R.id.jenis);
        btnsimpan = (Button)findViewById(R.id.btnSimpan);

        //Drawerbar
        toolbars = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbars);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtid.setText(getIntent().getStringExtra("id_kandang"));
        txtjenis.setText(getIntent().getStringExtra("kandang"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
