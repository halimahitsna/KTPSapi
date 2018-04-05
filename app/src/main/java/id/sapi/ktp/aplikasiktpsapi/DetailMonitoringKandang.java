package id.sapi.ktp.aplikasiktpsapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

public class DetailMonitoringKandang extends AppCompatActivity {
    TextView txtid, txtkandang, txtsuhu, txtkelembapan, txtgas;
    Switch swotomatis, swkipas, swlampu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_monitoring_kandang);

        //txtid = (TextView)findViewById(R.id.idKandang);
        txtkandang = (TextView)findViewById(R.id.kandang);
        txtsuhu = (TextView)findViewById(R.id.suhu);
        txtkelembapan = (TextView)findViewById(R.id.kelembapan);
        txtgas = (TextView)findViewById(R.id.gas);

        txtkandang.setText(getIntent().getStringExtra("kandang"));
        txtsuhu.setText(getIntent().getStringExtra("suhu"));
        txtkelembapan.setText(getIntent().getStringExtra("kelembapan"));
        txtgas.setText(getIntent().getStringExtra("gas"));
    }
}
