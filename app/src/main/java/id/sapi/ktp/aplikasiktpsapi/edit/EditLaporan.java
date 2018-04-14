package id.sapi.ktp.aplikasiktpsapi.edit;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import id.sapi.ktp.aplikasiktpsapi.R;
import id.sapi.ktp.aplikasiktpsapi.database.LaporanDB;

public class EditLaporan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ActionBar actionBar;
    Toolbar toolbar;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mDate, iduser;
    EditText eid, ejudul, eisi, etgl;
    ImageView tgl;
    Button btnsimpan;
    String jdl, isilap, tangg;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_laporan);

        Intent i = getIntent();
        iduser = i.getStringExtra("id_user");

        //Drawerbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setDisplayShowHomeEnabled(true);

        eid = (EditText)findViewById(R.id.idLaporan);
        ejudul= (EditText)findViewById(R.id.judul);
        eisi = (EditText)findViewById(R.id.isi);
        etgl = (EditText)findViewById(R.id.tgl);
        tgl = (ImageView)findViewById(R.id.datepick);
        btnsimpan = (Button)findViewById(R.id.btnSimpan);

        eisi.setScroller(new Scroller(getApplicationContext()));
        eisi.setVerticalScrollBarEnabled(true);
        eisi.setMinLines(3);
        eisi.setMaxLines(10);

        ejudul.setText(getIntent().getStringExtra("judul"));
        eisi.setText(getIntent().getStringExtra("isi"));
        etgl.setText(getIntent().getStringExtra("tanggal"));

        jdl = getIntent().getStringExtra("judul");
        isilap = getIntent().getStringExtra("isi");
        tangg = getIntent().getStringExtra("tanggal");

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        etgl.setText(mDate);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //simpan();
            }
        });
    }

    private void simpan() {
        Long i = LaporanDB.count(LaporanDB.class, "", null);
        String id = eid.getText().toString().trim();
        String newjudul = ejudul.getText().toString().trim();
        String newisi = eisi.getText().toString().trim();
        String newtanggal = mDate.trim();

        Log.d("Note", "updating");

        List<LaporanDB> laporanDBS = LaporanDB.find(LaporanDB.class, "judul = ?", jdl);
        if (laporanDBS.size() > 0) {

            LaporanDB note = laporanDBS.get(0);
            Log.d("got note", "note: " + note.getJudul_laporan());
            note.judul_laporan = newjudul;
            note.isi_laporan = newisi;
            note.input_date = newtanggal;

            note.save();

            Toast.makeText(EditLaporan.this, "berhasil simpan", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        etgl.setText(mDate);
    }

    public void setTangal(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
}

